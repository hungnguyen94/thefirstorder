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
                
                if(scope.vm.editable == false) {
                    drawable.lockMovementX = true;
                    drawable.lockMovementY = true;
                    drawable.hasControls = false;
                }
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
                var p = new fabric.Circle();
                p.set({
                    radius: 15,
                    x: player.x,
                    y: player.y,
                    scaleX: 0.5,
                    scaleY: 0.5,
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

