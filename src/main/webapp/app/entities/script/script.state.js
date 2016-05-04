(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('script', {
            parent: 'entity',
            url: '/script?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Scripts'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/script/scripts.html',
                    controller: 'ScriptController',
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
        .state('script-detail', {
            parent: 'entity',
            url: '/script/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Script'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/script/script-detail.html',
                    controller: 'ScriptDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Script', function($stateParams, Script) {
                    return Script.get({id : $stateParams.id});
                }]
            }
        })
        .state('script.new', {
            parent: 'script',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/script/script-dialog.html',
                    controller: 'ScriptDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('script', null, { reload: true });
                }, function() {
                    $state.go('script');
                });
            }]
        })
        .state('script.edit', {
            parent: 'script',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/script/script-dialog.html',
                    controller: 'ScriptDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Script', function(Script) {
                            return Script.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('script', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('script.delete', {
            parent: 'script',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/script/script-delete-dialog.html',
                    controller: 'ScriptDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Script', function(Script) {
                            return Script.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('script', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
