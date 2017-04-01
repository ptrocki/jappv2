'use strict';

describe('Controller Tests', function() {

    describe('InsuranceUser Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockInsuranceUser, MockInsurance;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockInsuranceUser = jasmine.createSpy('MockInsuranceUser');
            MockInsurance = jasmine.createSpy('MockInsurance');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'InsuranceUser': MockInsuranceUser,
                'Insurance': MockInsurance
            };
            createController = function() {
                $injector.get('$controller')("InsuranceUserDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jappv2App:insuranceUserUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
