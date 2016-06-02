(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('ScriptingController', ScriptingController);

    ScriptingController.$inject = ['$rootScope', '$scope', '$state', 'Camera', 'Script', 'Cue', 'AlertService'];

    /**
     * The controller for the script view.
     * @param $scope the scope of the map
     * @param $state the state of the map
     * @param Camera the camera entity
     * @param AlertService the alertservice
     * @constructor
     */
    function ScriptingController ($rootScope, $scope, $state, Camera, Script, Cue, AlertService) {
        var vm = this;

        vm.saved = true;
        vm.save = function() {
            vm.saved = false;
        }

        vm.loadCameras = loadCameras;
        vm.loadCues = loadCues;
        vm.loadCameras();
        vm.loadScripts = Script.query();

        function loadCameras () {
            Camera.query({

            }, onSuccess, onError);

            function onSuccess(data, headers) {
                vm.cameras = data;
                vm.queryCount = vm.totalItems;
                vm.loadCues(data);
            }
            function onError(error) {
                AlertService.error(error.data.message);
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
                    var startTime = vm.cues[i].bar;
                    var endTime = vm.cues[i].bar + vm.cues[i].duration;

                    var startYear = parseIntAsYear(startTime);
                    var endYear = parseIntAsYear(endTime);

                    dataSet.push({
                        id: vm.cues[i].id,
                        content: vm.cues[i].action,
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
                    onUpdate: onUpdate,
                    onMove: unSaved,
                    onRemove: unSaved
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
                vm.saved = false;
                $state.go('scripting.new');
                var endyear = item.start.getFullYear() + 5;
                item.end = endyear + '-01-01';
                $rootScope.$on('cueadded', function (event, args) {
                    item.content = args.cuename;
                    callback(item);
                });
            }

            function onUpdate(item, callback) {
                vm.saved = false;
                $state.go('scripting.update', {name: item.content});
                $rootScope.$on('cueupdated', function (event, args) {
                    item.content = args.cuename;
                    callback(item);
                    item = null;
                });
            }

            function unSaved(item, callback) {
                vm.saved = false;
                callback(item);
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
