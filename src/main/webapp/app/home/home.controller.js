(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'Camera', 'CameraAction'];

    function HomeController ($scope, Principal, LoginService, $state,  Camera, CameraAction) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        vm.loadCamera = loadCamera;
        vm.loadCamera();
        vm.loadCameraActions = loadCameraActions;
        vm.loadCameraActions();
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }

        function loadCamera () {
            Camera.query({

            }, onSuccess, onError);

            function onSuccess(data, headers) {
                vm.cameras = data;
                vm.queryCount = vm.totalItems;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadCameraActions () {
            CameraAction.query({

            }, onSuccess, onError);
            function onSuccess(data, headers) {
                vm.cameraActions = data;
                vm.queryCount = vm.totalItems;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }
    }
})();
