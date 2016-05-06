(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('MapviewController', MapviewController);

    MapviewController.$inject = ['$scope', '$state', 'Camera', 'AlertService'];

    function MapviewController ($scope, $state, Camera, AlertService) {
        var vm = this;
        vm.loadCamera = loadCamera;
        vm.loadCamera();
        
        function loadCamera () {
            Camera.query({

            }, onSuccess, onError);
            
            function onSuccess(data, headers) {
                vm.cameras = data;
                vm.queryCount = vm.totalItems;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }
    }
})();
