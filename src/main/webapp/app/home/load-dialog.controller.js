(function () {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('LoadDialogController', LoadDialogController);

    LoadDialogController.$inject = ['$timeout', '$scope', '$state', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Project', 'ProjectManager'];

    function LoadDialogController($timeout, $scope, $state, $stateParams, $uibModalInstance, $q, entity, Project, ProjectManager) {
        var vm = this;
        vm.isLoading = false;
        vm.project = entity;

        vm.loadAllProjects = loadAllProjects;
        vm.loadAllProjects();

        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        vm.clear = function () {
            $uibModalInstance.dismiss('cancel');
        };

        vm.load = function (projectId) {
            vm.isLoading = true;
            console.log("loading project...");
            if (projectId !== null) {
                ProjectManager.update(projectId);
                onLoadSuccess();
            } else {
                vm.clear();
            }
        };

        var onLoadSuccess = function (result) {
            $scope.$emit('thefirstorderApp:projectUpdate', result);
            $uibModalInstance.close(result);
            vm.isLoading = false;
        };

        var onLoadError = function () {
            console.log("not able to load project");
            vm.isLoading = false;
        };

        function loadAllProjects() {
            Project.query({}, onSuccess, onError);

            function onSuccess(data, headers) {
                vm.projects = data;
                vm.queryCount = vm.totalItems;
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }
    }
})();
