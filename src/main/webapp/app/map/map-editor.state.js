(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

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
                currentProject: ['ProjectManager', 'currentProjectId',
                    function (ProjectManager, currentProjectId) {
                        return ProjectManager.validateScript(currentProjectId);
                    }
                ]
            }
        });
    }
})();
