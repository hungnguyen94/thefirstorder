(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .directive('fabricMap', fabricMap);

    fabricMap.$inject = ['$window'];
    
    function fabricMap($window) {
        var directive = {
            restrict: 'EA',
            link: link,
            // templateUrl: 'app/map/fabric-map-view-template.html',
            scope: {
                map: '=', 
                selected: '='
                // onSelect: '&', 
                // onClick: '&'
            }, 
            controller: 'FabricMapController',
            controllerAs: 'vm',
            bindToController: true
        };
        return directive;

        function link(scope, element, attrs) {
            console.log('fabric map directive called');
            console.log('Element is: ', element);
            console.log('Scope is: ', scope);
            
            scope.canvas = {};
            scope.selected = {};

            init();
            
            ///////////////////////////////////////
            
            angular.element($window).bind('resize', resize);

            scope.$watch('vm.map', function (newMap) {
                console.log('Changed map :', newMap);
                draw(newMap.cameras, newMap.players);
            });
            
            scope.canvas.on('object:selected', onSelect);

            scope.canvas.on('object:modified', updateEntity);

            /////////////////////////


            function init() {
                console.log('init directive');
                var canvas = new fabric.Canvas(element[0]);

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
                scope.canvas.calcOffset();
                scope.canvas.renderAll();
                console.log('parent is ', parent);
            }
            
            function draw(cameras, players) {
                var drawableCameras = cameras.map(transformCamera);
                var drawablePlayers = players.map(transformPlayer);
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
                drawable.hasControls = false;
                var position = getAbsolutePosition(drawable.x, drawable.y);
                drawable.left = position.x;
                drawable.top = position.y;
                return drawable;
            }

            function transformCamera(camera) {
                var rect = new fabric.Circle({
                    radius: 10, 
                    x: camera.x,
                    y: camera.y,
                    fill: 'red',
                    entity: camera
                });
                return rect;
            }

            function transformPlayer(player) {
                var rect = new fabric.Rect({
                    x: player.x,
                    y: player.y,
                    fill: 'blue',
                    entity: player
                });
                return rect;
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
                }
            }

            function onSelect (options) {
                console.log('Selected object: ', options);
                var target = options.target;
                if(target) {
                    scope.vm.selected = target.entity;
                }
            }
            
            
            // /**
            //  * Draws a grid on the canvas
            //  * @param gridsize Size of the blocks in the grid
            //  */
            // function drawGrid(gridsize) {
            //     var canvas = scope.canvas;
            //     for(var x = 0; x < (canvas.width / gridsize); x++) {
            //         canvas.add(new fabric.Line([
            //                 gridsize * x,
            //                 0,
            //                 gridsize * x,
            //                 Math.floor(canvas.height / gridsize) * gridsize],
            //             { stroke: "#000000", strokeWidth: 1, selectable:false, strokeDashArray: [1, 1]}
            //         ));
            //         canvas.add(new fabric.Line([
            //                 0,
            //                 gridsize * x,
            //                 Math.floor(canvas.width / gridsize) * gridsize - gridsize,
            //                 gridsize * x],
            //             { stroke: "#000000", strokeWidth: 1, selectable:false, strokeDashArray: [1, 1]}
            //         ));
            //     }
            // }
            
        }
    }

    // FabricMapController.$inject = ['$scope', 'MapService'];
    //
    // function FabricMapController ($scope, MapService) {
    //     console.log('This is the fabric-map-view controller');
    //     var vm = this;
    //     vm.canvas = $scope.canvas;
    //     vm.draw = $scope.draw;
    //     console.log('Scope is: ', $scope);
    //
    //     vm.transformCamera = transformCamera;
    //     vm.transformPlayer = transformPlayer;
    //
    //     // MapService.get({mapId: vm.mapId}, function (result) {
    //     //     console.log('result is: ', result);
    //     //     vm.cameras = result.cameras;
    //     //     vm.players = result.players;
    //     //     var drawableCameras = vm.cameras.map(transformCamera);
    //     //     var drawablePlayers = vm.players.map(transformPlayer);
    //     //     vm.drawables = drawableCameras.concat(drawablePlayers);
    //     //     console.log('Drawables: ', vm.drawables);
    //     //     $scope.draw(vm.drawables);
    //     // });
    //
    //     function transformCamera(camera) {
    //         var rect = new fabric.Rect({
    //             left: camera.x,
    //             top: camera.y,
    //             fill: 'red'
    //         });
    //         return rect;
    //     }
    //
    //     function transformPlayer(player) {
    //         var rect = new fabric.Rect({
    //             left: player.x,
    //             top: player.y,
    //             fill: 'blue'
    //         });
    //         return rect;
    //     }
    // };
})();

