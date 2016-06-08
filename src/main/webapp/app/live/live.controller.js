(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('LiveController', LiveController);

    LiveController.$inject = ['$scope', '$state', 'Cue', 'JhiTrackerService','AlertService'];

    /**
     * The controller for the script view.
     * @param $scope the scope of the map
     * @param $state the state of the map
     * @param Camera the camera entity
     * @param AlertService the alertservice
     * @constructor
     */
    function LiveController ($scope, $state, Cue, JhiTrackerService, AlertService) {
        var vm = this;

        // Initialize the current timeline element to the first one
        vm.current = 0;

        // Initialize the functions
        vm.previous = previous;
        vm.next = next;

        // Query all cues
        vm.cues = Cue.query();

        // Initialize the activities to an empty array
        vm.activities = [];

        /**
         * This callback recognizes an incoming websocket message and handles it
         */
        JhiTrackerService.receive().then(null, null, function(activity) {
            showActivity(activity);
            console.log("Activity: ", activity);
            if (activity.page == 'next') {
                if (vm.current != vm.cues.length - 1)
                    vm.current++;
                scrollToTimelineElement(true);
            }
            if (activity.page == 'previous') {
                if (vm.current > 0)
                    vm.current--;
                scrollToTimelineElement(false);
            }
        });

        /**
         * This function lets the webbrowser focus on the current timeline element
         */
        function scrollToTimelineElement(isNext) {
            var currentElement = $("#" + vm.current);

            $('html, body').animate({
                scrollTop: currentElement.offset().top
            }, 500);

            if (isNext)
                $("#" + (vm.current - 1)).removeClass("timeline-focus");
            else
                $("#" + (vm.current + 1)).removeClass("timeline-focus");

            currentElement.addClass("timeline-focus");
        }

        function showActivity(activity) {
            var existingActivity = false;
            for (var index = 0; index < vm.activities.length; index++) {
                if(vm.activities[index].sessionId === activity.sessionId) {
                    existingActivity = true;
                    if (activity.page === 'logout') {
                        vm.activities.splice(index, 1);
                    } else {
                        vm.activities[index] = activity;
                    }
                }
            }
            if (!existingActivity && (activity.page !== 'logout')) {

                vm.activities.push(activity);
            }
        }

        function previous() {
            JhiTrackerService.sendPrevious();
        }

        function next() {
            JhiTrackerService.sendNext();
        }
    }
})();
