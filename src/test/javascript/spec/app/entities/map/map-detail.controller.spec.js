'use strict';

describe('Controller Tests', function() {

    describe('Map Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockMap, MockProject, MockPlayer, MockCamera;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockMap = jasmine.createSpy('MockMap');
            MockProject = jasmine.createSpy('MockProject');
            MockPlayer = jasmine.createSpy('MockPlayer');
            MockCamera = jasmine.createSpy('MockCamera');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Map': MockMap,
                'Project': MockProject,
                'Player': MockPlayer,
                'Camera': MockCamera
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
