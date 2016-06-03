(function () {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('LoadProjectDialogController', LoadProjectDialogController);

    LoadProjectDialogController.$inject = ['$timeout', '$scope', '$state', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Project', 'ProjectManager', 'AlertService'];

    function LoadProjectDialogController($timeout, $scope, $state, $stateParams, $uibModalInstance, $q, entity, Project, ProjectManager, AlertService) {
        var vm = this;
        vm.project = entity;
        vm.hasProjects = false;

        vm.getProjects = getProjects;
        vm.getProjects();

        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        vm.clear = function () {
            $uibModalInstance.dismiss('cancel');
        };

        vm.create = function () {
            $uibModalInstance.dismiss('create');
        };

        vm.load = function (projectId) {
            vm.isLoading = true;
            if (projectId !== null) {
                ProjectManager.update(projectId).then(onLoadSuccess, onLoadError);
            } else {
                vm.clear();
            }
        };

        function getProjects() {
            vm.projects = Project.query({}, onSuccess, onError);

            function onSuccess(data, headers) {
                vm.hasProjects = data.length > 0;
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        var onLoadSuccess = function (result) {
            $scope.$emit('thefirstorderApp:projectLoad', result);
            $uibModalInstance.close(result);
            vm.isLoading = false;
        };

        var onLoadError = function () {
            vm.isLoading = false;
        };
    }
})();
