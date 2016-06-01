(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('FabricMapController', FabricMapController);

    FabricMapController.$inject = ['$scope'];

    function FabricMapController ($scope) {
        console.log('This is the fabric-map controller');
        console.log('Scope is: ', $scope);

        var vm = this;
        // vm.canvas = $scope.canvas;

        // $scope.$watch('vm.map', function (newVal) {
        //     console.log('newVal: ', newVal);
        //     // $scope.draw(vm.map.cameras, vm.map.players);
        // });
    }

})();
