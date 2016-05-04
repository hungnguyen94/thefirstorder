(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('CueDialogController', CueDialogController);

    CueDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Cue', 'Script'];

    function CueDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Cue, Script) {
        var vm = this;
        vm.cue = entity;
        vm.scripts = Script.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('thefirstorderApp:cueUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.cue.id !== null) {
                Cue.update(vm.cue, onSaveSuccess, onSaveError);
            } else {
                Cue.save(vm.cue, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
