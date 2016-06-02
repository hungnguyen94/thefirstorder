(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .directive('mapTableView', mapTableView);
    
    mapTableView.$inject = ['$uibModal'];

    function mapTableView($uibModal) {
        var directive = {
            restrict: 'EA', 
            templateUrl: 'app/map/map-table-view-template.html',
            link: link,
            // scope: {
            //     map: '='
            // }, 
            // controller: mapTableViewController,
            controller: 'MapViewController', 
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
                    // scope.vm.map.cameras.push(result);
                    scope.vm.update();
                    console.log('vm.map is: ', scope.vm.map);
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
                    // scope.vm.map.cameras.push(result);
                    scope.vm.update();
                    console.log('vm.map is: ', scope.vm.map);
                }, function() {
                    console.log('Pressed cancel');
                });
            }
        }
    }

    // mapTableViewController.$inject = ['$scope'];
    //
    // function mapTableViewController ($scope) {
    //     console.log('This is the map-table controller: ', $scope);
    //     var vm = this;
    //     vm.setSelected = setSelected;
    //    
    //     vm.isCamerasCollapsed = true;
    //     vm.isPlayersCollapsed = true;
    //    
    //     console.log('Scope is: ', $scope);
    //
    //     function setSelected(entity) {
    //         if(vm.selected == entity) {
    //             vm.selected = null;
    //         } else {
    //             vm.selected = entity;
    //         }
    //         console.log('Selected entity: ', vm.selected);
    //         return vm.selected;
    //     }
    // }
})();

