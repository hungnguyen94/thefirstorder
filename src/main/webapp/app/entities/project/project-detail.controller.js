(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('ProjectDetailController', ProjectDetailController);

    ProjectDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Project', 'Script', 'Map'];

    function ProjectDetailController($scope, $rootScope, $stateParams, entity, Project, Script, Map) {
        var vm = this;
        vm.project = entity;
        
        var unsubscribe = $rootScope.$on('thefirstorderApp:projectUpdate', function(event, result) {
            vm.project = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
