(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('ScriptingController', ScriptingController);

    ScriptingController.$inject = ['$scope', '$uibModal', 'Cue', 'ProjectManager', 'Project'];

    /**
     * The controller for the script view.
     * @param $scope the scope of the map
     * @param Cue the cue class
     * @constructor
     */
    function ScriptingController ($scope, $uibModal, Cue, ProjectManager, Project) {
        var vm = this;
        vm.selectedCamera = null;
        vm.selectedPlayer = null;
        vm.download = download;

        update();

        $scope.$watch('vm.selected', function (selected) {
            if(selected.hasOwnProperty('cameraType')) {
                vm.selectedCamera = selected;
            } else {
                vm.selectedPlayer = selected;
            }
        });

        $scope.$watch('vm.selectedEntities', function (selected) {
            vm.highlight(selected);
        });

        /**
         * Update cues by querying Cue.
         */
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

        /**
         * Opens the download dialog.
         */
        function download() {
            $uibModal.open({
                templateUrl: 'app/entities/project/project-download-dialog.html',
                controller: 'ProjectDownloadController',
                controllerAs: 'vm',
                size: 'md',
                resolve: {
                    entity: ['Project', function(Project) {
                        return Project.get({id : vm.project.id});
                    }]
                }
            }).result.then(function() {
            }, function() {
            });
        }
    }
})();
