(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('TimelineController', TimelineController);

    TimelineController.$inject = ['$scope', '$state', 'Cue', 'AlertService'];

    function TimelineController ($scope, $state, Cue, AlertService) {
        var vm = this;
        var grid = 15;

        vm.loadCues = loadCues;
        vm.loadCues();

        function loadCues () {
            Cue.query({

            }, onSuccess, onError);

            function onSuccess(data, headers) {
                vm.cues = data;
                vm.queryCount = vm.totalItems;
                drawTimeline(data);
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function drawTimeline(cues) {
            var htmlCanvas = document.getElementById('concertTimeline');

            // Resize the timeline to the size of the parent
            resizeToParent(htmlCanvas);

            var canvas = new fabric.Canvas('concertTimeline');
            var interval = 25;

            // Draw the grid of the timeline
            drawInterval(canvas, interval);
        }

        function drawInterval(canvas, interval) {
            var x = 0;
            var line = null;
            var rect = [];

            for (var i = 0; i < Math.ceil(canvas.width / interval); ++i) {
                // Instantiate an array representing the line (drawn as rectangle)
                rect[0] = i * Math.ceil(canvas.width / interval);
                rect[1] = 0;
                rect[2] = i * Math.ceil(canvas.width / interval);
                rect[3] = canvas.height;

                // Overwrite line from the previous loop
                line = null;
                line = new fabric.Line(rect, {
                    stroke: 'black'
                });

                line.selectable = false;
                canvas.add(line);
                line.sendToBack();
            }
            canvas.renderAll;
        }

        function resizeToParent(canvas) {
            // Resize it to the parents' size
            canvas.style.width = '100%';
            canvas.style.height = '100%';

            // Now set the internal size, so it will not appear blurry
            canvas.width = canvas.offsetWidth;
            canvas.height = canvas.offsetHeight;
        }
    }
})();
