(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('CameraDeleteController',CameraDeleteController);

    CameraDeleteController.$inject = ['$uibModalInstance', 'entity', 'Camera'];

    function CameraDeleteController($uibModalInstance, entity, Camera) {
        var vm = this;
        vm.camera = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Camera.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
