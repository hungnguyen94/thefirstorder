(function () {
    'use strict';

    angular
        .module('thefirstorderApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('script-setup', {
            parent: 'app',
            url: '/script-setup',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/setup/script/script-setup.html',
                    controller: 'ScriptSetupController',
                    controllerAs: 'vm'
                }
            }
        }).state('script.create', {
            parent: 'script-setup',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', 'ProjectManager', function ($stateParams, $state, $uibModal, currentProject) {
                $uibModal.open({
                    templateUrl: 'app/setup/script/createscript-dialog.html',
                    controller: 'CreateScriptController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        },
                        currentProject: function(ProjectManager){
                            return ProjectManager.getProject();
                        }
                    }
                }).result.then(function () {
                    $state.go('scripting', null, {reload: true});
                }, function () {
                    $state.go('script-setup');
                });
            }]
        }).state('script.load', {
            parent: 'script-setup',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', 'ProjectManager', function($stateParams, $state, $uibModal, currentProject) {
                $uibModal.open({
                    templateUrl: 'app/setup/script/loadscript-dialog.html',
                    controller: 'LoadScriptController',
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
                    $state.go('script-setup');
                });
            }]
        });
    }
})();
