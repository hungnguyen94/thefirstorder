'use strict';

describe('Controller Tests', function() {

    describe('Script Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockScript, MockCue, MockProject;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockScript = jasmine.createSpy('MockScript');
            MockCue = jasmine.createSpy('MockCue');
            MockProject = jasmine.createSpy('MockProject');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Script': MockScript,
                'Cue': MockCue,
                'Project': MockProject
            };
            createController = function() {
                $injector.get('$controller')("ScriptDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'thefirstorderApp:scriptUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
