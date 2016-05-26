(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('MapviewController', MapviewController);

    MapviewController.$inject = ['$scope', '$state', 'Camera', 'Player', 'Cue', 'AlertService'];

    /**
     * The controller for the map view.
     * @param $scope the scope of the map
     * @param $state the state of the map
     * @param Camera the camera entity
     * @param AlertService the alertservice
     * @constructor
     */
    function MapviewController ($scope, $state, Camera, Player, Cue, AlertService) {
        var vm = this;
        var grid = 15;

        vm.loadCameras = loadCameras;
        vm.loadCameras();
        vm.loadPlayers = loadPlayers;
        vm.loadCues = loadCues;
        vm.loadCues();

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

                var objectType = options.target.type;
                var objectId = options.target.id;

                var currentObject;

                // Determine whether to update a Camera or a Player object
                switch(objectType) {
                    case 'Camera':
                        currentObject = cameraData[objectId];
                        currentObject.x = options.target.left / grid;
                        currentObject.y = options.target.top / grid;
                        Camera.update(currentObject);
                        break;
                    case 'Player':
                        currentObject = playerData[objectId];
                        currentObject.x = options.target.left / grid;
                        currentObject.y = options.target.top / grid;
                        Player.update(currentObject);
                        break;
                }
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

                        var newObject;

                        // Determine wether to initialize a Camera or a Player
                        switch(document.getElementById('selectObjectType').value) {
                            case 'Camera':
                                newObject = new Camera();
                                break;
                            case 'Player':
                                newObject = new Player();
                                break;
                        }

                        // Set the coordinates of the object to the coordinates where the mouse has been clicked
                        newObject.x = gridPosX;
                        newObject.y = gridPosY;

                        // Get the name for the object from the form
                        var name = document.getElementById('nameNewObject').value;;

                        // Set name to Undefined when no name has been filled in
                        if (name == '')
                            name = 'Undefined';

                        // Set the name of the new object to the name fetched from the form
                        newObject.name = name;

                        switch(document.getElementById('selectObjectType').value) {
                            case 'Camera':
                                Camera.save(newObject);
                                break;
                            case 'Player':
                                Player.save(newObject);
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
                drawObject(canvas, cameraData[i], i, 'blue', 'Camera');
            }

            for (var i = 0; i < playerData.length; ++i) {
                console.log(playerData[i])
                drawObject(canvas, playerData[i], i, 'green', 'Player');
            }
        }

        function parseIntAsYear(year) {
            var current = "";

            for (var j = 0; j < 4 - year.toString().length; ++j)
                current += '0';

            current += year.toString();

            return current;
        }

        function loadCues () {
            Cue.query({

            }, onSuccess, onError);

            function onSuccess(data, headers) {
                vm.cues = data;
                vm.queryCount = vm.totalItems;
                // DOM element where the Timeline will be attached
                var container = document.getElementById('visualization');

                // Create a DataSet using the cues from the database
                var dataSet = [];
                for (var i = 0; i < vm.cues.length; ++i) {
                    var startTime = vm.cues[i].timePoint.startTime;
                    var endTime = vm.cues[i].timePoint.startTime + vm.cues[i].timePoint.duration;

                    var startYear = parseIntAsYear(startTime);
                    var endYear = parseIntAsYear(endTime);

                    dataSet.push({
                        id: vm.cues[i].id,
                        content: "Cue " + vm.cues[i].id,
                        start: startYear + "-01-01",
                        end: endYear + '-01-01'
                    })
                }

                var items = new vis.DataSet(dataSet);

                // Configuration for the Timeline
                var options = {
                    'timeAxis' : {scale: 'year', step: 1},
                    'min': '0000-01-01',
                    'zoomMin': 63072000000,
                    'zoomMax': 700000000000
                };

                // Create a Timeline
                var timeline = new vis.Timeline(container, items, options);
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        /**
         * Draws a single camera.
         * @param canvas the canvas to draw to
         * @param camera the camera to draw
         * @param index the index of the camera
         */
        function drawObject(canvas, object, index, color, type) {
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
                id: index,
                type: type
            });

            canvas.add(rect);
        }

    }
})();
