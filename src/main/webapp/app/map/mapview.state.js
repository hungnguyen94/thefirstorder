(function() {
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
            }
        });
    }
})();
