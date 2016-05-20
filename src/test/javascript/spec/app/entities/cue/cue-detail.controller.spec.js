'use strict';

describe('Controller Tests', function() {

    describe('Cue Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCue, MockScript, MockCameraAction, MockTimePoint, MockPlayer, MockCamera;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCue = jasmine.createSpy('MockCue');
            MockScript = jasmine.createSpy('MockScript');
            MockCameraAction = jasmine.createSpy('MockCameraAction');
            MockTimePoint = jasmine.createSpy('MockTimePoint');
            MockPlayer = jasmine.createSpy('MockPlayer');
            MockCamera = jasmine.createSpy('MockCamera');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Cue': MockCue,
                'Script': MockScript,
                'CameraAction': MockCameraAction,
                'TimePoint': MockTimePoint,
                'Player': MockPlayer,
                'Camera': MockCamera
            };
            createController = function() {
                $injector.get('$controller')("CueDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'thefirstorderApp:cueUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
