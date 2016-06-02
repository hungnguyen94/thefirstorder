(function () {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('LoadMapController', LoadMapController);

    LoadMapController.$inject = ['$timeout', '$scope', '$uibModalInstance', 'currentProject', 'Map', 'Project', 'AlertService'];

    function LoadMapController($timeout, $scope, $uibModalInstance, currentProject, Map, Project, AlertService) {
        var vm = this;
        vm.hasMaps = false;

        vm.getMaps = getMaps;
        vm.getMaps();

        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        vm.clear = function () {
            $uibModalInstance.dismiss('cancel');
        };

        vm.create = function () {
            $uibModalInstance.dismiss('create');
        };

        vm.load = function (mapId) {
            vm.isLoading = true;
            Map.get({id: mapId}, onLoadSuccess, onLoadError);
        };

        function getMaps() {
            vm.maps = Map.query({}, onSuccess, onError);

            function onSuccess(data, headers) {
                vm.hasMaps = data.length > 0;
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        var onLoadSuccess = function (result) {
            currentProject.map = result;
            Project.update(currentProject);

            $scope.$emit('thefirstorderApp:mapLoad', result);
            $uibModalInstance.close(result);
            vm.isLoading = false;
        };

        var onLoadError = function () {
            vm.isLoading = false;
        };
    }
})();
