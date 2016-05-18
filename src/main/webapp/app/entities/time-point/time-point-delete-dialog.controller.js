(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('TimePointDeleteController',TimePointDeleteController);

    TimePointDeleteController.$inject = ['$uibModalInstance', 'entity', 'TimePoint'];

    function TimePointDeleteController($uibModalInstance, entity, TimePoint) {
        var vm = this;
        vm.timePoint = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            TimePoint.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
