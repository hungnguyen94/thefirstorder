(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    /**
     * Configures each state for the scripting page.
     * @param $stateProvider
     */
    function stateConfig($stateProvider) {
        $stateProvider
        .state('scripting', {
            parent: 'app',
            url: '/scripting',
            data: {
                authorities: ['ROLE_USER']
            },
            views: {
                'content@': {
                    templateUrl: 'app/scripting/scripting.html',
                    controller: 'ScriptingController',
                    controllerAs: 'vm'
                }
            }
        })
        .state('scripting.new', {
            parent: 'scripting',
            url: '/scripting/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/scripting/scripting-new-dialog.html',
                    controller: 'ScriptingNewDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('scripting', null, { reload: true });
                }, function() {
                    $state.go('scripting');
                });
            }]
        })
        .state('scripting.update', {
            parent: 'scripting',
            url: '/scripting/update/:name',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/scripting/scripting-update-dialog.html',
                    controller: 'ScriptingUpdateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('scripting', null, { reload: true });
                }, function() {
                    $state.go('scripting');
                });
            }]
        }).state('script.load-score', {
            parent: 'scripting',
            url: '/scripting/load-score',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', 'ProjectManager', function($stateParams, $state, $uibModal, currentProject) {
                $uibModal.open({
                    templateUrl: 'app/scripting/load-score-dialog.html',
                    controller: 'LoadScoreController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        currentProject: function(ProjectManager){
                            return ProjectManager.getProject();
                        }
                    }
                }).result.then(function() {
                    $state.go('scripting', null, { reload: true });
                }, function() {
                    $state.go('scripting');
                });
            }]
        });
    }
})();
