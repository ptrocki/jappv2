(function() {
    'use strict';

    angular
        .module('jappv2App')
        .controller('InsuranceUserDeleteController',InsuranceUserDeleteController);

    InsuranceUserDeleteController.$inject = ['$uibModalInstance', 'entity', 'InsuranceUser'];

    function InsuranceUserDeleteController($uibModalInstance, entity, InsuranceUser) {
        var vm = this;

        vm.insuranceUser = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            InsuranceUser.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
