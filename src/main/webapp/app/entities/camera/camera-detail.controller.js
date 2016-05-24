(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('CameraDetailController', CameraDetailController);

    CameraDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Camera', 'Project'];

    function CameraDetailController($scope, $rootScope, $stateParams, entity, Camera, Project) {
        var vm = this;
        vm.camera = entity;
        
        var unsubscribe = $rootScope.$on('thefirstorderApp:cameraUpdate', function(event, result) {
            vm.camera = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
