(function () {
    'use strict';

    angular
        .module('thefirstorderApp')
        .directive('onBgUpload', onBgUpload)
        .directive('fileModel', fileModel)
        .controller('LoadBackgroundController', LoadBackgroundController);

    LoadBackgroundController.$inject = ['$http', '$scope', '$uibModalInstance', 'currentProject', 'Script', 'Project', 'ProjectManager', 'Map'];
    fileModel.$inject = ['$parse'];

    function LoadBackgroundController($http, $scope, $uibModalInstance, currentProject, Script, Project, ProjectManager, Map) {
        var vm = this;
        vm.uploadedBackground = null;
        vm.hasPreview = false;
        vm.isUploading = false;

        vm.preview = preview;
        vm.upload = upload;

        /**
         * Loads a preview of the selected image into the concertMap element.
         */
        function preview() {
            var file = document.getElementById("getImage").files[0];
            var reader = new FileReader();
            reader.onloadend = function () {
                document.getElementById('backgroundPreview').style.backgroundImage = "url(" + reader.result + ")";
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

            $http.post('api/upload/image', fd, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            }).then(onPostSuccess, onPostError);
        }

        /**
         * When posting the image succeeds, update the background image location of the map
         *  of the current project of the active user.
         * @param response should be the response message after uploading,
         *  containing the location of the uploaded image on the server.
         */
        function onPostSuccess(response) {
            var location = response.data.location;

            vm.map = currentProject.map;
            vm.map.backgroundImage = location;
            Map.update(vm.map, onUpdateSuccess, onUpdateError);
        }

        /**
         * When posting the image fails, set isUploading to false.
         */
        function onPostError() {
            console.log("Error uploading file. Please keep in mind that files should not be larger than 25MB.");
            vm.isUploading = false;
        }

        /**
         * When updating the map succeeds, close the dialog.
         * @param result should be the updated map.
         */
        function onUpdateSuccess(result) {
            $scope.$emit('thefirstorderApp:scriptLoad', result);
            $uibModalInstance.close(result);
            vm.isUploading = false;
        }

        /**
         * When updating the map fails, set isUploading to false.
         */
        function onUpdateError() {
            console.log("Error setting image as map background image.");
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
