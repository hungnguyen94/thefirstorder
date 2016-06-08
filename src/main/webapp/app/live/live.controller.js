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

        vm.current = 0;

        vm.previous = previous;
        vm.next = next;
        vm.cues = Cue.query();

        vm.activities = [];

        JhiTrackerService.receive().then(null, null, function(activity) {
            showActivity(activity);
            console.log("Activity: ", activity);
            if (activity.page == 'next') {
                vm.current++;
                alert("At bar: " + vm.current);
            }
            if (activity.page == 'previous') {
                vm.current--;
                alert("At bar: " + vm.current);
            }
        });

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
