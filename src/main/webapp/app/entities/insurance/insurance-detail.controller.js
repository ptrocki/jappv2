(function() {
    'use strict';

    angular
        .module('jappv2App')
        .controller('InsuranceDetailController', InsuranceDetailController);

    InsuranceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Insurance', 'InsuranceUser', 'InsuranceOption', 'Address'];

    function InsuranceDetailController($scope, $rootScope, $stateParams, previousState, entity, Insurance, InsuranceUser, InsuranceOption, Address) {
        var vm = this;

        vm.insurance = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('jappv2App:insuranceUpdate', function(event, result) {
            vm.insurance = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
