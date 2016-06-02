(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('LiveController', LiveController);

    LiveController.$inject = ['$scope', '$state', 'Cue', 'AlertService'];

    /**
     * The controller for the script view.
     * @param $scope the scope of the map
     * @param $state the state of the map
     * @param Camera the camera entity
     * @param AlertService the alertservice
     * @constructor
     */
    function LiveController ($scope, $state, Cue, AlertService) {
        var vm = this;
        vm.loadCues = loadCues;
        vm.loadCues();

        function loadCues () {
            Cue.query({}, onSuccess, onError);

            function onSuccess(data, headers) {
                vm.cues = data;
                vm.queryCount = vm.totalItems;
                console.log(vm.cues[0]);
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }
    }
})();
