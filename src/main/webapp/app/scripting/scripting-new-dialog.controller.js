(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('ScriptingNewDialogController', ScriptingNewDialogController);

    ScriptingNewDialogController.$inject = ['$timeout', '$scope', '$uibModalInstance', 'entity'];

    /**
     * The controller for for the state that adds a new cue.
     * @param $timeout
     * @param $scope
     * @param $uibModalInstance
     * @param entity the cue that is added
     * @constructor
     */
    function ScriptingNewDialogController ($timeout, $scope, $uibModalInstance, entity) {
        var vm = this;
        vm.cue = entity;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        vm.save = function () {
            vm.isSaving = true;
            $scope.$emit('cueadded', {cuename: vm.cue.name});
            $uibModalInstance.dismiss();
            vm.isSaving = false;
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
