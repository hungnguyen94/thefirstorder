(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('ScriptDialogController', ScriptDialogController);

    ScriptDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Script', 'Cue'];

    function ScriptDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Script, Cue) {
        var vm = this;
        vm.script = entity;
        vm.cues = Cue.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('thefirstorderApp:scriptUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.script.id !== null) {
                Script.update(vm.script, onSaveSuccess, onSaveError);
            } else {
                Script.save(vm.script, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
