'use strict';

describe('Controller Tests', function() {

    describe('Player Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPlayer, MockMap;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPlayer = jasmine.createSpy('MockPlayer');
            MockMap = jasmine.createSpy('MockMap');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Player': MockPlayer,
                'Map': MockMap
            };
            createController = function() {
                $injector.get('$controller')("PlayerDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'thefirstorderApp:playerUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
