(function () {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('CreateProjectDialogController', CreateProjectDialogController);

    CreateProjectDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Project', 'Script', 'Map', 'Player', 'Camera', 'ProjectManager'];

    function CreateProjectDialogController($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Project, Script, Map, Player, Camera, ProjectManager) {
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
        vm.players = Player.query();
        vm.cameras = Camera.query();

        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('thefirstorderApp:projectUpdate', result);
            vm.isSaving = false;
            vm.load(result.id);
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.project.id !== null) {
                Project.update(vm.project, onSaveSuccess, onSaveError);
            } else {
                Project.save(vm.project, onSaveSuccess, onSaveError);
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
    }
})();
