(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .directive('mapTableView', mapTableView);

    mapTableView.$inject = ['$uibModal'];

    /**
     * The controller for the map table view. Shows all cameras and players in a table.
     * @param $uibModal
     * @returns {{restrict: string, scope: {map: string, selected: string}, templateUrl: string, link: link, controller: string, controllerAs: string, bindToController: boolean}}
     */
    function mapTableView($uibModal) {
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
            scope.vm.addCamera = addCamera;
            scope.vm.addPlayer = addPlayer;
            scope.vm.deleteCamera = deleteCamera;
            scope.vm.deletePlayer = deletePlayer;

            /**
             * Opens the delete player dialog and deletes the player on submit.
             * @param playerId the id of the player to delete
             */
            function deletePlayer(playerId) {
                $uibModal.open({
                    templateUrl: 'app/entities/player/player-delete-dialog.html',
                    controller: 'PlayerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Player', function(Player) {
                            return Player.get({id : playerId});
                        }]
                    }
                }).result.then(function(result) {
                    scope.vm.update();
                }, function() {
                    callback(null);
                });
            }

            /**
             * Opens the delete camera dialog and deletes the player on submit.
             * @param cameraId the id of the camera to delete
             */
            function deleteCamera(cameraId) {
                $uibModal.open({
                    templateUrl: 'app/entities/camera/camera-delete-dialog.html',
                    controller: 'CameraDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Camera', function(Camera) {
                            return Camera.get({id : cameraId});
                        }]
                    }
                }).result.then(function(result) {
                    scope.vm.update();
                }, function() {
                    callback(null);
                });
            }

            /**
             * Opens the add player dialog and adds a player on submit.
             */
            function addPlayer() {
                $uibModal.open({
                    templateUrl: 'app/entities/player/player-dialog.html',
                    controller: 'PlayerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                x: null,
                                y: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function(result) {
                    scope.vm.update();
                }, function() {
                    callback(null);
                });
            }

            /**
             * Opens the add camera dialog and adds a camera on submit.
             */
            function addCamera () {
                $uibModal.open({
                    templateUrl: 'app/entities/camera/camera-dialog.html',
                    controller: 'CameraDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                x: null,
                                y: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function(result) {
                    scope.vm.update();
                }, function() {
                    callback(null);
                });
            }
        }
    }
})();

