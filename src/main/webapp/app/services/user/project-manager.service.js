/**
 * Created by Hung.
 */
(function () {
    'use strict';
    angular
        .module('thefirstorderApp')
        .factory('ProjectManager', ProjectManager);

    ProjectManager.$inject = ['$http', '$state', 'Project'];
    
    function ProjectManager($http, $state, Project) {
        var resourceUrl = 'api/account/';
        var service = {
            get: get,
            update: update,
            loadProject: loadProject,
            validateProject: validateProject,
            validateMap: validateMap,
            validateScript: validateScript
        };
        return service;

        function get() {
            return $http.get('api/account/currentproject');
        }

        function update(id) {
            return $http.put(
                'api/account/update_currentproject',
                {},
                {params: {projectId: id}}
            );
        }

        function loadProject() {
            var currentProject = this.get().then(onLoadSuccess, onLoadError);

            return currentProject;

            function onLoadSuccess(result) {
                return result.data;
            };

            function onLoadError(error) {
                return null;
            };
        }

        function validateProject(currentProjectId) {
            var res = validate(currentProjectId, function (project) {});

            return res;
        }

        function validateMap(currentProjectId) {
            var res = validate(currentProjectId, function (project) {
                if (project.map == null) {
                    $state.go("map-setup");
                }
            });

            return res;
        }

        function validateScript(currentProjectId) {
            var res = validate(currentProjectId, function (project) {
                if (project.script == null) {
                    $state.go("script-setup");
                }
            });

            return res;
        }

        function validate(currentProjectId, onSuccess) {
            if (currentProjectId == null) {
                $state.go("noproject");
            }

            var res = Project.get({id: currentProjectId}, onSuccess, onError);

            function onError() {
                $state.go("noproject");
            }

            return res;
        }
    }
})();
