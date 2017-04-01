(function() {
    'use strict';

    angular
        .module('jappv2App')
        .controller('InsuranceUserDialogController', InsuranceUserDialogController);

    InsuranceUserDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'InsuranceUser', 'Insurance'];

    function InsuranceUserDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, InsuranceUser, Insurance) {
        var vm = this;

        vm.insuranceUser = entity;
        vm.clear = clear;
        vm.save = save;
        vm.insurances = Insurance.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.insuranceUser.id !== null) {
                InsuranceUser.update(vm.insuranceUser, onSaveSuccess, onSaveError);
            } else {
                InsuranceUser.save(vm.insuranceUser, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jappv2App:insuranceUserUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
