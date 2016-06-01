(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('MapViewController', MapViewController);

    MapViewController.$inject = ['$scope', '$state', 'Map', 'Project', 'currentProject'];

    function MapViewController ($scope, $state, Map, Project, currentProject) {
        console.log('MapViewController scope: ', $scope);
        console.log('MapViewController this: ', this);
        var vm = this;

        getMapEntities();

        // $scope.fabric = {};
        // $scope.FabricConstants = FabricConstants;
        
        function getMapEntities() {
            Map.getDTO({id: 1}, function (result) {
                console.log('result is: ', result);
                vm.eenwillekeurigemap = result;
            });
        }

        // $scope.$watch('vm.gekozen', function (newVal) {
        //     console.log('vm.gekozen is: ', newVal);
        //     vm.gekozen = newVal;
        //    
        // });
        //
        //
    }
})();
