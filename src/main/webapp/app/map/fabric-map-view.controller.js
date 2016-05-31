(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('FabricMapViewController', FabricMapViewController);

    FabricMapViewController.$inject = ['$scope', 'MapService'];

    function FabricMapViewController ($scope, MapService) {
        console.log('This is the fabric-map-view controller');
        var vm = this;
        vm.canvas = $scope.canvas;
        vm.draw = $scope.draw;
        console.log('Scope is: ', $scope);

        vm.transformCamera = transformCamera;
        vm.transformPlayer = transformPlayer;

        // MapService.get({mapId: vm.mapId}, function (result) {
        //     console.log('result is: ', result);
        //     vm.cameras = result.cameras;
        //     vm.players = result.players;
        //     var drawableCameras = vm.cameras.map(transformCamera);
        //     var drawablePlayers = vm.players.map(transformPlayer);
        //     vm.drawables = drawableCameras.concat(drawablePlayers);
        //     console.log('Drawables: ', vm.drawables);
        //     $scope.draw(vm.drawables);
        // });

        function transformCamera(camera) {
            var rect = new fabric.Rect({
                left: camera.x,
                top: camera.y,
                fill: 'red'
            });
            return rect;
        }

        function transformPlayer(player) {
            var rect = new fabric.Rect({
                left: player.x,
                top: player.y,
                fill: 'blue'
            });
            return rect;
        }
    }

})();
