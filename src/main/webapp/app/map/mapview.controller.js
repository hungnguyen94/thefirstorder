(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('MapviewController', MapviewController);

    MapviewController.$inject = ['$scope', '$state', 'Camera', 'Player', 'AlertService'];

    /**
     * The controller for the map view.
     * @param $scope the scope of the map
     * @param $state the state of the map
     * @param Camera the camera entity
     * @param AlertService the alertservice
     * @constructor
     */
    function MapviewController ($scope, $state, Camera, Player, AlertService) {
        var vm = this;
        var grid = 15;

        vm.loadCameras = loadCameras;
        vm.loadCameras();
        vm.loadPlayers = loadPlayers;

        function loadCameras () {
            Camera.query({

            }, onSuccess, onError);

            function onSuccess(data, headers) {
                vm.cameras = data;
                vm.queryCount = vm.totalItems;
                vm.loadPlayers(data);
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadPlayers(cameraData) {
            Player.query({

            }, onSuccess, onError);

            function onSuccess(data, headers) {
                vm.players = data;
                vm.queryCount = vm.totalItems;
                drawCameras(cameraData, data);
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        /**
         * Draws the cameras to the map.
         * @param cameraData the data of the cameras to draw
         */
        function drawCameras(cameraData, playerData) {
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

                        var name = document.getElementById('nameNewObject').value;;

                        if (name == '')
                            name = 'Undefined';

                        newCamera.name = name;

                        switch(document.getElementById('selectObjectType').value) {
                            case 'Camera':
                                Camera.save(newCamera);
                                break;
                            case 'Player':
                                Player.save(newCamera);
                                break;
                        }
                        
                        $state.reload();
                    }
                });
            });

            canvas.on('object:selected', function (options) {
                console.log("Selected: " + options.target.left + " - " + options.target.id);
            });

            for (var i = 0; i < cameraData.length; ++i) {
                drawObject(canvas, cameraData[i], i, 'blue');
            }

            for (var i = 0; i < playerData.length; ++i) {
                console.log(playerData[i])
                drawObject(canvas, playerData[i], i, 'green');
            }
        }

        /**
         * Draws a single camera.
         * @param canvas the canvas to draw to
         * @param camera the camera to draw
         * @param index the index of the camera
         */
        function drawObject(canvas, object, index, color) {
            var rect = new fabric.Rect({
                left: object.x * grid,
                top: object.y * grid,
                fill: color,
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
