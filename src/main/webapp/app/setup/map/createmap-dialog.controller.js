(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('CreateMapController', CreateMapController);

    CreateMapController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Map', 'Project'];

    function CreateMapController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Map, Project) {
        var vm = this;
        vm.map = entity;
        vm.projects = Project.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
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
            // TODO Set map of project
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
