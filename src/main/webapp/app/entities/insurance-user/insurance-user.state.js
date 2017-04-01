(function() {
    'use strict';

    angular
        .module('jappv2App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('insurance-user', {
            parent: 'entity',
            url: '/insurance-user',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'InsuranceUsers'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/insurance-user/insurance-users.html',
                    controller: 'InsuranceUserController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('insurance-user-detail', {
            parent: 'insurance-user',
            url: '/insurance-user/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'InsuranceUser'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/insurance-user/insurance-user-detail.html',
                    controller: 'InsuranceUserDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'InsuranceUser', function($stateParams, InsuranceUser) {
                    return InsuranceUser.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'insurance-user',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('insurance-user-detail.edit', {
            parent: 'insurance-user-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/insurance-user/insurance-user-dialog.html',
                    controller: 'InsuranceUserDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['InsuranceUser', function(InsuranceUser) {
                            return InsuranceUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('insurance-user.new', {
            parent: 'insurance-user',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/insurance-user/insurance-user-dialog.html',
                    controller: 'InsuranceUserDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                firstName: null,
                                lastName: null,
                                email: null,
                                phoneNumber: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('insurance-user', null, { reload: 'insurance-user' });
                }, function() {
                    $state.go('insurance-user');
                });
            }]
        })
        .state('insurance-user.edit', {
            parent: 'insurance-user',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/insurance-user/insurance-user-dialog.html',
                    controller: 'InsuranceUserDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['InsuranceUser', function(InsuranceUser) {
                            return InsuranceUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('insurance-user', null, { reload: 'insurance-user' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('insurance-user.delete', {
            parent: 'insurance-user',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/insurance-user/insurance-user-delete-dialog.html',
                    controller: 'InsuranceUserDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['InsuranceUser', function(InsuranceUser) {
                            return InsuranceUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('insurance-user', null, { reload: 'insurance-user' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
