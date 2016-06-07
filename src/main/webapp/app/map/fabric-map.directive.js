(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .directive('fabricMap', fabricMap);

    fabricMap.$inject = ['$window', 'Player', 'Camera'];

    function fabricMap($window, Player, Camera) {
        var directive = {
            restrict: 'EA', 
            scope: {
                map: '=map', 
                selected: '=selected', 
                editable: '=editable'
            }, 
            link: link,
            controller: 'MapEditorController',
            controllerAs: 'vm',
            bindToController: true
        };
        return directive;

        function link(scope, element, attrs) {
            console.log('fabric map directive called');
            console.log('Element is: ', element);
            console.log('Scope is: ', scope);

            scope.canvas = {};
            

            init();

            ///////////////////////////////////////

            angular.element($window).bind('resize', resize);

            scope.$watch('vm.map', function (newMap) {
                console.log('Changed map :', newMap);
                draw(scope.vm.map.cameras, scope.vm.map.players);
            });

            scope.canvas.on('object:selected', onSelect);

            scope.canvas.on('object:modified', updateEntity);

            /////////////////////////


            function init() {
                console.log('init directive');
                var canvas = new fabric.Canvas(element[0]);
                canvas.selection = false;
                fabric.Image.fromURL('content/images/concertzaal.jpg', function(img) {
                    scope.aspectRatio = img.width / img.height;
                    img.set({
                        width: canvas.width,
                        height: canvas.width / scope.aspectRatio,
                        originX: 'left',
                        originY: 'top'
                    });
                    canvas.setBackgroundImage(img, canvas.renderAll.bind(canvas));
                    resize();
                });
                scope.canvas = canvas;
                // drawGrid(grid);
            }

            /**
             * Resize the canvas based on the parent.
             */
            function resize() {
                var parent = element.parent().parent();
                var width = parent[0].clientWidth - (parent[0].offsetLeft);
                scope.canvas.setWidth(width);
                scope.canvas.setHeight(width / scope.aspectRatio);

                console.log('background image: ', scope.canvas.backgroundImage);
                scope.canvas.backgroundImage.set({
                    width: scope.canvas.width,
                    height: scope.canvas.width / scope.aspectRatio
                });
                scope.canvas.forEachObject(setDrawableProperties);
                scope.canvas.renderAll();
                scope.canvas.calcOffset();
                console.log('parent is ', parent);
            }

            function draw(cameras, players) {
                var drawableCameras = cameras.map(transformCamera);
                var drawablePlayers = players.map(transformPlayer);
                scope.canvas.clear();
                drawEntities(drawableCameras.concat(drawablePlayers));
            }

            function drawEntities(entities) {
                var drawables = entities.map(setDrawableProperties);
                console.log('drawing ', drawables);
                drawables.forEach(function (d) {
                    scope.canvas.add(d);
                });
                scope.canvas.renderAll();
            }

            function setDrawableProperties(drawable) {
                drawable.width = 15;
                drawable.height = 15;
                drawable.lockScalingX = true;
                drawable.lockScalingY = true;
                var position = getAbsolutePosition(drawable.x, drawable.y);
                drawable.left = position.x;
                drawable.top = position.y;
                return drawable;
            }

            function transformCamera(camera) {
                var cam = new fabric.Path('m96.6,26.8h-86.1c-2.2,0-4.1,1.8-4.1,4.1v67.2c0,2.2 1.8,4.1 4.1,4.1h86.1c2.2,0 4.1-1.8 4.1-4.1v-19.4l14.9,14.9c0.8,0.8 1.8,1.2 2.9,1.2 0.5,0 1.1-0.1 1.6-0.3 1.5-0.6 2.5-2.1 2.5-3.8v-52.5c0-1.6-1-3.1-2.5-3.8-1.5-0.6-3.3-0.3-4.4,0.9l-14.9,14.9v-19.3c-0.1-2.3-1.9-4.1-4.2-4.1zm-4.1,33.3v8.8 25.2h-78v-59.2h78v25.2zm21.9-12v32.9l-13.7-13.7v-5.4l13.7-13.8z');
                cam.set({
                    x: camera.x,
                    y: camera.y,
                    scaleX: 0.3,
                    scaleY: 0.3,
                    padding: 10,
                    fill: 'white',
                    entity: camera,
                    camera: true,
                    hasControls: true
                });
                cam.setControlsVisibility({
                    bl: false,
                    br: false,
                    mb: false,
                    ml: false,
                    mr: false,
                    mt: false,
                    tl: false,
                    tr: false,
                    mtr: true
                });
                return cam;
            }

            function transformPlayer(player) {
                var path = 'M130.643,165.335c-4.203-9.563-2.312-9.878,0.63-17.759s-0.21-14.291-2.942-13.556c-2.732,0.735-1.891,2.522-3.257,5.254 c-1.366,2.732-2.732,4.414-6.2,4.414s-4.833-1.997-4.833-1.997l-0.736-75.343c1.366-3.889,6.095-5.254,6.2-5.569 c-0.42-6.516-1.576-7.671-2.837-10.193c-1.261-2.521-1.051-4.623,1.261-5.464s3.993-5.779,0.841-8.196s-6.831-0.315-7.566,1.892 c-0.118,0.353-0.372,1.21-0.716,2.4l-0.926-0.522c0,0,0.138-0.374-0.414-0.827s-1.222-0.552-1.498-0.315 c-0.208,0.179-0.736,0.931,0.375,1.497c0.926,0.474,1.34,0.132,1.34,0.132l0.979,0.534c-0.278,0.966-0.604,2.107-0.957,3.355 l-0.771-0.436c0,0,0.138-0.374-0.414-0.827s-1.222-0.552-1.498-0.315c-0.208,0.179-0.736,0.931,0.375,1.498 c0.926,0.473,1.34,0.131,1.34,0.131l0.826,0.451c-0.322,1.137-0.663,2.35-1.011,3.585l-0.682-0.385c0,0,0.138-0.374-0.414-0.827 s-1.222-0.552-1.498-0.315c-0.208,0.179-0.736,0.931,0.375,1.497c0.926,0.474,1.34,0.132,1.34,0.132l0.737,0.402 c-0.32,1.141-0.644,2.294-0.959,3.422l-0.724-0.409c0,0,0.138-0.374-0.414-0.827c-0.552-0.453-1.222-0.552-1.498-0.315 c-0.208,0.179-0.736,0.931,0.375,1.497c0.926,0.473,1.339,0.132,1.339,0.132l0.781,0.426c-0.335,1.199-0.66,2.36-0.959,3.435 l-0.649-0.366c0,0,0.138-0.375-0.414-0.828s-1.221-0.552-1.497-0.315c-0.208,0.179-0.736,0.931,0.374,1.498 c0.926,0.473,1.34,0.131,1.34,0.131l0.706,0.385c-0.369,1.322-0.694,2.492-0.948,3.405l-0.664-0.375c0,0,0.138-0.374-0.414-0.827 c-0.552-0.453-1.222-0.552-1.498-0.315c-0.208,0.179-0.736,0.931,0.375,1.497c0.926,0.474,1.339,0.132,1.339,0.132l0.72,0.393 c-0.271,0.974-0.431,1.551-0.431,1.551l2.102,3.468l-1.261,67.567c0,0-1.786,2.942-5.464,2.207 c-3.678-0.736-5.254-4.834-5.359-9.037c-0.105-4.203-4.939-5.149-6.936,2.312s1.576,14.396,2.417,18.39 c0.84,3.992,1.471,6.62,1.051,10.613s-2.837,5.989-5.149,13.345s-2.627,12.925,0.735,17.653c3.363,4.729,8.512,6.62,12.189,7.776 c3.678,1.156,10.718,1.261,10.718,1.261c7.776,0.631,19.397,1.182,24.799-8.722C137.999,182.883,134.846,174.897,130.643,165.335z'

                var p = new fabric.Path(path);
                p.set({
                    x: player.x,
                    y: player.y,
                    scaleX: 0.5,
                    scaleY: 0.5,
                    padding: 15,
                    fill: 'blue',
                    entity: player,
                    player: true,
                    hasControls: false
                });
                return p;
            }

            function getAbsolutePosition(x, y) {
                var currentWidth = scope.canvas.getWidth();
                var currentHeight = scope.canvas.getHeight();
                var position = {};
                position.x = Math.round((x / 100) * currentWidth);
                position.y = Math.round((y / 100) * currentHeight);
                console.log('Calculated absolute positions: ', position);
                return position;
            }

            function getRelativePosition(x, y) {
                var currentWidth = scope.canvas.getWidth();
                var currentHeight = scope.canvas.getHeight();
                var position = {};
                position.x = Math.round((x / currentWidth) * 100);
                position.y = Math.round((y / currentHeight) * 100);
                console.log('Calculated relative positions: ', position);
                return position;
            }

            function updateEntity(options) {
                console.log('Modified object: ', options);
                var target = options.target;
                if(target) {
                    var entity = target.entity;
                    var position = getRelativePosition(target.left, target.top);
                    entity.x = position.x;
                    entity.y = position.y;
                    onModify(target);
                }
            }

            function onSelect (options) {
                console.log('Selected object: ', options);
                var target = options.target;
                scope.vm.setSelected(target.entity);// = target.entity;
                console.log('vm.selected: ', scope.vm.selected);
            }

            function onModify(target) {
                console.log('updated target: ', target);
                if(target.camera) {
                    Camera.update(target.entity);
                } else if(target.player) {
                    Player.update(target.entity);
                }
            }
        }
    }
})();

