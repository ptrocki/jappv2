(function() {
    'use strict';
    angular
        .module('jappv2App')
        .factory('Insurance', Insurance);

    Insurance.$inject = ['$resource'];

    function Insurance ($resource) {
        var resourceUrl =  'api/insurances/:id';

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
