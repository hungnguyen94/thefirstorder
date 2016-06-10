'use strict';

describe('Controller Tests', function() {

    describe('Camera Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCamera, MockMap;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCamera = jasmine.createSpy('MockCamera');
            MockMap = jasmine.createSpy('MockMap');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Camera': MockCamera,
                'Map': MockMap
            };
            createController = function() {
                $injector.get('$controller')("CameraDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'thefirstorderApp:cameraUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
