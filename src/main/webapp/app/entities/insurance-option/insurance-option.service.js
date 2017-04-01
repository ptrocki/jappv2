(function() {
    'use strict';
    angular
        .module('jappv2App')
        .factory('InsuranceOption', InsuranceOption);

    InsuranceOption.$inject = ['$resource'];

    function InsuranceOption ($resource) {
        var resourceUrl =  'api/insurance-options/:id';

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
