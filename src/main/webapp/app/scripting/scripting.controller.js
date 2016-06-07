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
        console.log('Scripting controller scope: ', $scope);
        console.log('Scripting controller this: ', vm);
        
        update();

        function update() {
            Cue.query({}, function (result) {
                vm.cues = result;
            });
        }
    }
})();
