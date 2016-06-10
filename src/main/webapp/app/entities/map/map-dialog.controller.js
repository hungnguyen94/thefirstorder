(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('MapDialogController', MapDialogController);

    MapDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Map', 'Project', 'Player', 'Camera'];

    function MapDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Map, Project, Player, Camera) {
        var vm = this;
        vm.map = entity;
        vm.projects = Project.query();
        vm.players = Player.query();
        vm.cameras = Camera.query();

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
            if (vm.map.id !== null) {
                Map.update(vm.map, onSaveSuccess, onSaveError);
            } else {
                Map.save(vm.map, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
