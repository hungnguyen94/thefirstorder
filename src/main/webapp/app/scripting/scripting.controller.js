(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('ScriptingController', ScriptingController);

    ScriptingController.$inject = ['$rootScope', '$scope', '$state', 'Map', 'Cue', 'AlertService'];

    /**
     * The controller for the script view.
     * @param $scope the scope of the map
     * @param $state the state of the map
     * @param Camera the camera entity
     * @param AlertService the alertservice
     * @constructor
     */
    function ScriptingController ($rootScope, $scope, $state, Map, Cue, AlertService) {
        var vm = this;

        getMapEntities();

        function getMapEntities() {
            Map.getDTO({id: 1}, function (result) {
                console.log('result is: ', result);
                vm.map = result;
            });
            vm.cues = Cue.query();
        }
    }
})();
