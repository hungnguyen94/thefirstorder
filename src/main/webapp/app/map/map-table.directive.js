(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .directive('mapTableView', mapTableView);

    mapTableView.$inject = ['FabricMapDialog'];

    /**
     * The controller for the map table view. Shows all cameras and players in a table.
     * @param $uibModal
     * @returns {{restrict: string, scope: {map: string, selected: string}, templateUrl: string, link: link, controller: string, controllerAs: string, bindToController: boolean}}
     */
    function mapTableView(FabricMapDialog) {
        var directive = {
            restrict: 'EA',
            scope: {
                'map': '=',
                'selected': '='
            },
            templateUrl: 'app/map/map-table-view-template.html',
            link: link,
            controller: 'MapEditorController',
            controllerAs: 'vm',
            bindToController: true
        };
        return directive;

        /**
         * Links the controller to this directive.
         * @param scope the scope that the directive is called in
         * @param element the element that the directive is called in
         * @param attrs the attributes of the element that the directive is called in
         */
        function link(scope, element, attrs) {
            scope.vm.addCamera = function(map) { dialogCallback(FabricMapDialog.addCamera(map)) };
            scope.vm.addPlayer = function(map) { dialogCallback(FabricMapDialog.addPlayer(map)) };
            scope.vm.deleteCamera = function(cameraId) { dialogCallback(FabricMapDialog.deleteCamera(cameraId)) };
            scope.vm.deletePlayer = function(playerId) { dialogCallback(abricMapDialog.deletePlayer(playerId)) };

            function dialogCallback(func) {
                console.log('dialog func', func);
                func.result.then(function () {
                    scope.vm.update();
                })
            }

        }
    }
})();

