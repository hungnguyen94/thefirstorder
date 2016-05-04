(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('PlayerDialogController', PlayerDialogController);

    PlayerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Player', 'Cue'];

    function PlayerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Player, Cue) {
        var vm = this;
        vm.player = entity;
        vm.cues = Cue.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('thefirstorderApp:playerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.player.id !== null) {
                Player.update(vm.player, onSaveSuccess, onSaveError);
            } else {
                Player.save(vm.player, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
