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
            controller: 'MapEditorController',
            controllerAs: 'vm',
            bindToController: true
        };
        return directive;
    }
})();

