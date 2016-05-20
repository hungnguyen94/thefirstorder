(function() {
    'use strict';
    angular
        .module('thefirstorderApp')
        .factory('ProjectDownload', ProjectDownload);

    ProjectDownload.$inject = ['$http'];

    function ProjectDownload ($http) {
        var resourceUrl =  'api/projects/';
        return {
            exportPdf: function (id) {
                return $http.get(
                    resourceUrl + id + '/exportpdf',
                    { responseType: 'arraybuffer' }
                );
            }
        }
    }
})();
