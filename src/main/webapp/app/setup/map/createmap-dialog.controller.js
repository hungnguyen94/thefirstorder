(function () {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('CreateMapController', CreateMapController);

    CreateMapController.$inject = ['$timeout', '$scope', 'entity', '$uibModalInstance', 'Map', 'Project', 'currentProject'];

    function CreateMapController($timeout, $scope, entity, $uibModalInstance, Map, Project, currentProject) {
        var vm = this;
        vm.map = entity;

        $timeout(function () {
            angular.element('.form-group:eq(0)>input').focus();
        });

        /**
         * Saves the created map.
         */
        vm.save = function () {
            vm.isSaving = true;
            Map.save(vm.map, onSaveSuccess, onSaveError);
        };

        /**
         * Closes the dialog after successfully saving the created map,
         *  and setting that map as the map of the current project of the active user.
         * @param result should be the created map.
         */
        function onSaveSuccess(result) {
            currentProject.map = result;
            Project.update(currentProject);

            $scope.$emit('thefirstorderApp:mapUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        /**
         * Do nothing when saving the map fails.
         */
        function onSaveError() {
            vm.isSaving = false;
        }

        /**
         * Closes the dialog without committing the changes.
         */
        vm.clear = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
