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
            'update': { method:'PUT' }
        });
    }
})();
