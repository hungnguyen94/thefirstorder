(function () {
    'use strict';

    angular
        .module('thefirstorderApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('noproject', {
            parent: 'app',
            url: '/noproject',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/setup/project/noproject.html',
                    controller: 'NoProjectController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
