(function () {
    'use strict';

    angular
        .module('thefirstorderApp')
        .directive('onBgUpload', onBgUpload)
        .controller('LoadBackgroundController', LoadBackgroundController);

    LoadBackgroundController.$inject = ['$resource', '$http', '$timeout', '$scope', '$uibModalInstance', 'currentProject', 'Script', 'Project', 'AlertService', 'Upload'];

    function LoadBackgroundController($resource, $http, $timeout, $scope, $uibModalInstance, currentProject, Script, Project, AlertService, Upload) {
        var vm = this;
        vm.uploadedBackground = null;

        vm.preview = preview;
        vm.upload = upload;

        function preview() {
            var file = document.getElementById("getval").files[0];
            var reader = new FileReader();
            reader.onloadend = function () {
                document.getElementById('concertMap').style.backgroundImage = "url(" + reader.result + ")";
                vm.uploadedBackground = reader.result;
            }
            if (file) {
                reader.readAsDataURL(file);
            } else {

            }
        }

        function upload() {
            var Uploader = $resource('api/maps/upload/:file', {}, {
                uploadBackground: {method: 'PUT', params: {file: '@file'}}
            });

            if(vm.uploadedBackground === null){
                console.log("No background selected...");
            } else {
                console.log("Uploading bg");
                Uploader.uploadBackground({file: vm.uploadedBackground});
            }
        }

        /**
         * Closes the dialog without committing the changes.
         */
        vm.clear = function () {
            $uibModalInstance.dismiss('cancel');
        };

        /**
         * Sets the given script as the script of the current project of the active user.
         * @param scriptId should be the id of the script
         */
        vm.load = function (scriptId) {
            vm.isLoading = true;
            Script.get({id: scriptId}, onLoadSuccess, onLoadError);
        };

        /**
         * Closes the dialog after successfully saving the script.
         * @param result should be the saved script.
         */
        function onLoadSuccess(result) {
            currentProject.script = result;
            Project.update(currentProject);

            $scope.$emit('thefirstorderApp:scriptLoad', result);
            $uibModalInstance.close(result);
            vm.isLoading = false;
        }

        /**
         * Do nothing when saving the script fails.
         */
        function onLoadError() {
            vm.isLoading = false;
        }

        /**
         * Loads all scripts from the database,
         *  setting hasScripts to true if there are scripts in the database.
         */
        function getScripts() {
            vm.scripts = Script.query({}, onSuccess, onError);

            function onSuccess(data) {
                vm.hasScripts = data.length > 0;
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }
    }

    function onBgUpload() {
        return {
            restrict: 'A',
            link: function (scope, element, attrs) {
                var onBgUpload = scope.$eval(attrs.onBgUpload);
                element.bind('change', onBgUpload);
            }
        };
    }
})
();
