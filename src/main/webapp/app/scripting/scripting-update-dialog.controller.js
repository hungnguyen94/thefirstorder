(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('ScriptingUpdateDialogController', ScriptingUpdateDialogController);

    ScriptingUpdateDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity'];

    /**
     * The controller for the state that updates a cue.
     * @param $timeout
     * @param $scope
     * @param $stateParams
     * @param $uibModalInstance
     * @param entity the cue that is updated
     * @constructor
     */
    function ScriptingUpdateDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity) {
        var vm = this;
        vm.cue = entity;

        $scope.cuename = $stateParams.name;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        vm.save = function () {
            vm.isSaving = true;
            $scope.$emit('cueupdated', {cuename: vm.cue.name});
            $uibModalInstance.dismiss();
            vm.isSaving = false;
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
