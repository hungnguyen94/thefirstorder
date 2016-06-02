(function () {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('LoadScriptController', LoadScriptController);

    LoadScriptController.$inject = ['$timeout', '$scope', '$uibModalInstance', 'currentProject', 'Script', 'Project', 'AlertService'];

    function LoadScriptController($timeout, $scope, $uibModalInstance, currentProject, Script, Project, AlertService) {
        var vm = this;
        vm.hasScripts = false;

        vm.getScripts = getScripts;
        vm.getScripts();

        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        vm.clear = function () {
            $uibModalInstance.dismiss('cancel');
        };

        vm.create = function () {
            $uibModalInstance.dismiss('create');
        };

        vm.load = function (scriptId) {
            vm.isLoading = true;
            Script.get({id: scriptId}, onLoadSuccess, onLoadError);
        };

        function getScripts() {
            vm.scripts = Script.query({}, onSuccess, onError);

            function onSuccess(data, headers) {
                vm.hasScripts = data.length > 0;
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        var onLoadSuccess = function (result) {
            currentProject.script = result;
            Project.update(currentProject);

            $scope.$emit('thefirstorderApp:scriptLoad', result);
            $uibModalInstance.close(result);
            vm.isLoading = false;
        };

        var onLoadError = function () {
            vm.isLoading = false;
        };
    }
})();
