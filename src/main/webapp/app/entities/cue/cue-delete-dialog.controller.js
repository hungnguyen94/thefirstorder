(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('CueDeleteController',CueDeleteController);

    CueDeleteController.$inject = ['$uibModalInstance', 'entity', 'Cue'];

    function CueDeleteController($uibModalInstance, entity, Cue) {
        var vm = this;
        vm.cue = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Cue.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
