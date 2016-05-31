(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('scripting', {
            parent: 'app',
            url: '/scripting',
            data: {
                authorities: ['ROLE_USER']
            },
            views: {
                'content@': {
                    templateUrl: 'app/scripting/scripting.html',
                    controller: 'ScriptingController',
                    controllerAs: 'vm'
                }
            }
        })
        .state('scripting.new', {
            parent: 'scripting',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/scripting/scripting-new-dialog.html',
                    controller: 'ScriptingNewDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('scripting', null, { reload: true });
                }, function() {
                    $state.go('scripting');
                });
            }]
        });
    }
})();
