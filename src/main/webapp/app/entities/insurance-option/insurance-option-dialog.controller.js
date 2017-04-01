(function() {
    'use strict';

    angular
        .module('jappv2App')
        .controller('InsuranceOptionDialogController', InsuranceOptionDialogController);

    InsuranceOptionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'InsuranceOption'];

    function InsuranceOptionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, InsuranceOption) {
        var vm = this;

        vm.insuranceOption = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.insuranceOption.id !== null) {
                InsuranceOption.update(vm.insuranceOption, onSaveSuccess, onSaveError);
            } else {
                InsuranceOption.save(vm.insuranceOption, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jappv2App:insuranceOptionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
