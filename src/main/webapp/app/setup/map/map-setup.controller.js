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

        /**
         * Sets projectIsNull to false if the active user has no current project.
         */
        function loadProject() {
            if (currentProjectId != null) {
                vm.projectIsNull = false;
            }
        }
    }
})();
