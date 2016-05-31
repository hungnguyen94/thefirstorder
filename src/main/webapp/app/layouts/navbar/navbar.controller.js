(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('NavbarController', NavbarController);

    NavbarController.$inject = ['$state', 'Auth', 'Principal', 'ENV', 'LoginService', 'Project', 'ProjectManager'];

    function NavbarController ($state, Auth, Principal, ENV, LoginService, Project, ProjectManager) {
        var vm = this;

        vm.isNavbarCollapsed = true;
        vm.isAuthenticated = Principal.isAuthenticated;
        vm.inProduction = ENV === 'prod';
        vm.login = login;
        vm.logout = logout;
        vm.toggleNavbar = toggleNavbar;
        vm.collapseNavbar = collapseNavbar;
        vm.$state = $state;
        
        loadProject();

        function login () {
            collapseNavbar();
            LoginService.open();
        }

        function logout () {
            collapseNavbar();
            Auth.logout();
            $state.go('home');
        }

        function toggleNavbar () {
            vm.isNavbarCollapsed = !vm.isNavbarCollapsed;
        }

        function collapseNavbar () {
            vm.isNavbarCollapsed = true;
        }

        function loadProject() {
            ProjectManager.get().then(function (object) {
                var projectId = object.data;
                vm.currentProject = Project.get({id: projectId});
            });
        }
    }
})();
