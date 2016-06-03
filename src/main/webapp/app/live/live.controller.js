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
        vm.cues = Cue.query();
    }
})();
