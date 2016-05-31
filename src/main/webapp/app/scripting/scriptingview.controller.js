(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('ScriptingviewController', ScriptingviewController);

    ScriptingviewController.$inject = ['$scope', '$state', 'Camera', 'Player', 'CameraAction', 'Script', 'TimePoint', 'Cue', 'AlertService'];

    /**
     * The controller for the script view.
     * @param $scope the scope of the map
     * @param $state the state of the map
     * @param Camera the camera entity
     * @param AlertService the alertservice
     * @constructor
     */
    function ScriptingviewController ($scope, $state, Camera, Player, CameraAction, Script, TimePoint, Cue, AlertService) {
        var vm = this;
        var grid = 15;

        vm.loadCameras = loadCameras;
        vm.loadCameras();
        vm.loadPlayers = loadPlayers;
        vm.loadCues = loadCues;
        vm.loadCues();
        vm.loadCameraActions = loadCameraActions;
        vm.loadCameraActions();
        vm.loadScripts = loadScripts;
        vm.loadScripts();
        vm.loadTimePoints = loadTimePoints;
        // vm.loadTimePoints();

        function loadTimePoints () {
            TimePoint.query({

            }, onSuccess, onError);

            function onSuccess(data, headers) {
                vm.timePoints = data;
                vm.queryCount = vm.totalItems;
                vm.loadPlayers(data);
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

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

        function loadCameraActions() {
            CameraAction.query({

            }, onSuccess, onError);

            function onSuccess(data, headers) {
                vm.cameraActions = data;
                vm.queryCount = vm.totalItems;
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadScripts() {
            Script.query({

            }, onSuccess, onError);

            function onSuccess(data, headers) {
                vm.scripts = data;
                vm.queryCount = vm.totalItems;
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

            draw_grid(grid, canvas);

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

            // When selecting a square in the gridview, update the select boxes
            canvas.on('object:selected', function (options) {
                var id = options.target.id;
                var type = options.target.type;

                switch(type) {
                    case 'Camera':
                        var selectBox = document.getElementById('selectCamera');
                        selectBox.value = id + 1;
                        break;
                    case 'Player':
                        var selectBox = document.getElementById('selectPlayer');
                        selectBox.value = id + 1;
                        break;
                }
            });

            for (var i = 0; i < cameraData.length; ++i) {
                drawObject(canvas, cameraData[i], i, 'blue', 'Camera');
            }

            for (var i = 0; i < playerData.length; ++i) {
                console.log(playerData[i])
                drawObject(canvas, playerData[i], i, 'white', 'Player');
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

                // Create groups
                var groups = new vis.DataSet([
                    {"content": "Camera 1", "id": "Camera 1", "value": 1},
                    {"content": "Camera 2", "id": "Camera 2", "value": 2},
                    {"content": "Camera 3", "id": "Camera 3", "value": 3}
                ])

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
                        end: endYear + '-01-01',
                        group: "Camera 1"
                    })
                }

                var items = new vis.DataSet(dataSet);

                // Configuration for the Timeline
                var options = {
                    'groupOrder': function (a, b) {
                        return a.value - b.value;
                    },
                    'timeAxis' : {scale: 'year', step: 1},
                    'start': '0000-01-01',
                    'end': '0010-01-01',
                    'zoomMin': 63072000000,
                    'zoomMax': 700000000000,
                    'editable': true,
                    'stack': false
                };

                // Create a Timeline
                var timeline = new vis.Timeline(container);
                timeline.setOptions(options);
                timeline.setGroups(groups);
                timeline.setItems(items);

                timeline.on('click', function (properties) {
                    var startTime = parseIntAsYear(properties.time.getFullYear());
                    var duration = document.getElementById('durationCue').value;

                    // Initialize new time point
                    var timePoint = new Object();
                    timePoint.startTime = startTime;
                    timePoint.duration = duration;

                    // Retrieve the rest of the objects
                    var player = Player.get({id: document.getElementById('selectPlayer').value});
                    var camera = Camera.get({id: document.getElementById('selectCamera').value});
                    var cameraAction = CameraAction.get({id: document.getElementById('selectCameraAction').value});
                    var script = Script.get({id: document.getElementById('selectScript').value});

                    player.id = document.getElementById('selectPlayer').value;
                    camera.id = document.getElementById('selectCamera').value;
                    cameraAction.id = document.getElementById('selectCameraAction').value;
                    script.id = document.getElementById('selectScript').value;

                    // Save the time point to the database
                    var temp = TimePoint.save(timePoint);

                    // Reload all timepoints, so the newly added one is in the memory
                    vm.loadTimePoints();

                    timeline.on('click', function (properties) {

                        vm.loadTimePoints();
                        // Initialize new cue
                        var cue = new Object();

                        cue.player = player;
                        cue.camera = camera;
                        cue.cameraAction = cameraAction;
                        cue.script = script;
                        cue.timePoint = vm.timePoints.pop();

                        console.log("Test: ", vm.timePoints.length);

                        // Add the Cue to the database
                        Cue.save(cue);
                        $state.reload();
                    });
                });
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
                lockMovementX: true,
                lockMovementY: true,
                hasControls: false,
                id: index,
                type: type
            });

            canvas.add(rect);
        }

        /**
         * Draws a grid on the canvas
         * @param gridsize Size of the blocks in the grid
         * @param canvas The canvas to draw the grid on
         */
        function draw_grid(gridsize, canvas) {
            for(var x = 0; x < (canvas.width / gridsize); x++)
            {
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
    }
})();
