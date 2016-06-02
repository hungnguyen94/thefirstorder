(function () {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('NoProjectController', NoProjectController);

    NoProjectController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'Project', 'AlertService', 'currentProject'];

    function NoProjectController($scope, Principal, LoginService, $state, Project, AlertService, currentProject) {
        var vm = this;
        vm.hasCurrentProject = false;

        loadProject();

        function loadProject() {
            if(currentProject != null) {
                vm.currentProject = Project.get({id: currentProject});
                if (vm.currentProject != null) {
                    vm.hasCurrentProject = true;
                }
            }
        }
    }
})();
