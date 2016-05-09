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
                drawCameras(data);
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function drawCameras(cameraData) {
            // Initialize the canvas
            var canvas = document.getElementById("canvas");
            var ctx = canvas.getContext("2d");
            for (var i = 0; i < cameraData.length; ++i) {
                ctx.fillStyle = "#00b0ff";
                var currentCamera = cameraData[i];
                ctx.beginPath();
                ctx.arc(currentCamera.x, currentCamera.y, 5,0,2*Math.PI);
                ctx.stroke();
                // ctx.fillRect(cameraData[i].x, cameraData[i].y, 3, 3);
            }
        }

    }
})();
