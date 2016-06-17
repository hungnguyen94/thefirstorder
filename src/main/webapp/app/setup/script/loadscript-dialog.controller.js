(function () {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('LoadScriptController', LoadScriptController);

    LoadScriptController.$inject = ['$timeout', '$scope', '$uibModalInstance', 'currentProject', 'Script', 'Project', 'AlertService'];

    function LoadScriptController($timeout, $scope, $uibModalInstance, currentProject, Script, Project, AlertService) {
        var vm = this;
        vm.hasScripts = false;

        vm.getScripts = getScripts;
        vm.getScripts();

        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        /**
         * Closes the dialog without committing the changes.
         */
        vm.clear = function () {
            $uibModalInstance.dismiss('cancel');
        };

        /**
         * Closes the dialog and requests the parent state to open a create script dialog.
         */
        vm.create = function () {
            $uibModalInstance.dismiss('create');
        };

        /**
         * Sets the given script as the script of the current project of the active user.
         * @param scriptId should be the id of the script
         */
        vm.load = function (scriptId) {
            vm.isLoading = true;
            Script.get({id: scriptId}, onLoadSuccess, onLoadError);
        };

        /**
         * Closes the dialog after successfully saving the script.
         * @param result should be the saved script.
         */
        function onLoadSuccess(result) {
            currentProject.script = result;
            Project.update(currentProject);

            $scope.$emit('thefirstorderApp:scriptLoad', result);
            $uibModalInstance.close(result);
            vm.isLoading = false;
        }

        /**
         * Do nothing when saving the script fails.
         */
        function onLoadError() {
            vm.isLoading = false;
        }

        /**
         * Loads all scripts from the database,
         *  setting hasScripts to true if there are scripts in the database.
         */
        function getScripts() {
            vm.scripts = Script.queryNoProject({}, onSuccess, onError);

            function onSuccess(data) {
                vm.hasScripts = data.length > 0;
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }
    }
})();
