(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('MapViewController', MapViewController);

    MapViewController.$inject = ['$scope', '$state', 'Map', 'Project'];

    function MapViewController ($scope, $state, Map, Project) {
        console.log('MapViewController scope: ', $scope);
        console.log('MapViewController this: ', this);
        var vm = this;
        
        vm.setSelected = setSelected;

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
