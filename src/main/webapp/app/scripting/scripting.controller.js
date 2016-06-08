(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('ScriptingController', ScriptingController);

    ScriptingController.$inject = ['$scope', 'Cue'];

    /**
     * The controller for the script view.
     * @param $scope the scope of the map
     * @param Cue the cue class
     * @constructor
     */
    function ScriptingController ($scope, Cue) {
        var vm = this;
        vm.selectedCamera = null;
        vm.selectedPlayer = null;

        update();

        $scope.$watch('vm.selected', function (selected) {
            if(selected.hasOwnProperty('cameraType')) {
                vm.selectedCamera = selected;
            } else {
                vm.selectedPlayer = selected;
            }
        });

        /**
         * Update cues by cuerying Cue.
         */
        function update() {
            Cue.query({}, function (result) {
                vm.cues = result;
            });
        }
    }
})();
