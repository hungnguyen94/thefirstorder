(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('TimelineController', TimelineController);

    TimelineController.$inject = ['$scope', '$state', 'Cue', 'AlertService'];

    function TimelineController ($scope, $state, Cue, AlertService) {
        var vm = this;
        var grid = 15;

        vm.loadCamera = loadCamera;
        vm.loadCamera();

        function loadCues () {
            Cue.query({

            }, onSuccess, onError);

            function onSuccess(data, headers) {
                vm.cues = data;
                vm.queryCount = vm.totalItems;
                drawTimelin(data);
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function drawTimeline(cues) {
            var canvas = new fabric.canvas('concertTimeline');
            var interval = 25;


        }

        function drawGrid(canvas, interval) {
            var x = 0;
            var line = null;

            while (x < canvas.width) {
                line = null;
            }
        }
    }
})();
