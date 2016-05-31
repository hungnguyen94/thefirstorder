(function () {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'ProjectManager', 'Project', 'AlertService'];

    function HomeController($scope, Principal, LoginService, $state, ProjectManager, Project, AlertService) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.hasCurrentProject = false;
        vm.login = LoginService.open;
        vm.register = register;
        vm.loadProject = loadProject;
        vm.loadProject();

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
            ProjectManager.get()
                .then(function (object) {
                    var projectId = object.data;
                    vm.currentProject = Project.get({id: projectId});
                    vm.hasCurrentProject = true;
                });
        }
    }
})();
