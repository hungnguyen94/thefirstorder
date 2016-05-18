(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('TimePointDialogController', TimePointDialogController);

    TimePointDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TimePoint', 'Cue'];

    function TimePointDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TimePoint, Cue) {
        var vm = this;
        vm.timePoint = entity;
        vm.cues = Cue.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('thefirstorderApp:timePointUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.timePoint.id !== null) {
                TimePoint.update(vm.timePoint, onSaveSuccess, onSaveError);
            } else {
                TimePoint.save(vm.timePoint, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
