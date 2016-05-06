(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('PlayerDetailController', PlayerDetailController);

    PlayerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Player', 'Cue'];

    function PlayerDetailController($scope, $rootScope, $stateParams, entity, Player, Cue) {
        var vm = this;
        vm.player = entity;
        
        var unsubscribe = $rootScope.$on('thefirstorderApp:playerUpdate', function(event, result) {
            vm.player = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
