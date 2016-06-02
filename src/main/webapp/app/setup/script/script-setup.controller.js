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

        function loadProject() {
            if(currentProjectId != null) {
                vm.projectIsNull = false;
            }
        }
    }
})();
