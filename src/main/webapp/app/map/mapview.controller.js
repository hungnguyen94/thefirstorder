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
            var canvas = new fabric.Canvas('concertMap');

            var grid = 15;

            canvas.on('object:moving', function (options) {
                options.target.set({
                    left: Math.round(options.target.left / grid) * grid,
                    top: Math.round(options.target.top / grid) * grid
                });
            });

            for (var i = 0; i < cameraData.length; ++i) {
                var currentCamera = cameraData[i];

                var rect = new fabric.Rect({
                    left: currentCamera.x*grid,
                    top: currentCamera.y*grid,
                    fill: 'red',
                    width: grid,
                    height: grid,
                    lockRotation: true,
                    lockScalingX: true,
                    lockScalingY: true,
                    hasControls: false
                });

                canvas.add(rect);
            }
        }

        function drawGrid(amountWidth, amountHeight) {
            var parent = document.getElementById('mapGrid');
            var width = parent.style.width;
            var height = parent.style.height;

            var squareLength = width / amountWidth;

            var childDiv = document.createElement('div');
            childDiv.style.height  = squareLength+ 'px';
            childDiv.style.width  = squareLength + 'px';
            childDiv.style.border = '1px solid black';
            parent.appendChild(childDiv);
        }

    }
})();
