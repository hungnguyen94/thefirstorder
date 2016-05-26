(function () {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('LoadDialogController', LoadDialogController);

    LoadDialogController.$inject = ['$timeout', '$scope', '$state', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Project', 'Script', 'Map', 'Player', 'Camera'];

    function LoadDialogController($timeout, $scope, $state, $stateParams, $uibModalInstance, $q, entity, Project, Script, Map, Player, Camera) {
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

        vm.loadAllProjects = loadAllProjects;
        vm.loadAllProjects();

        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        vm.clear = function () {
            $uibModalInstance.dismiss('cancel');
        };

        vm.open = function (projectId) {
            $uibModalInstance.dismiss();
        };

        function loadAllProjects() {
            Project.query({}, onSuccess, onError);

            function onSuccess(data, headers) {
                vm.projects = data;
                console.log(vm.projects);
                vm.queryCount = vm.totalItems;
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }
    }
})();
