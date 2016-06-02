(function () {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('MapSetupController', MapSetupController);

    MapSetupController.$inject = ['currentProjectId'];

    function MapSetupController(currentProjectId) {
        var vm = this;
        vm.projectIsNull = true;

        loadProject();

        function loadProject() {
            if(currentProjectId != null) {
                vm.projectIsNull = false;
            }
        }
    }
})();
