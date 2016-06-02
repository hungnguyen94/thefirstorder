(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('CreateScriptController', CreateScriptController);

    CreateScriptController.$inject = ['$timeout', '$scope', 'entity', '$uibModalInstance', 'Script', 'Project', 'currentProject'];

    function CreateScriptController ($timeout, $scope, entity, $uibModalInstance, Script, Project, currentProject) {
        var vm = this;
        vm.script = entity;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            currentProject.script = result;
            Project.update(currentProject);

            $scope.$emit('thefirstorderApp:mapUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            Script.save(vm.script, onSaveSuccess, onSaveError);
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
