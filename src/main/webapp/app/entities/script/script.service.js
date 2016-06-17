(function () {
    'use strict';
    angular
        .module('thefirstorderApp')
        .factory('Script', Script);

    Script.$inject = ['$resource'];

    function Script($resource) {
        var resourceUrl = 'api/scripts/:id';

        return $resource(resourceUrl, {}, {
            'query': {method: 'GET', isArray: true},
            'queryNoProject': {
                method: 'GET',
                isArray: true,
                url: resourceUrl + '?filter=project-is-null'
            },
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': {method: 'PUT'}
        });
    }
})();
