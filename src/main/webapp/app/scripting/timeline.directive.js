(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .directive('timeline', timeline);

    timeline.$inject = ['$window', '$rootScope', '$state', 'Cue', '$uibModal'];

    function timeline($window, $rootScope, $state, Cue, $uibModal) {
        var directive = {
            template: '<div id="visualization"></div>', 
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

        function link(scope, element, attrs) {
            console.log('timeline directive called');
            console.log('Element is: ', element);
            console.log('Scope is: ', scope);
            
            scope.timeline = {};
            scope.vm.timelineSelected = {};
            scope.dataset = new vis.DataSet();
            
            init();

            scope.$watch('vm.map', function (newMap) {
                console.log('Changed map:', newMap);
                drawTimelineCameras(scope.vm.map.cameras);
            });

            scope.$watch('vm.cues', function (newCues) {
                console.log('Cues changed: ', newCues);
                createItems(scope.vm.cues);
                console.log('scope.dataset is: ', scope.dataset);
                console.log('timeline items: ', scope.timeline);
            });

            scope.timeline.on('select', function (properties) {
                console.log('selected timeline items: ', properties);
            });
            
            ///////////////////////////////////////////
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
                var timeline = new vis.Timeline(element[0]);
                timeline.setOptions(options);
                timeline.addCustomTime('0000-01-01', 'scroller');
                timeline.setItems(scope.dataset);
                scope.timeline = timeline;
                console.log('init called, timeline is', scope.timeline);
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
                        id: camera.name,
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
                scope.dataset.add(cues.map(transformCueToItem))
            }

            /**
             * Transforms a Cue to a Vis timeline item. 
             * @param cue The cue to be transformed
             * @returns Vis item
             */
            function transformCueToItem(cue) {
                var startTime = cue.bar;
                var endTime = cue.bar + cue.duration;

                var startYear = parseIntAsYear(startTime);
                var endYear = parseIntAsYear(endTime);

                var item = {
                    id: cue.id,
                    content: cue.action,
                    start: startYear,
                    end: endYear,
                    group: cue.camera.name,
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
                $uibModal.open({
                    templateUrl: 'app/entities/cue/cue-dialog.html',
                    controller: 'CueDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'xs',
                    resolve: {
                        entity: function () {
                            return {
                                action: null,
                                bar: null,
                                duration: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function(result) {
                    item.content = result.action;
                    item.start = parseIntAsYear(result.bar);
                    var end = result.bar + result.duration;
                    item.end = parseIntAsYear(end);
                    
                    console.log('StartYear is: ', item.start);
                    console.log('End is: ', end);
                    console.log('item.end is', item.end);
                    callback(item);
                }, function() {
                    console.log('cancelled');
                });
            }

            /**
             * Moves to the scripting.update state and updates item on input from that state.
             * @param item the item that is updated
             * @param callback the callback to the vis.js draw function
             */
            function onUpdate(item, callback) {
                console.log('item is', item);
                $uibModal.open({
                    templateUrl: 'app/entities/cue/cue-dialog.html',
                    controller: 'CueDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'md',
                    resolve: {
                        entity: item.cue
                    }
                }).result.then(function(result) {
                    console.log('result is: ', result);
                    item.content = result.action;
                    callback(item);
                }, function() {
                    console.log('cancelled');
                });
            }

            /**
             * Sets the save state to unsaved.
             * @param item the item that was updated
             * @param callback the callback to the vis.js draw function
             */
            function unSaved(item, callback) {
                callback(item);
            }

            /**
             * Parses an int and returns the correct string for date parsing.
             * @param year the year to parse
             * @returns a string with a year
             */
            function parseIntAsYear(year) {
                var str = "" + year; 
                var padding = "0000"; 
                var result = padding.substring(0, padding.length - str.length) + str; 
                
                return result;
            }
        }
    }
})();

