(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('MapEditorController', MapEditorController);

    MapEditorController.$inject = ['$scope', '$state', 'Map', 'Project', 'Camera', 'Player'];

    /**
     * The controller for the map editor state.
     * @param $scope
     * @param $state
     * @param Map
     * @param Project
     * @param Camera
     * @param Player
     * @constructor
     */
    function MapEditorController ($scope, $state, Map, Project, Camera, Player) {
        console.log('MapViewController scope: ', $scope);
        console.log('MapViewController this: ', this);
        var vm = this;
        vm.selected = null;

        vm.setSelected = setSelected;
        vm.update = getMapEntities;
        getMapEntities();

        /**
         * Loads all the map entities (players and cameras).
         */
        function getMapEntities() {
            Map.getDTO({id: 1}, function (result) {
                console.log('result is: ', result);
                vm.map = result;
            });
        }

        /**
         * Sets the selected entity to a given value.
         * @param entity the entity that is selected
         * @returns {*|null}
         */
        function setSelected(entity) {
            if(vm.selected == entity) {
                vm.selected = null;
            } else {
                vm.selected = entity;
            }
            console.log('Selected entity: ', vm.selected);
            return vm.selected;
        }
    }
})();
