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
        })
    }
})();
