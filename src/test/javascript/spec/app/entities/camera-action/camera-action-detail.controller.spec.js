'use strict';

describe('Controller Tests', function() {

    describe('CameraAction Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCameraAction, MockCue, MockCamera;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCameraAction = jasmine.createSpy('MockCameraAction');
            MockCue = jasmine.createSpy('MockCue');
            MockCamera = jasmine.createSpy('MockCamera');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'CameraAction': MockCameraAction,
                'Cue': MockCue,
                'Camera': MockCamera
            };
            createController = function() {
                $injector.get('$controller')("CameraActionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'thefirstorderApp:cameraActionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
