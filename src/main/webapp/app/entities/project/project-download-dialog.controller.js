(function() {
    'use strict';

    angular
        .module('thefirstorderApp')
        .controller('ProjectDownloadController', ProjectDownloadController);

    ProjectDownloadController.$inject = ['$uibModalInstance', 'entity', 'ProjectDownload', 'AlertService'];

    function ProjectDownloadController($uibModalInstance, entity, ProjectDownload, AlertService) {
        var vm = this;
        vm.project = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.confirmPDFDownload = function (id) {
            ProjectDownload.exportPdf(id).then(function (result) {
                var file = new Blob([result.data], {type: 'application/pdf'});
                var fileName = 'Project_' + id + '.pdf';
                saveAs(file, fileName);
                $uibModalInstance.close(true);
            });
        };

        vm.confirmXMLDownload = function (id) {
            ProjectDownload.exportXml(id).then(function (result) {
                var file = new Blob([result.data], {type: 'application/xml'});
                var fileName = 'Project_' + id + '.xml';
                saveAs(file, fileName);
                $uibModalInstance.close(true);
            });
        };
    }
})();
