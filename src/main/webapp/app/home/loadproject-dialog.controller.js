(function () {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('LoadProjectDialogController', LoadProjectDialogController);

    LoadProjectDialogController.$inject = ['$timeout', '$scope', '$state', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Project', 'ProjectManager'];

    function LoadProjectDialogController($timeout, $scope, $state, $stateParams, $uibModalInstance, $q, entity, Project, ProjectManager) {
        var vm = this;
        vm.project = entity;

        vm.projects = Project.query();

        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        vm.clear = function () {
            $uibModalInstance.dismiss('cancel');
        };

        vm.load = function (projectId) {
            vm.isLoading = true;
            if (projectId !== null) {
                ProjectManager.update(projectId).then(onLoadSuccess, onLoadError);
            } else {
                vm.clear();
            }
        };

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
