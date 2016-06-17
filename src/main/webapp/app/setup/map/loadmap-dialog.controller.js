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

        /**
         * Closes the dialog without committing the changes.
         */
        vm.clear = function () {
            $uibModalInstance.dismiss('cancel');
        };

        /**
         * Closes the dialog and requests the parent state to open a create map dialog.
         */
        vm.create = function () {
            $uibModalInstance.dismiss('create');
        };

        /**
         * Sets the given map as the map of the current project of the active user.
         * @param mapId should be the id of the map
         */
        vm.load = function (mapId) {
            vm.isLoading = true;
            Map.get({id: mapId}, onLoadSuccess, onLoadError);
        };

        /**
         * Closes the dialog after successfully saving the map.
         * @param result should be the saved map.
         */
        function onLoadSuccess(result) {
            currentProject.map = result;
            Project.update(currentProject);

            $scope.$emit('thefirstorderApp:mapLoad', result);
            $uibModalInstance.close(result);
            vm.isLoading = false;
        }

        /**
         * Do nothing when saving the map fails.
         */
        function onLoadError() {
            vm.isLoading = false;
        }

        /**
         * Loads all maps from the database,
         *  setting hasMaps to true if there are maps in the database.
         */
        function getMaps() {
            vm.maps = Map.queryNoProject({}, onSuccess, onError);

            function onSuccess(data) {
                vm.hasMaps = data.length > 0;
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }
    }
})();
