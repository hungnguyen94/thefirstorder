(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('MapDeleteController',MapDeleteController);

    MapDeleteController.$inject = ['$uibModalInstance', 'entity', 'Map'];

    function MapDeleteController($uibModalInstance, entity, Map) {
        var vm = this;
        vm.map = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Map.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
