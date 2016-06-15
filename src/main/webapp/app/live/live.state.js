(function () {
    'use strict';

    angular
        .module('thefirstorderApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    /**
     * Configures the states for the live views.
     * @param $stateProvider
     */
    function stateConfig($stateProvider) {
        $stateProvider.state('live', {
            parent: 'app',
            url: '/live',
            data: {
                authorities: ['ROLE_USER']
            },
            views: {
                'content@': {
                    templateUrl: 'app/live/live.html',
                    controller: 'LiveController',
                    controllerAs: 'vm'
                }
            },
            onEnter: function(JhiTrackerService) {
                JhiTrackerService.subscribe();
            },
            onExit: function(JhiTrackerService) {
                JhiTrackerService.unsubscribe();
            },
            resolve: {
                validateScript: ['ProjectManager', 'currentProjectId',
                    function (ProjectManager, currentProjectId) {
                        ProjectManager.validateScript(currentProjectId);
                    }
                ],
                currentProject: ['Project', 'currentProjectId',
                    function (Project, currentProjectId) {
                        return Project.get({
                            id: currentProjectId
                        });
                    }
                ]
            }
        });
    }
})();
