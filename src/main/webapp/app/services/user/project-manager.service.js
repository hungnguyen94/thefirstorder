(function () {
    'use strict';
    angular
        .module('thefirstorderApp')
        .factory('ProjectManager', ProjectManager);

    ProjectManager.$inject = ['$http', '$state', 'Project'];

    function ProjectManager($http, $state, Project) {
        return {
            get: get,
            update: update,
            getProjectId: getProjectId,
            getProject: getProject,
            validateProject: validateProject,
            validateMap: validateMap,
            validateScript: validateScript
        };

        /**
         * Promises the current project id of the active user.
         *
         * @returns {*} Promises the current project id of the active user.
         */
        function get() {
            return $http.get('api/account/currentproject');
        }


        /**
         * Updates the current project id of the active user.
         *
         * @param id the is
         * @returns {*|Request}
         */
        function update(id) {
            return $http.put(
                'api/account/update_currentproject',
                {},
                {params: {projectId: id}}
            );
        }

        /**
         * Gets the current project id of the active user
         *
         * @returns {*} the current project id of the active user,
         *  returns null if the active user hasn't got a current project.
         */
        function getProjectId() {
            return this.get().then(onLoadSuccess, onLoadError);

            function onLoadSuccess(result) {
                return result.data;
            }

            function onLoadError() {
                return null;
            }
        }

        /**
         * Gets the current project of the active user
         *
         * @returns {*} the current project of the active user,
         *  returns null if the active user hasn't got a current project.
         */
        function getProject() {
            return this.get().then(onLoadSuccess, onLoadError);

            function onLoadSuccess(result) {
                return Project.get({id: result.data}, onSuccess, onError);

                function onSuccess(project) {
                    return project;
                }

                function onError() {
                    return null;
                }
            }

            function onLoadError() {
                return null;
            }
        }

        /**
         * Validates that the project with id given exists, goes to the noproject state if it does not.
         *
         * @param currentProjectId should be the id of a project.
         * @returns {*} The project.
         */
        function validateProject(currentProjectId) {
            return validate(currentProjectId, function (project) {});
        }

        /**
         * Validates that a project has a map. Promises the project containing the map.
         * Goes to the map-setup state when the project hasn't got a map.
         *
         * @param projectId should be the id of a validated project.
         * @returns {Promise} Promises the project containing the map.
         */
        function validateMap(projectId) {
            validate(projectId, function(project) {
                if (project.map == null) {
                    $state.go("map-setup");
                }
            });
        }

        /**
         * Validates that a project has a script. Promises the project containing the script.
         * Goes to the script-setup state when the project hasn't got a script.
         *
         * @param projectId should be the id of a validated project.
         * @returns {Promise} Promises the project containing the script.
         */
        function validateScript(projectId) {
            validate(projectId, function(project) {
                if (project.script == null) {
                    $state.go("script-setup");
                }
            });
        }

        /**
         * Validates that the project with id given exists, goes to the noproject state if it does not.
         * If the project exists, it executes the given function, with the project as argument.
         *
         * @param currentProjectId should be the id of a project.
         * @param onExistingProject should be a function, executed when the project exists.
         * @returns {*} The project.
         */
        function validate(currentProjectId, onExistingProject) {
            if (currentProjectId == null) {
                $state.go("noproject");
            }

            return Project.get({id: currentProjectId}, onExistingProject, onNonexistingProject);

            function onNonexistingProject() {
                $state.go("noproject");
            }
        }
    }
})();
