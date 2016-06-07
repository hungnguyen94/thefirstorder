(function () {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'Project', 'AlertService', 'currentProjectId'];

    function HomeController($scope, Principal, LoginService, $state, Project, AlertService, currentProjectId) {
        var vm = this;
        vm.account = null;
        vm.isAuthenticated = null;
        vm.hasCurrentProject = false;
        vm.login = LoginService.open;
        vm.register = register;

        loadProject();

        $scope.$on('authenticationSuccess', function () {
            getAccount();
        });

        getAccount();

        /**
         * Checks whether the user is authorised to use the app,
         *  gets the name of the user to show on the home page.
         */
        function getAccount() {
            Principal.identity().then(function (account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }

        /**
         * Go to the register state to register a new account.
         */
        function register() {
            $state.go('register');
        }

        /**
         * Loads the current project of the active user. 
         */
        function loadProject() {
            if(currentProjectId != null) {
                vm.currentProject = Project.get({id: currentProjectId});
                if (vm.currentProject != null) {
                    vm.hasCurrentProject = true;
                }
            }
        }
    }
})();
