(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .directive('timeline', timeline);

    timeline.$inject = ['Cue', 'Camera', '$uibModal'];

    /**
     * The controller for the timeline directive.
     * @param Cue
     * @param Camera
     * @param $uibModal
     * @returns {{scope: {map: string, selected: string}, restrict: string, link: link, controller: string, controllerAs: string, bindToController: boolean}}
     */
    function timeline(Cue, Camera, $uibModal) {
        var directive = {
            scope: {
                'map': '=',
                'selected': '='
            },
            restrict: 'EA',
            link: link,
            controller: 'ScriptingController',
            controllerAs: 'vm',
            bindToController: true
        };
        return directive;

        /**
         * Links the controller to the correct directive.
         * @param scope the current scope
         * @param element the element that the directive was called from
         * @param attrs the attributes of the element
         */
        function link(scope, element, attrs) {
            scope.timeline = {};
            scope.vm.timelineSelected = {};
            scope.dataset = new vis.DataSet();

            init();

            scope.$watch('vm.map', function (newMap) {
                drawTimelineCameras(scope.vm.map.cameras);
            });

            scope.$watch('vm.cues', function (newCues) {
                createItems(scope.vm.cues);
            });

            scope.timeline.on('select', function (properties) {
                var selected = properties.items;
                var selectedItems = scope.dataset.get({
                    filter: function (item) {
                        return selected.indexOf(item.id) > -1;
                    }
                });
                console.log('selected items: ', selectedItems);
            });
            
            scope.timeline.on('timechanged', function (event) {
                var time = event.time;
                var selectedItems = scope.dataset.get({
                    filter: function (item) {
                        console.log('item is: ', item);
                        return time >= new Date(item.start) && time <= new Date(item.end);
                    }
                });
                console.log('items on bar: ', selectedItems);
            });

            /**
             * Draws the timeline with all the cues.
             */
            function drawTimelineCameras(cameras) {
                var groups = createGroups(cameras);
                scope.timeline.setGroups(groups);
            }

            /**
             * Initializes the timeline.
             */
            function init() {
                var options = {
                    groupOrder: function (a, b) {
                        return a.value - b.value;
                    },
                    timeAxis : {scale: 'year', step: 1},
                    min: '0000-12-31',
                    start: '0000-12-31',
                    end: '0010-12-31',
                    zoomMin: 63072000000,
                    zoomMax: 700000000000,
                    editable: true,
                    stack: false,
                    itemsAlwaysDraggable: true,
                    onAdd: onAdd,
                    onUpdate: onUpdate,
                    onMove: onMove,
                    onRemove: onRemove
                };

                // Create a Timeline
                var timeline = new vis.Timeline(element[0]);
                timeline.setOptions(options);
                timeline.addCustomTime('0000-12-31', 'scroller');
                timeline.setItems(scope.dataset);
                scope.timeline = timeline;
            }

            /**
             * Creates a vis.DataSet with all the groups for the timeline.
             * @returns vis.DataSet with timeline groups
             */
            function createGroups(cameras) {
                var groupsArray = [];
                cameras.forEach(function (camera) {
                    var classNumber = camera.id % 3 + 1;
                    groupsArray.push({
                        content: camera.name,
                        id: camera.id,
                        value: camera.id,
                        className: "camera" + classNumber,
                        camera: camera
                    });
                });
                return new vis.DataSet(groupsArray);
            }

            /**
             * Adds all cues to the dataset by transforming them into
             * items first.
             */
            function createItems(cues) {
                var items = cues.map(transformCueToItem);
                console.log('Cues: ', items);
                scope.dataset.add(items);
            }

            /**
             * Transforms a Cue to a Vis timeline item.
             * @param cue The cue to be transformed
             * @returns Vis item
             */
            function transformCueToItem(cue) {
                var startTime = cue.bar;
                var endTime = cue.bar + cue.duration;

                var startYear = parseIntAsDate(startTime);
                var endYear = parseIntAsDate(endTime);

                var item = {
                    id: cue.id,
                    content: cue.action,
                    start: startYear,
                    end: endYear,
                    group: cue.camera.id,
                    cue: cue
                };
                return item;
            }

            /**
             * Moves to the scripting.new state and updates item on input from that state.
             * @param item the item that is updated
             * @param callback the callback to the vis.js draw function
             */
            function onAdd(item, callback) {
                Camera.get({id: item.group}, function (camera) {
                    scope.vm.selectedCamera = camera;
                    $uibModal.open({
                        templateUrl: 'app/scripting/scripting-new-dialog.html',
                        controller: 'CueDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    action: null,
                                    bar: item.start.getFullYear(),
                                    duration: 1,
                                    id: null,
                                    camera: scope.vm.selectedCamera,
                                    player: scope.vm.selectedPlayer, 
                                    script: scope.vm.script, 
                                    project: scope.vm.project
                                };
                            }
                        }
                    }).result.then(function(result) {
                        var item = transformCueToItem(result);
                        callback(item);
                    }, function() {
                        callback(null);
                    });
                });
            }

            /**
             * Moves to the scripting.update state and updates item on input from that state.
             * @param item the item that is updated
             * @param callback the callback to the vis.js draw function
             */
            function onUpdate(item, callback) {
                $uibModal.open({
                    templateUrl: 'app/scripting/scripting-new-dialog.html',
                    controller: 'CueDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'md',
                    resolve: {
                        entity: item.cue
                    }
                }).result.then(function(result) {
                    var end = result.bar + result.duration;
                    item.content = result.action;
                    item.start = parseIntAsDate(result.bar);
                    item.end = parseIntAsDate(end);
                    item.cue = result;
                    callback(item);
                }, function() {
                    callback(null);
                });
            }

            /**
             * Updates the cue in the backend when moving a cue in the timeline.
             * @param item the item that is updated
             * @param callback the callback to the vis.js draw function
             */
            function onMove(item, callback) {
                var bar = item.start.getFullYear();
                var duration = item.end.getFullYear() - bar;
                item.cue.bar = bar;
                item.cue.duration = duration;
                Camera.get({id: item.group}, function (camera) {
                    item.cue.camera = camera;
                    Cue.update(item.cue, function () {
                        callback(item);
                    });
                });

            }

            /**
             * Moves to the cue.delete state and deletes the item if the user accepts deletion.
             * @param item
             * @param callback
             */
            function onRemove(item, callback) {
                $uibModal.open({
                    templateUrl: 'app/entities/cue/cue-delete-dialog.html',
                    controller: 'CueDeleteController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'md',
                    resolve: {
                        entity: item.cue
                    }
                }).result.then(function(result) {
                    callback(item);
                }, function() {
                    callback(null);
                });
            }

            /**
             * Parses an int and returns the correct string for date parsing.
             * @param year the year to parse
             * @returns a string with a year
             */
            function parseIntAsDate(year) {
                var str = "" + year;
                var padding = "0000";
                var result = padding.substring(0, padding.length - str.length) + str;
                return result + '-12-31';
            }
        }
    }
})();

