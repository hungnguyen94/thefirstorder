(function () {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('MapEditorController', MapEditorController);

    MapEditorController.$inject = ['Map', 'Project', 'ProjectManager'];

    function MapEditorController(Map, Project, ProjectManager) {
        var vm = this;
        vm.selected = null;

        vm.setSelected = setSelected;
        vm.update = getMapEntities;
        getMapEntities();

        function getMapEntities() {
            ProjectManager.get().then(function (projectId) {
                Project.get({id: projectId.data}, function (project) {

                    Map.getDTO({id: project.id}, function (result) {
                        vm.map = result;
                    });
                });
            });
        }

        function setSelected(entity) {
            if (vm.selected == entity) {
                vm.selected = null;
            } else {
                vm.selected = entity;
            }
            console.log('Selected entity: ', vm.selected);
            return vm.selected;
        }
    }
})();
