(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('map', {
            parent: 'entity',
            url: '/map?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Maps'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/map/maps.html',
                    controller: 'MapController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }]
            }
        })
        .state('map-detail', {
            parent: 'entity',
            url: '/map/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Map'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/map/map-detail.html',
                    controller: 'MapDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Map', function($stateParams, Map) {
                    return Map.get({id : $stateParams.id});
                }]
            }
        })
        .state('map.new', {
            parent: 'map',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/map/map-dialog.html',
                    controller: 'MapDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                background_image: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('map', null, { reload: true });
                }, function() {
                    $state.go('map');
                });
            }]
        })
        .state('map.edit', {
            parent: 'map',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/map/map-dialog.html',
                    controller: 'MapDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Map', function(Map) {
                            return Map.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('map', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('map.delete', {
            parent: 'map',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/map/map-delete-dialog.html',
                    controller: 'MapDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Map', function(Map) {
                            return Map.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('map', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
