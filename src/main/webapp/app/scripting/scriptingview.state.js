(function () {
    'use strict';

    angular
        .module('thefirstorderApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('scriptingview', {
            parent: 'app',
            url: '/scripting',
            data: {
                authorities: ['ROLE_USER']
            },
            views: {
                'content@': {
                    templateUrl: 'app/scripting/scriptingview.html',
                    controller: 'ScriptingviewController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                validateProject: ['ProjectManager', 'currentProjectId',
                    function (ProjectManager, currentProjectId) {
                        return ProjectManager.validateProject(currentProjectId);
                    }
                ],
                validateScript: ['ProjectManager', 'validateProject',
                    function (ProjectManager, validateProject) {
                        ProjectManager.validateScript(validateProject);
                    }
                ]
            }
        });
    }
})();
