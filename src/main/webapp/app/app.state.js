(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('app', {
            abstract: true,
            views: {
                'navbar@': {
                    templateUrl: 'app/layouts/navbar/navbar.html',
                    controller: 'NavbarController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                authorize: ['Auth',
                    function (Auth) {
                        return Auth.authorize();
                    }
                ],
                currentProject: ['ProjectManager',
                    function (ProjectManager) {
                        return ProjectManager.get().then(function (result) {
                            return result.data;
                        }, function (error) {
                            return null;
                        });
                    }
                ]
            }
        });
    }
})();
