(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    /**
     * Configuration for the Request Reset state.
     * @param $stateProvider
     */
    function stateConfig($stateProvider) {
        $stateProvider.state('requestReset', {
            parent: 'account',
            url: '/reset/request',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/account/reset/request/reset.request.html',
                    controller: 'RequestResetController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
