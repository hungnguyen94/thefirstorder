(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('camera', {
            parent: 'entity',
            url: '/camera?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Cameras'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/camera/cameras.html',
                    controller: 'CameraController',
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
        .state('camera-detail', {
            parent: 'entity',
            url: '/camera/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Camera'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/camera/camera-detail.html',
                    controller: 'CameraDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Camera', function($stateParams, Camera) {
                    return Camera.get({id : $stateParams.id});
                }]
            }
        })
        .state('camera.new', {
            parent: 'camera',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
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
                                cameraType: null,
                                lensType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('camera', null, { reload: true });
                }, function() {
                    $state.go('camera');
                });
            }]
        })
        .state('camera.edit', {
            parent: 'camera',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/camera/camera-dialog.html',
                    controller: 'CameraDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Camera', function(Camera) {
                            return Camera.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('camera', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('camera.delete', {
            parent: 'camera',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/camera/camera-delete-dialog.html',
                    controller: 'CameraDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Camera', function(Camera) {
                            return Camera.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('camera', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
