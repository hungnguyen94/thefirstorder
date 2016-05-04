(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cue', {
            parent: 'entity',
            url: '/cue?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Cues'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cue/cues.html',
                    controller: 'CueController',
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
        .state('cue-detail', {
            parent: 'entity',
            url: '/cue/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Cue'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cue/cue-detail.html',
                    controller: 'CueDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Cue', function($stateParams, Cue) {
                    return Cue.get({id : $stateParams.id});
                }]
            }
        })
        .state('cue.new', {
            parent: 'cue',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cue/cue-dialog.html',
                    controller: 'CueDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                duration: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('cue', null, { reload: true });
                }, function() {
                    $state.go('cue');
                });
            }]
        })
        .state('cue.edit', {
            parent: 'cue',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cue/cue-dialog.html',
                    controller: 'CueDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Cue', function(Cue) {
                            return Cue.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('cue', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cue.delete', {
            parent: 'cue',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cue/cue-delete-dialog.html',
                    controller: 'CueDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Cue', function(Cue) {
                            return Cue.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('cue', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
