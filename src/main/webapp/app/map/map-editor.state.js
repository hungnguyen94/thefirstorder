(function () {
    'use strict';

    angular
        .module('thefirstorderApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    /**
     * Configures the state for all map pages.
     * @param $stateProvider
     */
    function stateConfig($stateProvider) {
        $stateProvider.state('map-editor', {
            parent: 'app',
            url: '/map-editor',
            data: {
                authorities: ['ROLE_USER']
            },
            views: {
                'content@': {
                    templateUrl: 'app/map/map-editor.html'
                }
            },
            resolve: {
                validateMap: ['ProjectManager', 'currentProjectId',
                    function (ProjectManager, currentProjectId) {
                        ProjectManager.validateMap(currentProjectId);
                    }
                ],
                currentProject: ['ProjectManager',
                    function (ProjectManager) {
                        return ProjectManager.getProject();
                    }
                ]
            }
        });
    }
})();
