(function() {
    'use strict';

    angular
        .module('jappv2App')
        .controller('InsuranceController', InsuranceController);

    InsuranceController.$inject = ['Insurance'];

    function InsuranceController(Insurance) {

        var vm = this;

        vm.insurances = [];

        loadAll();

        function loadAll() {
            Insurance.query(function(result) {
                vm.insurances = result;
                vm.searchQuery = null;
            });
        }
    }
})();
