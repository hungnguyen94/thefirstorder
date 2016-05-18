(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('TimePointDetailController', TimePointDetailController);

    TimePointDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'TimePoint', 'Cue'];

    function TimePointDetailController($scope, $rootScope, $stateParams, entity, TimePoint, Cue) {
        var vm = this;
        vm.timePoint = entity;
        
        var unsubscribe = $rootScope.$on('thefirstorderApp:timePointUpdate', function(event, result) {
            vm.timePoint = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
