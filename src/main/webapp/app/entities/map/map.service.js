(function() {
    'use strict';
    angular
        .module('thefirstorderApp')
        .factory('Map', Map);

    Map.$inject = ['$resource'];

    function Map ($resource) {
        var resourceUrl =  'api/maps/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method: 'PUT' },
            'getDTO': {
                method: 'GET',
                url: resourceUrl + '/dto',
                isArray: false,
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'addCamera': {
                method: 'PUT',
                // url: resourceUrl + '/addCamera',
                url: resourceUrl + '/addCamera?cameraId=:cameraId',
                isArray: false,
                params: {
                    id: '@id',
                    cameraId: '@cameraId'
                }
            }
        });
    }
})();
