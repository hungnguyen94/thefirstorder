'use strict';

describe('Controller Tests', function() {

    describe('Project Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockProject, MockScript, MockMap, MockCue;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockProject = jasmine.createSpy('MockProject');
            MockScript = jasmine.createSpy('MockScript');
            MockMap = jasmine.createSpy('MockMap');
            MockCue = jasmine.createSpy('MockCue');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Project': MockProject,
                'Script': MockScript,
                'Map': MockMap,
                'Cue': MockCue
            };
            createController = function() {
                $injector.get('$controller')("ProjectDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'thefirstorderApp:projectUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
