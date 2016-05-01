(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('camera-action', {
            parent: 'entity',
            url: '/camera-action?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CameraActions'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/camera-action/camera-actions.html',
                    controller: 'CameraActionController',
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
        .state('camera-action-detail', {
            parent: 'entity',
            url: '/camera-action/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CameraAction'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/camera-action/camera-action-detail.html',
                    controller: 'CameraActionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'CameraAction', function($stateParams, CameraAction) {
                    return CameraAction.get({id : $stateParams.id});
                }]
            }
        })
        .state('camera-action.new', {
            parent: 'camera-action',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/camera-action/camera-action-dialog.html',
                    controller: 'CameraActionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                duration: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('camera-action', null, { reload: true });
                }, function() {
                    $state.go('camera-action');
                });
            }]
        })
        .state('camera-action.edit', {
            parent: 'camera-action',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/camera-action/camera-action-dialog.html',
                    controller: 'CameraActionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CameraAction', function(CameraAction) {
                            return CameraAction.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('camera-action', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('camera-action.delete', {
            parent: 'camera-action',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/camera-action/camera-action-delete-dialog.html',
                    controller: 'CameraActionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CameraAction', function(CameraAction) {
                            return CameraAction.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('camera-action', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
