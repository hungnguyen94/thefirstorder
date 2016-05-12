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

                var currentCamera = cameraData[options.target.id];
                currentCamera.x = options.target.left / grid;
                currentCamera.y = options.target.top / grid;
                console.log("New X: " + currentCamera.x + " New Y: " + currentCamera.y);
                Camera.update(currentCamera);

            });

            // This function definition will generate a new Camera at the clicked position
            canvas.on('mouse:up', function (options) {
                var pointer = canvas.getPointer(options.e);

                var actualPosX = pointer.x;
                var actualPosY = pointer.y;

                var gridPosX = Math.floor(actualPosX / grid);
                var gridPosY = Math.floor(actualPosY / grid);

                console.log(gridPosX + ", " + gridPosY);    // Log to console

                var newCamera = new Object();
                newCamera.x = gridPosX;
                newCamera.y = gridPosY;
                newCamera.name = "Gazorpazorpfield";
                Camera.save(newCamera);
                drawCamera(canvas, newCamera, 100);
            });

            canvas.on('object:selected', function (options) {
                console.log("Selected: " + options.target.left + " - " + options.target.id);
            });

            for (var i = 0; i < cameraData.length; ++i) {
                drawCamera(canvas, cameraData[i], i);
            }
        }

        function drawCamera(canvas, camera, index) {
            var rect = new fabric.Rect({
                left: camera.x*15,
                top: camera.y*15,
                fill: 'blue',
                width: 15,
                height: 15,
                lockRotation: true,
                lockScalingX: true,
                lockScalingY: true,
                hasControls: false,
                id: index
            });

            canvas.add(rect);
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
