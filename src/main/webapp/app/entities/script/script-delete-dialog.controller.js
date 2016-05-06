(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('ScriptDeleteController',ScriptDeleteController);

    ScriptDeleteController.$inject = ['$uibModalInstance', 'entity', 'Script'];

    function ScriptDeleteController($uibModalInstance, entity, Script) {
        var vm = this;
        vm.script = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Script.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
