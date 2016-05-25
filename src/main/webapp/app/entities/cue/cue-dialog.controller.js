(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('CueDialogController', CueDialogController);

    CueDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Cue', 'Script', 'CameraAction', 'TimePoint', 'Player', 'Camera', 'Project'];

    function CueDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Cue, Script, CameraAction, TimePoint, Player, Camera, Project) {
        var vm = this;
        vm.cue = entity;
        vm.scripts = Script.query();
        vm.cameraactions = CameraAction.query({filter: 'cue-is-null'});
        $q.all([vm.cue.$promise, vm.cameraactions.$promise]).then(function() {
            if (!vm.cue.cameraAction || !vm.cue.cameraAction.id) {
                return $q.reject();
            }
            return CameraAction.get({id : vm.cue.cameraAction.id}).$promise;
        }).then(function(cameraAction) {
            vm.cameraactions.push(cameraAction);
        });
        vm.timepoints = TimePoint.query({filter: 'cue-is-null'});
        $q.all([vm.cue.$promise, vm.timepoints.$promise]).then(function() {
            if (!vm.cue.timePoint || !vm.cue.timePoint.id) {
                return $q.reject();
            }
            return TimePoint.get({id : vm.cue.timePoint.id}).$promise;
        }).then(function(timePoint) {
            vm.timepoints.push(timePoint);
        });
        vm.players = Player.query();
        vm.cameras = Camera.query();
        vm.projects = Project.query();

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
