(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('CreateMapController', CreateMapController);

    CreateMapController.$inject = ['$timeout', '$scope', 'entity', '$uibModalInstance', 'Map', 'Project', 'currentProject'];

    function CreateMapController ($timeout, $scope, entity, $uibModalInstance, Map, Project, currentProject) {
        var vm = this;
        vm.map = entity;

        $timeout(function (){
            angular.element('.form-group:eq(0)>input').focus();
        });

        var onSaveSuccess = function (result) {
            currentProject.map = result;
            Project.update(currentProject);

            $scope.$emit('thefirstorderApp:mapUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            Map.save(vm.map, onSaveSuccess, onSaveError);
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
