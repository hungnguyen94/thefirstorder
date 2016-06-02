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

        function getAccount() {
            Principal.identity().then(function (account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }

        function register() {
            $state.go('register');
        }

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
