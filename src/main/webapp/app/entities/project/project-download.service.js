(function() {
    'use strict';
    angular
        .module('thefirstorderApp')
        .factory('ProjectDownload', ProjectDownload);

    ProjectDownload.$inject = ['$http'];

    function ProjectDownload ($http) {
        var resourceUrl =  'api/projects/';
        var services = {
            exportPdf: exportPdf,
            exportXml: exportXml
        }
        return services;

        function exportPdf (id) {
            return $http.get(
                resourceUrl + id + '/exportpdf',
                { responseType: 'arraybuffer' }
            );
        };
        function exportXml (id) {
            return $http.get(
                resourceUrl + id + '/exportxml',
                { responseType: 'arraybuffer' }
            );
        }
    }
})();
