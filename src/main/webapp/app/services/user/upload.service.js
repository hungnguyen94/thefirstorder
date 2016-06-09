(function () {
    'use strict';

    angular
        .module('thefirstorderApp')
        .factory('Upload', Upload);

    Upload.$inject = ['$resource'];

    function Upload ($resource) {
        var service = $resource('api/upload', {content: '@content'}, {
            create: {
                method: "POST",
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined},
                data: ':content'
            }
        });

        return service;
    }
})();
