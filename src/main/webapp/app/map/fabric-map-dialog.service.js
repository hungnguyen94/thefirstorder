(function() {
    'use strict';
    angular
        .module('thefirstorderApp')
        .factory('FabricMapDialog', FabricMapDialog);

    FabricMapDialog.$inject = ['$uibModal'];

    function FabricMapDialog ($uibModal) {
        var services =  {
            addPlayer: addPlayer,
            addCamera: addCamera,
            deletePlayer: deletePlayer,
            deleteCamera: deleteCamera
        };
        return services;


        /**
         * Opens the delete player dialog and deletes the player on submit.
         * @param playerId the id of the player to delete
         */
        function deletePlayer(playerId) {
            return $uibModal.open({
                templateUrl: 'app/entities/player/player-delete-dialog.html',
                controller: 'PlayerDeleteController',
                controllerAs: 'vm',
                size: 'md',
                resolve: {
                    entity: ['Player', function(Player) {
                        return Player.get({id : playerId});
                    }]
                }
            });
        }

        /**
         * Opens the delete camera dialog and deletes the player on submit.
         * @param cameraId the id of the camera to delete
         */
        function deleteCamera(cameraId) {
            return $uibModal.open({
                templateUrl: 'app/entities/camera/camera-delete-dialog.html',
                controller: 'CameraDeleteController',
                controllerAs: 'vm',
                size: 'md',
                resolve: {
                    entity: ['Camera', function(Camera) {
                        return Camera.get({id : cameraId});
                    }]
                }
            });
        }

        /**
         * Opens the add player dialog and adds a player on submit.
         */
        function addPlayer(map) {
            return $uibModal.open({
                templateUrl: 'app/map/player-map-dialog.html',
                controller: 'PlayerDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'xs',
                resolve: {
                    entity: function () {
                        return {
                            name: null,
                            x: null,
                            y: null,
                            id: null,
                            map: map
                        };
                    }
                }
            });
        }

        /**
         * Opens the add camera dialog and adds a camera on submit.
         */
        function addCamera(map) {
            return $uibModal.open({
                templateUrl: 'app/map/camera-map-dialog.html',
                controller: 'CameraDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'xs',
                resolve: {
                    entity: function () {
                        return {
                            name: null,
                            x: null,
                            y: null,
                            id: null,
                            angle: 0,
                            map: map
                        };
                    }
                }
            });
        }
    }
})();

