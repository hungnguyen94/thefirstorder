(function () {
    'use strict';

    angular
        .module('thefirstorderApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('map-setup', {
            parent: 'app',
            url: '/map-setup',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/setup/map/map-setup.html',
                    controller: 'MapSetupController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                currentProject: ['ProjectManager', 'currentProjectId',
                    function (ProjectManager, currentProjectId) {
                        return ProjectManager.validateMap(currentProjectId);
                    }
                ]
            }
        }).state('map.create', {
            parent: 'map-setup',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', 'currentProject', function ($stateParams, $state, $uibModal, currentProject) {
                $uibModal.open({
                    templateUrl: 'app/setup/map/createmap-dialog.html',
                    controller: 'CreateMapController',
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
                    $state.go('mapview', null, {reload: true});
                }, function () {
                    $state.go('map-setup');
                });
            }]
        }).state('map.load', {
            parent: 'map-setup',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', 'currentProject', function($stateParams, $state, $uibModal, currentProject) {
                $uibModal.open({
                    templateUrl: 'app/setup/map/loadmap-dialog.html',
                    controller: 'LoadMapController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        currentProject: function(){
                            return currentProject;
                        }
                    }
                }).result.then(function() {
                    $state.go('mapview', null, { reload: true });
                }, function() {
                    $state.go('map-setup');
                });
            }]
        })
    }
})();
