(function () {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('CreateScriptController', CreateScriptController);

    CreateScriptController.$inject = ['$timeout', '$scope', 'entity', '$uibModalInstance', 'Script', 'Project', 'currentProject'];

    function CreateScriptController($timeout, $scope, entity, $uibModalInstance, Script, Project, currentProject) {
        var vm = this;
        vm.script = entity;

        $timeout(function () {
            angular.element('.form-group:eq(0)>input').focus();
        });

        /**
         * Saves the created script.
         */
        vm.save = function () {
            vm.isSaving = true;
            Script.save(vm.script, onSaveSuccess, onSaveError);
        };

        /**
         * Closes the dialog after successfully saving the created script,
         *  and setting that script as the script of the current project of the active user.
         * @param result should be the created script.
         */
        function onSaveSuccess(result) {
            currentProject.script = result;
            Project.update(currentProject);

            $scope.$emit('thefirstorderApp:mapUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        /**
         * Do nothing when saving the script fails.
         */
        function onSaveError() {
            vm.isSaving = false;
        }

        /**
         * Closes the dialog without committing the changes.
         */
        vm.clear = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
