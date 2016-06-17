(function () {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('ScriptingController', ScriptingController);

    ScriptingController.$inject = ['$scope', '$uibModal', 'Cue', 'ProjectManager', 'Project'];

    /**
     * The controller for the script view.
     * @param $scope
     * @param $uibModal
     * @param Cue
     * @param ProjectManager
     * @param Project
     * @constructor
     */
    function ScriptingController($scope, $uibModal, Cue, ProjectManager, Project) {
        var vm = this;
        vm.selectedCamera = null;
        vm.selectedPlayer = null;
        vm.download = download;

        vm.hasCamera = false;
        vm.hasPlayer = false;

        vm.hasScore = false;
        vm.hasMap = false;

        update();

        $scope.$watch('vm.selected', function (selected) {
            if (selected.hasOwnProperty('cameraType')) {
                vm.selectedCamera = selected;
                vm.hasCamera = true;
            } else {
                vm.selectedPlayer = selected;
                vm.hasPlayer = true;
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
                    if (vm.project.map) {
                        vm.hasMap = true;
                    }

                    vm.script = project.script;
                    vm.score = vm.script.score;
                    if (vm.score) {
                        vm.hasScore = true;
                        updatePDFViewer(vm.score);
                    }
                    Cue.query({scriptId: project.script.id}, function (result) {
                        vm.cues = result;
                    });
                });
            });
        }

        /**
         * Updates the pdf viewer.
         * @param url should be the url of the PDF the viewer should show.
         */
        function updatePDFViewer(url) {
            document.getElementById("pdf-viewer").setAttribute("src", url);
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
                    entity: ['Project', function (Project) {
                        return Project.get({id: vm.project.id});
                    }]
                }
            });
        }
    }
})
();
