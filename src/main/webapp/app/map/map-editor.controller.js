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

        /**
         * Gets the entities within the map by getting the map of the current project of the active user.
         */
        function getMapEntities() {
            ProjectManager.get().then(function (projectId) {

                Project.get({id: projectId.data}, function (project) {
                    Map.getDTO({id: project.map.id}, function (result) {
                        vm.map = result;
                    });
                });
            });
        }

        /**
         * Sets the entity selected by the user.
         */
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
