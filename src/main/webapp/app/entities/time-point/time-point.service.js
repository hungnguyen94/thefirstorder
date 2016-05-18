(function() {
    'use strict';
    angular
        .module('thefirstorderApp')
        .factory('TimePoint', TimePoint);

    TimePoint.$inject = ['$resource'];

    function TimePoint ($resource) {
        var resourceUrl =  'api/time-points/:id';

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
