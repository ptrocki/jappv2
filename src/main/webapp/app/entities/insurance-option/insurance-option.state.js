(function() {
    'use strict';

    angular
        .module('jappv2App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('insurance-option', {
            parent: 'entity',
            url: '/insurance-option',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'InsuranceOptions'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/insurance-option/insurance-options.html',
                    controller: 'InsuranceOptionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('insurance-option-detail', {
            parent: 'insurance-option',
            url: '/insurance-option/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'InsuranceOption'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/insurance-option/insurance-option-detail.html',
                    controller: 'InsuranceOptionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'InsuranceOption', function($stateParams, InsuranceOption) {
                    return InsuranceOption.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'insurance-option',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('insurance-option-detail.edit', {
            parent: 'insurance-option-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/insurance-option/insurance-option-dialog.html',
                    controller: 'InsuranceOptionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['InsuranceOption', function(InsuranceOption) {
                            return InsuranceOption.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('insurance-option.new', {
            parent: 'insurance-option',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/insurance-option/insurance-option-dialog.html',
                    controller: 'InsuranceOptionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                fisrt: null,
                                second: null,
                                third: null,
                                fourth: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('insurance-option', null, { reload: 'insurance-option' });
                }, function() {
                    $state.go('insurance-option');
                });
            }]
        })
        .state('insurance-option.edit', {
            parent: 'insurance-option',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/insurance-option/insurance-option-dialog.html',
                    controller: 'InsuranceOptionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['InsuranceOption', function(InsuranceOption) {
                            return InsuranceOption.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('insurance-option', null, { reload: 'insurance-option' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('insurance-option.delete', {
            parent: 'insurance-option',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/insurance-option/insurance-option-delete-dialog.html',
                    controller: 'InsuranceOptionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['InsuranceOption', function(InsuranceOption) {
                            return InsuranceOption.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('insurance-option', null, { reload: 'insurance-option' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
