'use strict';

describe('Controller Tests', function() {

    describe('Map Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockMap, MockProject;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockMap = jasmine.createSpy('MockMap');
            MockProject = jasmine.createSpy('MockProject');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Map': MockMap,
                'Project': MockProject
            };
            createController = function() {
                $injector.get('$controller')("MapDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'thefirstorderApp:mapUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
