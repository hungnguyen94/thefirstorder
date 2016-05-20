(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('ScriptDetailController', ScriptDetailController);

    ScriptDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Script', 'Cue'];

    function ScriptDetailController($scope, $rootScope, $stateParams, entity, Script, Cue) {
        var vm = this;
        vm.script = entity;
        
        var unsubscribe = $rootScope.$on('thefirstorderApp:scriptUpdate', function(event, result) {
            vm.script = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
