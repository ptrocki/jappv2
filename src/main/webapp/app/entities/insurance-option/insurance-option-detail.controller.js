(function() {
    'use strict';

    angular
        .module('jappv2App')
        .controller('InsuranceOptionDetailController', InsuranceOptionDetailController);

    InsuranceOptionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'InsuranceOption'];

    function InsuranceOptionDetailController($scope, $rootScope, $stateParams, previousState, entity, InsuranceOption) {
        var vm = this;

        vm.insuranceOption = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('jappv2App:insuranceOptionUpdate', function(event, result) {
            vm.insuranceOption = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
