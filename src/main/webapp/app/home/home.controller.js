(function () {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'Project', 'AlertService', 'currentProject'];

    function HomeController($scope, Principal, LoginService, $state, Project, AlertService, currentProject) {
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
            vm.currentProject = Project.get({id: currentProject});
            if(vm.currentProject != null) {
                vm.hasCurrentProject = true;
            }
        }
    }
})();
