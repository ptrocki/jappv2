(function() {
    'use strict';

    angular
        .module('jappv2App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('insurance', {
            parent: 'entity',
            url: '/insurance',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Insurances'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/insurance/insurances.html',
                    controller: 'InsuranceController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('insurance-detail', {
            parent: 'insurance',
            url: '/insurance/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Insurance'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/insurance/insurance-detail.html',
                    controller: 'InsuranceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Insurance', function($stateParams, Insurance) {
                    return Insurance.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'insurance',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('insurance-detail.edit', {
            parent: 'insurance-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/insurance/insurance-dialog.html',
                    controller: 'InsuranceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Insurance', function(Insurance) {
                            return Insurance.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('insurance.new', {
            parent: 'insurance',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/insurance/insurance-dialog.html',
                    controller: 'InsuranceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('insurance', null, { reload: 'insurance' });
                }, function() {
                    $state.go('insurance');
                });
            }]
        })
        .state('insurance.edit', {
            parent: 'insurance',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/insurance/insurance-dialog.html',
                    controller: 'InsuranceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Insurance', function(Insurance) {
                            return Insurance.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('insurance', null, { reload: 'insurance' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('insurance.delete', {
            parent: 'insurance',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/insurance/insurance-delete-dialog.html',
                    controller: 'InsuranceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Insurance', function(Insurance) {
                            return Insurance.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('insurance', null, { reload: 'insurance' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
