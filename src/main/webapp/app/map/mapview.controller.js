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

            var rect = new fabric.Rect({
                left: 100,
                top: 100,
                fill: 'red',
                width: 20,
                height: 20
            });

            var rect2 = new fabric.Rect({
                left: 120,
                top: 100,
                fill: '#990000',
                width: 20,
                height: 20
            });

            canvas.add(rect);
            canvas.add(rect2);
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
