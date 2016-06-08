(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('RequestResetController', RequestResetController);

    RequestResetController.$inject = ['$timeout', 'Auth'];

    /**
     * Controller for the Request Reset state.
     * A user can reset his password here.
     * @param $timeout
     * @param Auth
     * @constructor
     */
    function RequestResetController ($timeout, Auth) {
        var vm = this;

        vm.error = null;
        vm.errorEmailNotExists = null;
        vm.requestReset = requestReset;
        vm.resetAccount = {};
        vm.success = null;

        $timeout(function (){angular.element('#email').focus();});

        /**
         * Requests the reset.
         */
        function requestReset () {

            vm.error = null;
            vm.errorEmailNotExists = null;

            Auth.resetPasswordInit(vm.resetAccount.email).then(function () {
                vm.success = 'OK';
            }).catch(function (response) {
                vm.success = null;
                if (response.status === 400 && response.data === 'e-mail address not registered') {
                    vm.errorEmailNotExists = 'ERROR';
                } else {
                    vm.error = 'ERROR';
                }
            });
        }
    }
})();
