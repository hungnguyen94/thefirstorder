(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('ScriptingController', ScriptingController);

    ScriptingController.$inject = ['$scope', 'Cue', 'ProjectManager', 'Project'];

    /**
     * The controller for the script view.
     * @param $scope the scope of the map
     * @param Cue the cue class
     * @constructor
     */
    function ScriptingController ($scope, Cue, ProjectManager, Project) {
        var vm = this;
        vm.selectedCamera = null;
        vm.selectedPlayer = null;

        update();

        $scope.$watch('vm.selected', function (selected) {
            if(selected.hasOwnProperty('cameraType')) {
                vm.selectedCamera = selected;
            } else {
                vm.selectedPlayer = selected;
            }
        });

        function update() {
            ProjectManager.get().then(function (projectId) {
                Project.get({id: projectId.data}, function (project) {
                    vm.project = project;
                    vm.script = project.script;
                    Cue.query({scriptId: project.script.id}, function (result) {
                        vm.cues = result;
                    });
                });
            });
        }
    }
})();
