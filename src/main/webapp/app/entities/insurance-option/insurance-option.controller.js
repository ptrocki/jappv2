(function() {
    'use strict';

    angular
        .module('jappv2App')
        .controller('InsuranceOptionController', InsuranceOptionController);

    InsuranceOptionController.$inject = ['InsuranceOption'];

    function InsuranceOptionController(InsuranceOption) {

        var vm = this;

        vm.insuranceOptions = [];

        loadAll();

        function loadAll() {
            InsuranceOption.query(function(result) {
                vm.insuranceOptions = result;
                vm.searchQuery = null;
            });
        }
    }
})();
