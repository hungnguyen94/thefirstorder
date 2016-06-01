(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('CueDetailController', CueDetailController);

    CueDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Cue', 'Script', 'TimePoint', 'Player', 'Camera', 'Project'];

    function CueDetailController($scope, $rootScope, $stateParams, entity, Cue, Script, TimePoint, Player, Camera, Project) {
        var vm = this;
        vm.cue = entity;
        
        var unsubscribe = $rootScope.$on('thefirstorderApp:cueUpdate', function(event, result) {
            vm.cue = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
