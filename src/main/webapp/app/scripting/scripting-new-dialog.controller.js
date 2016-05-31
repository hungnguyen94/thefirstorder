(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('ScriptingNewDialogController', ScriptingNewDialogController);

    ScriptingNewDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Project', 'Script', 'Map', 'Player', 'Camera', 'Cue'];

    function ScriptingNewDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Project, Script, Map, Player, Camera, Cue) {
        var vm = this;
        vm.project = entity;
        vm.scripts = Script.query({filter: 'project-is-null'});
        $q.all([vm.project.$promise, vm.scripts.$promise]).then(function() {
            if (!vm.project.script || !vm.project.script.id) {
                return $q.reject();
            }
            return Script.get({id : vm.project.script.id}).$promise;
        }).then(function(script) {
            vm.scripts.push(script);
        });
        vm.maps = Map.query({filter: 'project-is-null'});
        $q.all([vm.project.$promise, vm.maps.$promise]).then(function() {
            if (!vm.project.map || !vm.project.map.id) {
                return $q.reject();
            }
            return Map.get({id : vm.project.map.id}).$promise;
        }).then(function(map) {
            vm.maps.push(map);
        });
        vm.players = Player.query();
        vm.cameras = Camera.query();
        vm.cues = Cue.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('thefirstorderApp:projectUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.project.id !== null) {
                Project.update(vm.project, onSaveSuccess, onSaveError);
            } else {
                Project.save(vm.project, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
