(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('timeline', {
            parent: 'app',
            url: '/timeline',
            data: {
                authorities: ['ROLE_USER']
            },
            views: {
                'content@': {
                    templateUrl: 'app/timeline/timeline.html',
                    controller: 'TimelineController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
