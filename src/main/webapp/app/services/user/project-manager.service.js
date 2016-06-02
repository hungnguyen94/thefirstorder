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
            update: update,
            loadProject: loadProject
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

        function loadProject() {
            var currentProject = this.get().then(onLoadSuccess, onLoadError);

            return currentProject;

            function onLoadSuccess(result) {
                return result.data;
            };

            function onLoadError (error) {
                return null;
            };
        }
    }
})();
