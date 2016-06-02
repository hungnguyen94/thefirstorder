/**
 * Created by Hung.
 */
(function() {
    'use strict';
    angular
        .module('thefirstorderApp')
        .factory('ProjectManager', ProjectManager);

    ProjectManager.$inject = ['$http', '$state', 'Project'];

    function ProjectManager ($http, $state, Project) {
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

        // function validateProject() {
        //     this.get().then(function(currentProject) {
        //         if (currentProject == null) {
        //             failProject();
        //             return null;
        //         }
        //
        //         return Project.get({id: currentProject.data}, onLoadSuccess, onLoadError);
        //
        //         function onLoadSuccess(project) {
        //             var map = project.map;
        //             if(map == null){
        //                 failMap();
        //             }
        //         };
        //
        //         function onLoadError(error) {
        //             failProject();
        //             return null;
        //         }
        //     });
        // }
        //
        // function failProject(){
        //     $state.go("noproject");
        // }
        //
        // function failMap(){
        //     $state.go("map-setup");
        // }
    }
})();
