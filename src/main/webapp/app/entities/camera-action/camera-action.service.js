(function() {
    'use strict';
    angular
        .module('thefirstorderApp')
        .factory('CameraAction', CameraAction);

    CameraAction.$inject = ['$resource'];

    function CameraAction ($resource) {
        var resourceUrl =  'api/camera-actions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
