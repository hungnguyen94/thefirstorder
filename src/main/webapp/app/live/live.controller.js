(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('LiveController', LiveController);

    LiveController.$inject = ['Cue', 'JhiTrackerService'];

    /**
     * The controller for the script view.
     * @param $scope the scope of the map
     * @param $state the state of the map
     * @param Camera the camera entity
     * @param AlertService the alertservice
     * @constructor
     */
    function LiveController (Cue, JhiTrackerService) {
        var vm = this;

        // Initialize the current timeline element to the first one
        vm.current = 0;

        // Initialize the functions
        vm.previous = previous;
        vm.next = next;
        vm.authorize = authorize;

        // Query all cues
        vm.cues = Cue.query();

        // Initialize the activities to an empty array
        vm.activities = [];
        vm.authorized = [];

        /**
         * Authorizes a certain ip address to make changes to the page.
         * @param ip: The IP address that should be authorized
         */
        function authorize(ip) {
            vm.authorized.push(ip);
        }

        /**
         * Recognizes an incoming websocket message and handles it.
         */
        JhiTrackerService.receive().then(null, null, function(activity) {
            showActivity(activity);

            if (activity.page === 'next' && vm.authorized.includes(activity.ipAddress)) {
                if (vm.current != vm.cues.length - 1) {
                    vm.current++;
                }
                scrollToTimelineElement(true);
            }
            if (activity.page === 'previous' && vm.authorized.includes(activity.ipAddress)) {
                if (vm.current > 0) {
                    vm.current--;
                }
                scrollToTimelineElement(false);
            }
        });

        /**
         * Lets the webbrowser focus on the current timeline element.
         * @param isNext states whether the next cue needs to be shown or not
         */
        function scrollToTimelineElement(isNext) {
            var currentElement = $("#" + vm.current);

            $('html, body').animate({
                scrollTop: currentElement.offset().top
            }, 500);

            if (isNext) {
                $("#" + (vm.current - 1)).removeClass("timeline-focus");
            } else {
                $("#" + (vm.current + 1)).removeClass("timeline-focus");
            }

            currentElement.addClass("timeline-focus");
        }

        /**
         * Handles all incoming messages and distinguishes them.
         * @param activity: Incoming activity
         */
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

        /**
         * Sends a previous websocket message.
         */
        function previous() {
            JhiTrackerService.sendPrevious();
        }

        /**
         * This function sends a next websocket message.
         */
        function next() {
            JhiTrackerService.sendNext();
        }
    }
})();
