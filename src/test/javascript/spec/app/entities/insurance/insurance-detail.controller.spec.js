'use strict';

describe('Controller Tests', function() {

    describe('Insurance Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockInsurance, MockInsuranceUser, MockInsuranceOption, MockAddress;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockInsurance = jasmine.createSpy('MockInsurance');
            MockInsuranceUser = jasmine.createSpy('MockInsuranceUser');
            MockInsuranceOption = jasmine.createSpy('MockInsuranceOption');
            MockAddress = jasmine.createSpy('MockAddress');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Insurance': MockInsurance,
                'InsuranceUser': MockInsuranceUser,
                'InsuranceOption': MockInsuranceOption,
                'Address': MockAddress
            };
            createController = function() {
                $injector.get('$controller')("InsuranceDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jappv2App:insuranceUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
