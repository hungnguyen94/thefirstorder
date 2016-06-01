(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .directive('mapTableView', mapTableView);

    function mapTableView() {
        var directive = {
            restrict: 'EA', 
            templateUrl: 'app/map/map-table-view-template.html',
            link: link,
            scope: {
                map: '='
            }, 
            controller: mapTableViewController, 
            controllerAs: 'vm', 
            bindToController: true
        };
        return directive;

        function link(scope, element, attrs) {
            console.log('Link from directive map-table-view called');
            console.log('Element is: ', element);
            console.log('Scope is: ', scope);
        }
    }

    mapTableViewController.$inject = ['$scope'];

    function mapTableViewController ($scope) {
        console.log('This is the map-table controller: ', $scope);
        var vm = this;
        vm.setSelected = setSelected;
        
        vm.isCamerasCollapsed = true;
        vm.isPlayersCollapsed = true;
        
        console.log('Scope is: ', $scope);

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

