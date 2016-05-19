(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('time-point', {
            parent: 'entity',
            url: '/time-point?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TimePoints'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/time-point/time-points.html',
                    controller: 'TimePointController',
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
        .state('time-point-detail', {
            parent: 'entity',
            url: '/time-point/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TimePoint'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/time-point/time-point-detail.html',
                    controller: 'TimePointDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TimePoint', function($stateParams, TimePoint) {
                    return TimePoint.get({id : $stateParams.id});
                }]
            }
        })
        .state('time-point.new', {
            parent: 'time-point',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/time-point/time-point-dialog.html',
                    controller: 'TimePointDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                startTime: null,
                                duration: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('time-point', null, { reload: true });
                }, function() {
                    $state.go('time-point');
                });
            }]
        })
        .state('time-point.edit', {
            parent: 'time-point',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/time-point/time-point-dialog.html',
                    controller: 'TimePointDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TimePoint', function(TimePoint) {
                            return TimePoint.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('time-point', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('time-point.delete', {
            parent: 'time-point',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/time-point/time-point-delete-dialog.html',
                    controller: 'TimePointDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TimePoint', function(TimePoint) {
                            return TimePoint.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('time-point', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
