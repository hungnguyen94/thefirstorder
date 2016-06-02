(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('TimelineController', TimelineController);

    TimelineController.$inject = ['$scope', '$state', 'Cue', 'Player', 'Camera', 'CameraAction', 'Script', 'TimePoint', 'AlertService', 'currentProject', 'Project'];

    function TimelineController ($scope, $state, Cue, Player, Camera, CameraAction, Script, TimePoint, AlertService, currentProject, Project) {
        var vm = this;

        var width = 120;
        var height = 60;

        vm.loadCues = loadCues;
        vm.loadCues();
        vm.loadPlayers = loadPlayers;
        vm.loadPlayers();
        vm.loadCameras = loadCameras;
        vm.loadCameras();
        vm.loadCameraActions = loadCameraActions;
        vm.loadCameraActions();
        vm.loadScripts = loadScripts;
        vm.loadScripts();

        function loadPlayers() {
            Player.query({

            }, onSuccess, onError);

            function onSuccess(data, headers) {
                vm.players = data;
                vm.queryCount = vm.totalItems;
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadCameras() {
            Camera.query({

            }, onSuccess, onError);

            function onSuccess(data, headers) {
                vm.cameras = data;
                vm.queryCount = vm.totalItems;
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
                var timeline = new vis.Timeline(container, items, options)

                // Add callbacks for functionality to the timeline
                timeline.on('click', function (properties) {
                    var startTime = parseIntAsYear(properties.time.getFullYear());
                    var duration = document.getElementById('durationCue').value;

                    // Initialize new time point
                    var timePoint = new TimePoint();
                    timePoint.startTime = startTime;
                    timePoint.duration = duration;

                    // Retrieve the rest of the objects
                    var player = Player.get({id: document.getElementById('selectPlayer').value});
                    var camera = Camera.get({id: document.getElementById('selectCamera').value});
                    var cameraAction = CameraAction.get({id: document.getElementById('selectCameraAction').value});
                    var script = Script.get({id: document.getElementById('selectScript').value});

                    // Save the time point to the database
                    TimePoint.save(timePoint);

                    // Initialize new cue
                    var cue = new Cue();

                    // Initialize the new player
                    var newPlayer = new Player();
                    newPlayer.id = player.id;
                    newPlayer.name = player.name;
                    newPlayer.x = player.x;
                    newPlayer.y = player.y;

                    // Initialize the new camera
                    var newCamera = new Camera();
                    newCamera.id = camera.id;
                    newCamera.name = camera.name;
                    newCamera.x = camera.x;
                    newCamera.y = camera.y;

                    // Initialize the new camera action
                    var newCameraAction = new CameraAction();
                    newCameraAction.id = cameraAction.id;
                    newCameraAction.name = cameraAction.name;
                    newCameraAction.duration = cameraAction.duration;

                    // Initialize the new script
                    var newScript= new Script();
                    newScript.id = script.id;
                    newScript.name = script.name;

                    cue.player = newPlayer;
                    cue.camera = newCamera;
                    cue.cameraAction = newCameraAction;
                    cue.script = newScript;
                    cue.timePoint = timePoint;

                    console.log(player, camera, cameraAction, script, timePoint);

                    // Add the Cue to the database
                    Cue.save(cue);
                    $state.reload();
                });
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }
    }
})();
