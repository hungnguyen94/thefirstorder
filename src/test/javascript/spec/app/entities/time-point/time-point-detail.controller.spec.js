'use strict';

describe('Controller Tests', function() {

    describe('TimePoint Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockTimePoint, MockCue;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockTimePoint = jasmine.createSpy('MockTimePoint');
            MockCue = jasmine.createSpy('MockCue');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'TimePoint': MockTimePoint,
                'Cue': MockCue
            };
            createController = function() {
                $injector.get('$controller')("TimePointDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'thefirstorderApp:timePointUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
