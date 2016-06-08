(function () {
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
        }).state('map.load-background', {
            parent: 'map-editor',
            url: '/map-editor/load-background',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', 'ProjectManager', function($stateParams, $state, $uibModal, currentProject) {
                $uibModal.open({
                    templateUrl: 'app/map/load-background-dialog.html',
                    controller: 'LoadBackgroundController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        currentProject: function(ProjectManager){
                            return ProjectManager.getProject();
                        }
                    }
                }).result.then(function() {
                    $state.go('map-editor', null, { reload: true });
                }, function() {
                    $state.go('map-editor');
                });
            }]
        });
    }
})();
