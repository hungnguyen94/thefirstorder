(function () {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('ScriptSetupController', ScriptSetupController);

    ScriptSetupController.$inject = ['currentProjectId'];

    function ScriptSetupController(currentProjectId) {
        var vm = this;
        vm.projectIsNull = true;

        loadProject();

        /**
         * Sets projectIsNull to false if the active user has no current project.
         */
        function loadProject() {
            if (currentProjectId !== null) {
                vm.projectIsNull = false;
            }
        }
    }
})();
