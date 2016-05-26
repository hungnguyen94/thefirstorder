/**
 * Created by Hung.
 */
(function() {
    'use strict';
    angular
        .module('thefirstorderApp')
        .factory('ProjectManager', ProjectManager);

    ProjectManager.$inject = ['$http'];

    function ProjectManager ($http) {
        var resourceUrl =  'api/account/';
        var service = {
            get: get,
            update: update
        };
        return service;

        function get() {
            return $http.get('api/account/currentproject');
        }

        function update(id) {
            return $http.put(
                'api/account/update_currentproject',
                {},
                { params: {projectId: id} }
            );
        }
    }
})();
