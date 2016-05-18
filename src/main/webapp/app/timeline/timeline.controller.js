(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('TimelineController', TimelineController);

    TimelineController.$inject = ['$scope', '$state', 'Cue', 'Player', 'Camera', 'AlertService'];

    function TimelineController ($scope, $state, Cue, Player, Camera, AlertService) {
        var vm = this;

        var width = 120;
        var height = 60;

        vm.loadCues = loadCues;
        vm.loadCues();
        vm.loadPlayers = loadPlayers;
        vm.loadPlayers();
        vm.loadCameras = loadCameras;
        vm.loadCameras();

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

                    var startYear = '';
                    var endYear = '';
                    for (var j = 0; j < 4 - startTime.toString().length; ++j)
                        startYear += '0';

                    startYear += startTime.toString();

                    for (var j = 0; j < 4 - endTime.toString().length; ++j)
                        endYear += '0';

                    endYear += endTime.toString();

                    console.log(startYear);

                    dataSet.push({
                        id: i,
                        content: "Cue " + i,
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
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }
    }
})();
