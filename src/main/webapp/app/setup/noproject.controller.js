(function () {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('NoProjectController', NoProjectController);

    NoProjectController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'Project', 'AlertService', 'currentProject'];

    function NoProjectController($scope, Principal, LoginService, $state, Project, AlertService, currentProject) {
        var vm = this;
        vm.projectIsNull = true;

        loadProject();

        function loadProject() {
            if(currentProject != null) {
                vm.projectIsNull = false;
            }
        }
    }
})();
