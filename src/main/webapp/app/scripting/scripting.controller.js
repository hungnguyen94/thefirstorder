(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('ScriptingController', ScriptingController);

    ScriptingController.$inject = ['$rootScope', '$scope', '$state', 'Map', 'Cue', 'AlertService'];

    /**
     * The controller for the script view.
     * @param $scope the scope of the map
     * @param $state the state of the map
     * @param Camera the camera entity
     * @param AlertService the alertservice
     * @constructor
     */
    function ScriptingController ($rootScope, $scope, $state, Map, Cue, AlertService) {
        var vm = this;

        vm.saved = true;
        vm.save = function() {
            vm.saved = false;
        }

        vm.cues = Cue.query();
        $scope.$watch('vm.map', function (newMap) {
            console.log('Changed map:', newMap);
            drawTimeline($scope.vm.map);
        });
        
        /**
         * Draws the timeline with all the cues.
         */
        function drawTimeline(map) {
            var container = document.getElementById('visualization');

            var groups = createGroups(map);
            var items = createItems();

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

        /**
         * Creates a vis.DataSet with all the groups for the timeline.
         * @returns vis.DataSet with timeline groups
         */
        function createGroups(map) {
            var groupsArray = [];
            map.cameras.forEach(function (camera) {
                var classNumber = camera.id % 3 + 1;
                groupsArray.push({
                    content: camera.name,
                    id: camera.name,
                    value: camera.id,
                    className: "camera" + classNumber
                });
            });
            return new vis.DataSet(groupsArray);
        }

        /**
         * Creates a vis.DataSet with all the cues to draw to the timeline.
         * @returns vis.DataSet with cues
         */
        function createItems() {
            var dataSet = [];
            vm.cues.forEach(function (cue) {
                var startTime = cue.bar;
                var endTime = cue.bar + cue.duration;

                var startYear = parseIntAsYear(startTime);
                var endYear = parseIntAsYear(endTime);

                dataSet.push({
                    id: cue.id,
                    content: cue.action,
                    start: startYear + "-01-01",
                    end: endYear + '-01-01',
                    group: cue.camera.name
                })
            });

            return new vis.DataSet(dataSet);
        }

        /**
         * Moves to the scripting.new state and updates item on input from that state.
         * @param item the item that is updated
         * @param callback the callback to the vis.js draw function
         */
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

        /**
         * Moves to the scripting.update state and updates item on input from that state.
         * @param item the item that is updated
         * @param callback the callback to the vis.js draw function
         */
        function onUpdate(item, callback) {
            vm.saved = false;
            $state.go('scripting.update', {name: item.content});
            $rootScope.$on('cueupdated', function (event, args) {
                item.content = args.cuename;
                callback(item);
                item = null;
            });
        }

        /**
         * Sets the save state to unsaved.
         * @param item the item that was updated
         * @param callback the callback to the vis.js draw function
         */
        function unSaved(item, callback) {
            vm.saved = false;
            callback(item);
        }

        /**
         * Parses an int and returns the correct string for date parsing.
         * @param year the year to parse
         * @returns a string with a year
         */
        function parseIntAsYear(year) {
            var current = "";

            for (var j = 0; j < 4 - year.toString().length; ++j)
                current += '0';

            current += year.toString();

            return current;
        }
    }
})();
