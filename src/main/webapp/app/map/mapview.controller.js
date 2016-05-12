(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('MapviewController', MapviewController);

    MapviewController.$inject = ['$scope', '$state', 'Camera', 'AlertService'];

    function MapviewController ($scope, $state, Camera, AlertService) {
        var vm = this;
        var grid = 15;

        vm.loadCamera = loadCamera;
        vm.loadCamera();

        function loadCamera () {
            Camera.query({

            }, onSuccess, onError);

            function onSuccess(data, headers) {
                vm.cameras = data;
                vm.queryCount = vm.totalItems;
                drawCameras(data);
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function drawCameras(cameraData) {
            var canvas = new fabric.Canvas('concertMap');

            var grid = 15;

            canvas.on('object:moving', function (options) {
                options.target.set({
                    left: Math.round(options.target.left / grid) * grid,
                    top: Math.round(options.target.top / grid) * grid
                });

                var currentCamera = cameraData[options.target.id];
                currentCamera.x = options.target.left / grid;
                currentCamera.y = options.target.top / grid;
                console.log("New X: " + currentCamera.x + " New Y: " + currentCamera.y);
                Camera.update(currentCamera);

            });

            // This function definition will generate a new Camera at the clicked position
            canvas.on('mouse:down', function (options) {
                canvas.on('mouse:up', function(options2) {
                    var firstPointer = canvas.getPointer(options.e);
                    var secondPointer = canvas.getPointer(options2.e);

                    // Check if coordinates are still the same at the beginning and the end of the click
                    if (firstPointer.x == secondPointer.x && firstPointer.y == secondPointer.y) {
                        var pointer = canvas.getPointer(options.e);

                        var actualPosX = pointer.x;
                        var actualPosY = pointer.y;

                        var gridPosX = Math.floor(actualPosX / grid);
                        var gridPosY = Math.floor(actualPosY / grid);

                        var newCamera = new Object();
                        newCamera.x = gridPosX;
                        newCamera.y = gridPosY;
                        newCamera.name = "New Camera";
                        Camera.save(newCamera);
                        $state.reload();
                    }
                });
            });

            canvas.on('object:selected', function (options) {
                console.log("Selected: " + options.target.left + " - " + options.target.id);
            });

            for (var i = 0; i < cameraData.length; ++i) {
                drawCamera(canvas, cameraData[i], i);
            }
        }

        function drawCamera(canvas, camera, index) {
            var rect = new fabric.Rect({
                left: camera.x * grid,
                top: camera.y * grid,
                fill: 'blue',
                width: grid,
                height: grid,
                lockRotation: true,
                lockScalingX: true,
                lockScalingY: true,
                hasControls: false,
                id: index
            });

            canvas.add(rect);
        }

    }
})();
