(function() {
    'use strict';

    angular
        .module('jappv2App')
        .controller('InsuranceUserController', InsuranceUserController);

    InsuranceUserController.$inject = ['InsuranceUser'];

    function InsuranceUserController(InsuranceUser) {

        var vm = this;

        vm.insuranceUsers = [];

        loadAll();

        function loadAll() {
            InsuranceUser.query(function(result) {
                vm.insuranceUsers = result;
                vm.searchQuery = null;
            });
        }
    }
})();
