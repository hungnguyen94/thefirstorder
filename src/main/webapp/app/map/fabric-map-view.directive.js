(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .directive('fabricMapView', fabricMapView);

    function fabricMapView() {
        var directive = {
            restrict: 'EA', 
            link: link,
            // templateUrl: 'app/map/fabric-map-view-template.html', 
            controller: 'FabricMapViewController', 
            controllerAs: 'vm', 
            bindToController: true
        };
        return directive;

        function link(scope, element, attrs) {
            console.log('Element is: ', element);
            // var canvasElement = document.getElementById('fabricCanvas');
            var canvas = new fabric.Canvas(element[0]);
            var grid = 15;
            
            // console.log('element parent: ', element.parent().parent());
            canvas.setWidth(600);
            // canvasElement.width = element.parent().width() * 0.95;
            // temp hardcoded
            canvas.setHeight(500);

            drawGrid();
            
            scope.fabriccanvas = canvas;
            
            scope.draw = function (drawables) {
                drawables = drawables.map(setDrawableProperties);
                console.log('drawing ', drawables);
                drawables.forEach(function (d) {
                    canvas.add(d);
                });
            };
            
            function setDrawableProperties(drawable) {
                drawable.width = grid;
                drawable.height = grid;
                drawable.lockScalingX = true;
                drawable.lockScalingY = true; 
                drawable.hasControls = false;
                return drawable;
            }

            /**
             * Draws a grid on the canvas
             * @param gridsize Size of the blocks in the grid
             */
            function draw_grid(gridsize) {
                for(var x = 0; x < (canvas.width / gridsize); x++) {
                    canvas.add(new fabric.Line([
                            gridsize * x,
                            0,
                            gridsize * x,
                            Math.floor(canvas.height / gridsize) * gridsize],
                        { stroke: "#000000", strokeWidth: 1, selectable:false, strokeDashArray: [1, 1]}
                    ));
                    canvas.add(new fabric.Line([
                            0,
                            gridsize * x,
                            Math.floor(canvas.width / gridsize) * gridsize - gridsize,
                            gridsize * x],
                        { stroke: "#000000", strokeWidth: 1, selectable:false, strokeDashArray: [1, 1]}
                    ));
                }
            }
            
            console.log('Link from directive fabricMapView called');
        }
    }

    // FabricMapViewController.$inject = ['$scope', 'MapService'];
    //
    // function FabricMapViewController ($scope, MapService) {
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

