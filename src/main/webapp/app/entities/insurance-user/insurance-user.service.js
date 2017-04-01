(function() {
    'use strict';
    angular
        .module('jappv2App')
        .factory('InsuranceUser', InsuranceUser);

    InsuranceUser.$inject = ['$resource'];

    function InsuranceUser ($resource) {
        var resourceUrl =  'api/insurance-users/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
