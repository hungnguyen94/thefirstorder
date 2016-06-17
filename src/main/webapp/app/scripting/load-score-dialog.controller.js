(function () {
    'use strict';

    angular
        .module('thefirstorderApp')
        .directive('onBgUpload', onBgUpload)
        .directive('fileModel', fileModel)
        .controller('LoadScoreController', LoadScoreController);

    LoadScoreController.$inject = ['$http', '$scope', '$uibModalInstance', 'currentProject', 'Script'];

    /**
     *
     * @param $http
     * @param $scope
     * @param $uibModalInstance
     * @param currentProject
     * @param Script
     * @constructor
     */
    function LoadScoreController($http, $scope, $uibModalInstance, currentProject, Script) {
        var vm = this;
        vm.uploadedScore = null;
        vm.hasPreview = false;
        vm.isUploading = false;

        vm.preview = preview;
        vm.upload = upload;

        /**
         * Loads a preview of the selected image into the score preview element.
         */
        function preview() {
            var file = document.getElementById("getImage").files[0];
            var reader = new FileReader();
            reader.onloadend = function () {
                $scope.$apply(function () {
                    vm.hasPreview = true;
                    vm.previewName = file.name;
                });
            };
            if (file) {
                reader.readAsDataURL(file);
            }
        }

        /**
         * Uploads the selected image to the server
         */
        function post() {
            var fd = new FormData();

            fd.append('file', $scope.image);

            $http.post('api/upload/pdf', fd, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            }).then(onPostSuccess, onPostError);
        }

        /**
         * When posting the image succeeds, update the score image location of the script
         *  of the current project of the active user.
         * @param response should be the response message after uploading,
         *  containing the location of the uploaded image on the server.
         */
        function onPostSuccess(response) {
            var location = response.data.location;

            vm.script = currentProject.script;
            vm.script.score = location;
            Script.update(vm.script, onUpdateSuccess, onUpdateError);
        }

        /**
         * When posting the image fails, set isUploading to false.
         */
        function onPostError() {
            vm.isUploading = false;
        }

        /**
         * When updating the script succeeds, close the dialog.
         * @param result should be the updated script.
         */
        function onUpdateSuccess(result) {
            $scope.$emit('thefirstorderApp:scriptLoad', result);
            $uibModalInstance.close(result);
            vm.isUploading = false;
        }

        /**
         * When updating the script fails, set isUploading to false.
         */
        function onUpdateError() {
            vm.isUploading = false;
        }

        /**
         * Uploads the selected image to the server when it exists.
         */
        function upload() {
            if (vm.hasPreview) {
                post();
            }
        }

        /**
         * Closes the dialog without committing the changes.
         */
        vm.clear = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }

    /**
     * Executes the on-bg-upload attribute on the change event of the element.
     * @returns {{restrict: string, link: link}}
     */
    function onBgUpload() {
        return {
            restrict: 'A',
            link: function (scope, element, attrs) {
                var onBgUpload = scope.$eval(attrs.onBgUpload);
                element.bind('change', onBgUpload);
            }
        };
    }

    /**
     * Sets the file model directive, necessary to get the image from the input in the correct FormData format.
     * @param $parse
     * @returns {{restrict: string, link: link}}
     */
    function fileModel($parse) {
        return {
            restrict: 'A',
            link: function (scope, element, attrs) {
                var model = $parse(attrs.fileModel);
                var modelSetter = model.assign;

                element.bind('change', function () {
                    scope.$apply(function () {
                        modelSetter(scope, element[0].files[0]);
                    });
                });
            }
        };
    }
})
();
