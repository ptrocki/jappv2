(function() {
    'use strict';

    angular
        .module('jappv2App')
        .controller('InsuranceUserDetailController', InsuranceUserDetailController);

    InsuranceUserDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'InsuranceUser', 'Insurance'];

    function InsuranceUserDetailController($scope, $rootScope, $stateParams, previousState, entity, InsuranceUser, Insurance) {
        var vm = this;

        vm.insuranceUser = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('jappv2App:insuranceUserUpdate', function(event, result) {
            vm.insuranceUser = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
