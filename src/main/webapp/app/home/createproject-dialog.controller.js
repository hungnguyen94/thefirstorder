(function () {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('CreateProjectDialogController', CreateProjectDialogController);

    CreateProjectDialogController.$inject = ['$timeout', '$scope', '$uibModalInstance', '$q', 'entity', 'Project', 'Script', 'Map', 'ProjectManager'];

    function CreateProjectDialogController($timeout, $scope, $uibModalInstance, $q, entity, Project, Script, Map, ProjectManager) {
        var vm = this;
        vm.project = entity;

        vm.scripts = Script.query({filter: 'project-is-null'});
        $q.all([vm.project.$promise, vm.scripts.$promise]).then(function () {
            if (!vm.project.script || !vm.project.script.id) {
                return $q.reject();
            }
            return Script.get({id: vm.project.script.id}).$promise;
        }).then(function (script) {
            vm.scripts.push(script);
        });

        vm.maps = Map.query({filter: 'project-is-null'});
        $q.all([vm.project.$promise, vm.maps.$promise]).then(function () {
            if (!vm.project.map || !vm.project.map.id) {
                return $q.reject();
            }
            return Map.get({id: vm.project.map.id}).$promise;
        }).then(function (map) {
            vm.maps.push(map);
        });

        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        /**
         * Saves the created project.
         */
        vm.save = function () {
            vm.isSaving = true;
            Project.save(vm.project, onSaveSuccess, onSaveError);
        };

        /**
         * Closes the dialog after successfully saving the project.
         * @param result should be the saved project.
         */
        function onSaveSuccess(result) {
            $scope.$emit('thefirstorderApp:projectUpdate', result);
            vm.isSaving = false;
            loadProject(result.id);
        }

        /**
         * Do nothing when saving the project fails.
         */
        function onSaveError() {
            vm.isSaving = false;
        }

        /**
         * Closes the dialog after successfully loading the created project.
         * @param result should be the loaded project.
         */
        var onLoadSuccess = function (result) {
            $scope.$emit('thefirstorderApp:projectLoad', result);
            $uibModalInstance.close(result);
            vm.isLoading = false;
        };

        /**
         * Do nothing when loading the project fails.
         */
        var onLoadError = function () {
            vm.isLoading = false;
        };

        /**
         * Sets the created project as the current project of the active user.
         * @param projectId should be the id of the project
         */
        function loadProject(projectId) {
            vm.isLoading = true;
            if (projectId !== null) {
                ProjectManager.update(projectId).then(onLoadSuccess, onLoadError);
            } else {
                vm.clear();
            }
        }

        /**
         * Closes the dialog without committing the changes.
         */
        vm.clear = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
