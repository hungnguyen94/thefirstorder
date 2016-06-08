(function () {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('LoadProjectDialogController', LoadProjectDialogController);

    LoadProjectDialogController.$inject = ['$timeout', '$scope', '$uibModalInstance', 'entity', 'Project', 'ProjectManager', 'AlertService'];

    function LoadProjectDialogController($timeout, $scope, $uibModalInstance, entity, Project, ProjectManager, AlertService) {
        var vm = this;
        vm.project = entity;
        vm.hasProjects = false;

        vm.getProjects = getProjects;
        vm.getProjects();

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
         * Closes the dialog and requests the parent state to open a create project dialog.
         */
        vm.create = function () {
            $uibModalInstance.dismiss('create');
        };

        /**
         * Sets the given project as the current project of the active user.
         * @param projectId should be the id of the project
         */
        vm.load = function (projectId) {
            vm.isLoading = true;
            if (projectId !== null) {
                ProjectManager.update(projectId).then(onLoadSuccess, onLoadError);
            } else {
                vm.clear();
            }
        };

        /**
         * Closes the dialog after successfully loading the project.
         * @param result should be the loaded project.
         */
        function onLoadSuccess(result) {
            $scope.$emit('thefirstorderApp:projectLoad', result);
            $uibModalInstance.close(result);
            vm.isLoading = false;
        }

        /**
         * Do nothing when loading the project fails.
         */
        function onLoadError() {
            vm.isLoading = false;
        }

        /**
         * Loads all projects from the database,
         *  setting hasProjects to true if there are projects in the database.
         */
        function getProjects() {
            vm.projects = Project.query({}, onSuccess, onError);

            function onSuccess(data) {
                vm.hasProjects = data.length > 0;
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

    }
})();
