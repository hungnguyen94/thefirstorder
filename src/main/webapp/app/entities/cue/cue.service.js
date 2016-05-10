(function() {
    'use strict';
    angular
        .module('thefirstorderApp')
        .factory('Cue', Cue);

    Cue.$inject = ['$resource'];

    function Cue ($resource) {
        var resourceUrl =  'api/cues/:id';

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
