(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('CameraDetailController', CameraDetailController);

    CameraDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Camera', 'Map'];

    function CameraDetailController($scope, $rootScope, $stateParams, entity, Camera, Map) {
        var vm = this;
        vm.camera = entity;
        
        var unsubscribe = $rootScope.$on('thefirstorderApp:cameraUpdate', function(event, result) {
            vm.camera = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
