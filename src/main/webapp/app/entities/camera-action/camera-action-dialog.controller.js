(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('CameraActionDialogController', CameraActionDialogController);

    CameraActionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CameraAction'];

    function CameraActionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CameraAction) {
        var vm = this;
        vm.cameraAction = entity;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('thefirstorderApp:cameraActionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.cameraAction.id !== null) {
                CameraAction.update(vm.cameraAction, onSaveSuccess, onSaveError);
            } else {
                CameraAction.save(vm.cameraAction, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
