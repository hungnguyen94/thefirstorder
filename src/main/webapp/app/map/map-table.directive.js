(function () {
    'use strict';

    angular
        .module('thefirstorderApp')
        .directive('mapTableView', mapTableView);

    mapTableView.$inject = ['FabricMapDialog', '$timeout'];

    /**
     * The controller for the map table view. Shows all cameras and players in a table.
     * @param FabricMapDialog map dialog service
     * @param $timeout
     * @returns {{restrict: string, scope: {map: string, selected: string}, link: link, templateUrl: string, controller: string, controllerAs: string, bindToController: boolean}}
     */
    function mapTableView(FabricMapDialog, $timeout) {
        var directive = {
            restrict: 'EA',
            scope: {
                'map': '=',
                'selected': '='
            },
            link: link,
            templateUrl: 'app/map/map-table-view-template.html',
            controller: 'MapEditorController',
            controllerAs: 'vm',
            bindToController: true
        };
        return directive;

        /**
         * Links the controller to this directive.
         * @param scope the scope that the directive is called in
         */
        function link(scope) {
            scope.vm.hasCameras = true;
            scope.vm.hasPlayers = true;

            scope.vm.deleteCamera = function(cameraId) { dialogCallback(FabricMapDialog.deleteCamera(cameraId)) };
            scope.vm.deletePlayer = function(playerId) { dialogCallback(FabricMapDialog.deletePlayer(playerId)) };
            
            scope.$watch('vm.map', function (map) {
                $timeout(function () {
                    scope.vm.hasCameras = map.cameras.length > 0;
                    scope.vm.hasPlayers = map.players.length > 0;
                });
            });

            function dialogCallback(func) {
                func.result.then(function () {
                    scope.vm.update();
                });
            }
        }
    }
})();
