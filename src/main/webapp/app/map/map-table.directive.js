(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .directive('mapTableView', mapTableView);
    
    mapTableView.$inject = ['$uibModal'];

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

        function link(scope, element, attrs) {
            console.log('Link from directive map-table-view called');
            console.log('Element is: ', element);
            console.log('Scope is: ', scope);
            
            scope.vm.addCamera = addCamera;
            scope.vm.addPlayer = addPlayer;
            scope.vm.deleteCamera = deleteCamera;
            scope.vm.deletePlayer = deletePlayer;
           
           
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
                    console.log('Deleted player result: ', result);
                    scope.vm.update();
                }, function() {
                    console.log('Cancelled delete player');
                });
            }
            
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
                    console.log('Deleted camera result: ', result);
                    scope.vm.update();
                }, function() {
                    console.log('Cancelled delete camera');
                });
            }
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
                    console.log('result add player: ', result);
                    scope.vm.update();
                }, function() {
                    console.log('Pressed cancel');
                });
            }
              
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
                    console.log('result add camera: ', result);
                    scope.vm.update();
                }, function() {
                    console.log('Pressed cancel');
                });
            }
        }
    }
})();

