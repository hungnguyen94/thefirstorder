(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('CameraDialogController', CameraDialogController);

    CameraDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Camera', 'Project', 'Map'];

    function CameraDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Camera, Project, Map) {
        var vm = this;
        vm.camera = entity;
        vm.projects = Project.query();
        vm.maps = Map.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            if(vm.map !== null) {
                var response = Map.addCamera({
                    id: vm.map.id, 
                    cameraId: result.id
                }); 
            }
            $scope.$emit('thefirstorderApp:cameraUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.camera.id !== null) {
                Camera.update(vm.camera, onSaveSuccess, onSaveError);
            } else {
                Camera.save(vm.camera, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
