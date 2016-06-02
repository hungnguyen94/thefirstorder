(function () {
    'use strict';

    angular
        .module('thefirstorderApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('mapview', {
            parent: 'app',
            url: '/maps',
            data: {
                authorities: ['ROLE_USER']
            },
            views: {
                'content@': {
                    templateUrl: 'app/map/mapview.html',
                    controller: 'MapviewController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                currentProject: ['$state', 'Project', 'currentProjectId',
                    function ($state, Project, currentProjectId) {
                        if (currentProjectId == null) {
                            $state.go("noproject");
                        }

                        var res = Project.get({id: currentProjectId}, onSuccess, onError);

                        function onSuccess(project) {
                            if (project.map == null) {
                                $state.go("map-setup");
                            }
                        }

                        function onError(error) {
                            $state.go("noproject");
                        }

                        return res;
                    }
                ]
            }
        });
    }
})();
