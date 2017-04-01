(function() {
    'use strict';

    angular
        .module('jappv2App')
        .controller('InsuranceController', InsuranceController);

    InsuranceController.$inject = ['Insurance','$scope'];

    function InsuranceController(Insurance,$scope) {
        var vm = this;
        vm.insurances = [];

        vm.loadAll = function (currentSearch) {
            Insurance.query({query: currentSearch}, function(result) {
                vm.insurances = result;
            });
        }

        vm.search = function (searchQuery) {
            $scope.page = 1;
            $scope.predicate = 'id';
            $scope.reverse = true;
            $scope.insuranceQuery = searchQuery;
            vm.loadAll(searchQuery);
        };

        vm.clear = function () {
            $scope.page = 0;
            $scope.predicate = 'id';
            $scope.reverse = true;
            $scope.insuranceQuery = null;
            vm.loadAll();
        };

        vm.loadAll();
    }
})();
