(function () {
    'use strict';

    angular
        .module('thefirstorderApp')
        .directive('mapTableView', mapTableView);

    mapTableView.$inject = ['FabricMapDialog', '$timeout'];

    /**
     * The controller for the map table view. Shows all cameras and players in a table.
     * @param $uibModal
     * @returns {{restrict: string, scope: {map: string, selected: string}, templateUrl: string, link: link, controller: string, controllerAs: string, bindToController: boolean}}
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
         * Links the controller with the directive.
         * @param scope the scope that the directive is called in
         * @param element the element that the directive is called in
         * @param attrs the attributes of the element that the directive is called in
         */
        function link(scope, element, attrs) {
            scope.vm.hasCameras = true;
            scope.vm.hasPlayers = true;

            scope.$watch('vm.map', function (map) {
                $timeout(function () {
                    scope.vm.hasCameras = map.cameras.length > 0;
                    scope.vm.hasPlayers = map.players.length > 0;
                });
            });
        }
    }
})();
