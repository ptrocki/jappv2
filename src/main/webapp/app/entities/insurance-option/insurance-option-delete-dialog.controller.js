(function() {
    'use strict';

    angular
        .module('jappv2App')
        .controller('InsuranceOptionDeleteController',InsuranceOptionDeleteController);

    InsuranceOptionDeleteController.$inject = ['$uibModalInstance', 'entity', 'InsuranceOption'];

    function InsuranceOptionDeleteController($uibModalInstance, entity, InsuranceOption) {
        var vm = this;

        vm.insuranceOption = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            InsuranceOption.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
