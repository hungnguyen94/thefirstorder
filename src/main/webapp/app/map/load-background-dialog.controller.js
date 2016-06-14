(function () {
    'use strict';

    angular
        .module('thefirstorderApp')
        .directive('onBgUpload', onBgUpload)
        .directive('fileModel', fileModel)
        .controller('LoadBackgroundController', LoadBackgroundController);

    LoadBackgroundController.$inject = ['$http', '$scope', '$uibModalInstance', 'currentProject', 'Script', 'Project'];
    fileModel.$inject = ['$parse'];

    function LoadBackgroundController($http, $scope, $uibModalInstance, currentProject, Script, Project) {
        var vm = this;
        vm.uploadedBackground = null;
        vm.hasPreview = false;

        vm.preview = preview;
        vm.upload = upload;

        /**
         * Loads a preview of the selected image into the concertMap element.
         */
        function preview() {
            var file = document.getElementById("getImage").files[0];
            var reader = new FileReader();
            reader.onloadend = function () {
                document.getElementById('concertMap').style.backgroundImage = "url(" + reader.result + ")";
                vm.uploadedBackground = reader.result;
                vm.hasPreview = true;
            };
            if (file) {
                reader.readAsDataURL(file);
            } else {

            }
        }

        /**
         * Uploads the selected image to the server
         */
        function post() {
            var fd = new FormData();

            fd.append('file', $scope.image);

            $http.post('api/upload', fd, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            }).catch(function (error) {
                vm.uploadError = true;
                throw error;
            });
        }

        /**
         * Uploads the selected image to the server, if not null
         */
        function upload() {
            if (vm.uploadedBackground !== null) {
                post();
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

    function fileModel($parse) {
        return {
            restrict: 'A',
            link: function(scope, element, attrs) {
                var model = $parse(attrs.fileModel);
                var modelSetter = model.assign;

                element.bind('change', function(){
                    scope.$apply(function(){
                        modelSetter(scope, element[0].files[0]);
                    });
                });
            }
        };
    }
})
();
