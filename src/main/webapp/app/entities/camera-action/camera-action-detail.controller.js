(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('CameraActionDetailController', CameraActionDetailController);

    CameraActionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'CameraAction', 'Cue', 'Camera'];

    function CameraActionDetailController($scope, $rootScope, $stateParams, entity, CameraAction, Cue, Camera) {
        var vm = this;
        vm.cameraAction = entity;
        
        var unsubscribe = $rootScope.$on('thefirstorderApp:cameraActionUpdate', function(event, result) {
            vm.cameraAction = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
