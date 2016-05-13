(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('TimelineController', TimelineController);

    TimelineController.$inject = ['$scope', '$state', 'Cue', 'AlertService'];

    /**
     * Controller for the timeline.
     * @param $scope scope of the timeline
     * @param $state state of the timeline
     * @param Cue the cue entity
     * @param AlertService the alert service.
     * @constructor
     */
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

        /**
         * Draws the timeline with given cues.
         * @param cues the cues to draw
         */
        function drawTimeline(cues) {
            var htmlCanvas = document.getElementById('concertTimeline');

            // Resize the timeline to the size of the parent
            resizeToParent(htmlCanvas);

            var canvas = new fabric.Canvas('concertTimeline');
            var interval = 25;

            // Draw the grid of the timeline
            drawInterval(canvas, interval);
        }

        /**
         * Draws interval lines on the timeline.
         * @param canvas the canvas to draw to
         * @param interval the distance between lines
         */
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

        /**
         * Resizes the canvas to parent size.
         * @param canvas the canvas to resize
         */
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
