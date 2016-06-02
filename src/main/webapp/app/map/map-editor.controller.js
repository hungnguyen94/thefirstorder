(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('MapEditorController', MapEditorController);

    MapEditorController.$inject = ['$scope', '$state', 'Map', 'Project', 'Camera', 'Player'];

    function MapEditorController ($scope, $state, Map, Project, Camera, Player) {
        console.log('MapViewController scope: ', $scope);
        console.log('MapViewController this: ', this);
        var vm = this;
        vm.selected = null;
        
        vm.setSelected = setSelected;
        vm.update = getMapEntities;
        getMapEntities();

        function getMapEntities() {
            Map.getDTO({id: 1}, function (result) {
                console.log('result is: ', result);
                // vm.eenwillekeurigemap = result;
                vm.map = result;
            });
        }

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
