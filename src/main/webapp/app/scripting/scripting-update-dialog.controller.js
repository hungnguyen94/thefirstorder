(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('ScriptingUpdateDialogController', ScriptingUpdateDialogController);

    ScriptingUpdateDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity'];

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
