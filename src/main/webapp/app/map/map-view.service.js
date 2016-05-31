(function() {
    'use strict';
    angular
        .module('thefirstorderApp')
        .factory('MapService', MapService);

    MapService.$inject = ['$resource'];

    function MapService ($resource) {
        var resourceUrl =  'api/maps/:mapId/dto';

        return $resource(resourceUrl, {}, {
            'get': {
                method: 'GET',
                isArray: false, 
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            }
        });
    }
})();
