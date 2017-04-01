(function() {
    'use strict';

    angular
        .module('jappv2App')
        .controller('InsuranceDialogController', InsuranceDialogController);

    InsuranceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Insurance', 'InsuranceUser', 'InsuranceOption', 'Address'];

    function InsuranceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Insurance, InsuranceUser, InsuranceOption, Address) {
        var vm = this;

        vm.insurance = entity;
        vm.clear = clear;
        vm.save = save;
        vm.insuranceusers = InsuranceUser.query();
        vm.insuranceoptions = InsuranceOption.query();
        vm.addresses = Address.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.insurance.id !== null) {
                Insurance.update(vm.insurance, onSaveSuccess, onSaveError);
            } else {
                Insurance.save(vm.insurance, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jappv2App:insuranceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
