(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('ScriptingController', ScriptingController);

    ScriptingController.$inject = ['$rootScope', '$scope', '$state', 'Camera', 'Player', 'Script', 'TimePoint', 'Cue', 'AlertService'];

    /**
     * The controller for the script view.
     * @param $scope the scope of the map
     * @param $state the state of the map
     * @param Camera the camera entity
     * @param AlertService the alertservice
     * @constructor
     */
    function ScriptingController ($rootScope, $scope, $state, Camera, Player, Script, TimePoint, Cue, AlertService) {
        var vm = this;
        var grid = 15;

        $scope.saved = true;

        vm.loadCameras = loadCameras;
        vm.loadCues = loadCues;
        vm.loadPlayers = loadPlayers;
        vm.loadCameras();
        vm.loadScripts = Script.query();
        vm.loadTimePoints = TimePoint.query();

        vm.save = function() {
            $scope.saved = true;
        }

        function loadCameras () {
            Camera.query({

            }, onSuccess, onError);

            function onSuccess(data, headers) {
                vm.cameras = data;
                vm.queryCount = vm.totalItems;
                vm.loadPlayers(data);
                vm.loadCues(data);
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
        }

        function loadCues (cameraData) {
            Cue.query({

            }, onSuccess, onError);

            function onSuccess(data, headers) {
                vm.cues = data;
                vm.queryCount = vm.totalItems;
                // DOM element where the Timeline will be attached
                var container = document.getElementById('visualization');

                // Create groups
                var groupsArray = [];
                for (var i = 0; i < cameraData.length; ++i) {
                    var classnr = cameraData[i].id % 3 + 1;
                    groupsArray.push({
                        content: cameraData[i].name,
                        id: cameraData[i].name,
                        value: cameraData[i].id,
                        className: "camera" + classnr
                    })
                }
                var groups = new vis.DataSet(groupsArray);


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
                        group: vm.cues[i].camera.name
                    })
                }

                var items = new vis.DataSet(dataSet);

                // Configuration for the Timeline
                var options = {
                    groupOrder: function (a, b) {
                        return a.value - b.value;
                    },
                    timeAxis : {scale: 'year', step: 1},
                    min: '0000-01-01',
                    start: '0000-01-01',
                    end: '0010-01-01',
                    zoomMin: 63072000000,
                    zoomMax: 700000000000,
                    editable: true,
                    stack: false,
                    itemsAlwaysDraggable: true,
                    onAdd: onAdd,
                    onUpdate: onUpdate
                };

                // Create a Timeline
                var timeline = new vis.Timeline(container);
                timeline.setOptions(options);
                timeline.setGroups(groups);
                timeline.setItems(items);
                timeline.addCustomTime('0000-01-01', 'scroller');
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }

            function onAdd(item, callback) {
                $scope.saved = false;
                $state.go('scripting.new');
                var endyear = item.start.getFullYear() + 5;
                item.end = endyear + '-01-01';
                $rootScope.$on('cueadded', function (event, args) {
                    item.content = args.cuename;
                    callback(item);
                });
            }

            function onUpdate(item, callback) {
                $scope.saved = false;
                console.log($scope.saved);
                $state.go('scripting.update', {name: item.content});
                $rootScope.$on('cueupdated', function (event, args) {
                    item.content = args.cuename;
                    callback(item);
                    item = null;
                });
            }
        }

        function parseIntAsYear(year) {
            var current = "";

            for (var j = 0; j < 4 - year.toString().length; ++j)
                current += '0';

            current += year.toString();

            return current;
        }
    }
})();
