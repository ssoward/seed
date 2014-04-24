angular.module('myApp').service('ComplementService', function ($http, $log) {

    this.saveComplements = function(complement){
        return $http({
            method: 'POST',
            data: complement,
            url: '/api/complements'
        });
    }

    this.getAllComplements = function (){
        return $http({
            method: 'GET',
            url: '/api/complements'
        });
    };

    this.deleteComplement = function(praise){
        return $http({
            method: 'DELETE',
            params: {
                id: praise.id
            },
            url: '/api/complements'
        });
    }

});