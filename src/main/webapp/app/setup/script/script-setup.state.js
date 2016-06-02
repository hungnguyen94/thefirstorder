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
            },
            resolve: {
                currentProject: ['ProjectManager', 'currentProjectId',
                    function (ProjectManager, currentProjectId) {
                        return ProjectManager.validateScript(currentProjectId);
                    }
                ]
            }
        }).state('script.create', {
            parent: 'script-setup',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', 'currentProject', function ($stateParams, $state, $uibModal, currentProject) {
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
                        currentProject: function(){
                            return currentProject;
                        }
                    }
                }).result.then(function () {
                    $state.go('timeline', null, {reload: true});
                }, function () {
                    $state.go('script-setup');
                });
            }]
        })
    }
})();
