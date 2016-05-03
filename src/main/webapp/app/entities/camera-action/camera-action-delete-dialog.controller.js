(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('CameraActionDeleteController',CameraActionDeleteController);

    CameraActionDeleteController.$inject = ['$uibModalInstance', 'entity', 'CameraAction'];

    function CameraActionDeleteController($uibModalInstance, entity, CameraAction) {
        var vm = this;
        vm.cameraAction = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            CameraAction.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
