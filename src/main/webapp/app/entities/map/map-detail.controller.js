(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('MapDetailController', MapDetailController);

    MapDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Map', 'Project', 'Player', 'Camera'];

    function MapDetailController($scope, $rootScope, $stateParams, entity, Map, Project, Player, Camera) {
        var vm = this;
        vm.map = entity;
        
        var unsubscribe = $rootScope.$on('thefirstorderApp:mapUpdate', function(event, result) {
            vm.map = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
