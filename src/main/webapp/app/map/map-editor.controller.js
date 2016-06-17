(function () {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('MapEditorController', MapEditorController);

    MapEditorController.$inject = ['Map', 'Project', 'ProjectManager'];

    /**
     * The controller for the map editor state.
     * @param Map
     * @param Project
     * @constructor
     */
    function MapEditorController(Map, Project, ProjectManager) {
        var vm = this;
        vm.selected = null;

        vm.setSelected = setSelected;
        vm.update = getMapEntities;

        getMapEntities();

        /**
         * Loads all the map entities (players and cameras).
         */
        function getMapEntities() {
            ProjectManager.get().then(function (projectId) {
                Project.get({id: projectId.data}, function (project) {
                    vm.project = project;
                    Map.getDTO({id: project.map.id}, function (result) {
                        vm.map = result;
                    });
                });
            });
        }

        /**
         * Sets the selected entity to a given value.
         * @param entity the entity that is selected
         * @returns {*|null}
         */
        function setSelected(entity) {
            if (vm.selected === entity) {
                vm.selected = null;
            } else {
                vm.selected = entity;
            }
            return vm.selected;
        }
    }
})();
