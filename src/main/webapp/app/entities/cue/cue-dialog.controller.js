(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('CueDialogController', CueDialogController);

    CueDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Cue', 'Script', 'Player', 'Camera', 'CameraAction'];

    function CueDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Cue, Script, Player, Camera, CameraAction) {
        var vm = this;
        vm.cue = entity;
        vm.scripts = Script.query();
        vm.players = Player.query({filter: 'cue-is-null'});
        $q.all([vm.cue.$promise, vm.players.$promise]).then(function() {
            if (!vm.cue.player || !vm.cue.player.id) {
                return $q.reject();
            }
            return Player.get({id : vm.cue.player.id}).$promise;
        }).then(function(player) {
            vm.players.push(player);
        });
        vm.cameras = Camera.query({filter: 'cue-is-null'});
        $q.all([vm.cue.$promise, vm.cameras.$promise]).then(function() {
            if (!vm.cue.camera || !vm.cue.camera.id) {
                return $q.reject();
            }
            return Camera.get({id : vm.cue.camera.id}).$promise;
        }).then(function(camera) {
            vm.cameras.push(camera);
        });
        vm.cameraactions = CameraAction.query({filter: 'cue-is-null'});
        $q.all([vm.cue.$promise, vm.cameraactions.$promise]).then(function() {
            if (!vm.cue.cameraAction || !vm.cue.cameraAction.id) {
                return $q.reject();
            }
            return CameraAction.get({id : vm.cue.cameraAction.id}).$promise;
        }).then(function(cameraAction) {
            vm.cameraactions.push(cameraAction);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('thefirstorderApp:cueUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.cue.id !== null) {
                Cue.update(vm.cue, onSaveSuccess, onSaveError);
            } else {
                Cue.save(vm.cue, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
