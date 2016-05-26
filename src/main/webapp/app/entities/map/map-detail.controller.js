(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('MapDetailController', MapDetailController);

    MapDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Map'];

    function MapDetailController($scope, $rootScope, $stateParams, entity, Map) {
        var vm = this;
        vm.map = entity;
        
        var unsubscribe = $rootScope.$on('thefirstorderApp:mapUpdate', function(event, result) {
            vm.map = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
