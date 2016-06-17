(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('ChooseEntityController', ChooseEntityController);

    ChooseEntityController.$inject = ['$uibModalInstance', 'entity', 'FabricMapDialog'];

    function ChooseEntityController ($uibModalInstance, entity, FabricMapDialog) {
        var vm = this;

        vm.addCamera = function () {
            FabricMapDialog.addCamera(entity.map, entity.x, entity.y).result.then(function (result) {
                $uibModalInstance.close(result);
            });
        };

        vm.addPlayer = function () {
            FabricMapDialog.addPlayer(entity.map, entity.x, entity.y).result.then(function (result) {
                $uibModalInstance.close(result);
            });
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();

