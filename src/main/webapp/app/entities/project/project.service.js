(function() {
    'use strict';
    angular
        .module('thefirstorderApp')
        .factory('Project', Project);

    Project.$inject = ['$resource'];

    function Project ($resource) {
        var resourceUrl =  'api/projects/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }, 
            'exportPdf': {
                method: 'GET',
                url: 'api/projects/:id/exportpdf', 
                responseType: 'arraybuffer'
            }
        });
    }
})();
