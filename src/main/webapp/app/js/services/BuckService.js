angular.module('myApp').service('BuckService', function ($http, $log) {

    this.decrementBucks = function(award){
        return $http({
            method: 'PUT',
            data: award,
            url: '/api/buck/buy'
        });
    }

    this.getAllCompliments = function (){
        return $http({
            method: 'GET',
            url: '/api/compliments'
        });
    };

    this.deleteCompliment = function(praise){
        return $http({
            method: 'DELETE',
            params: {
                id: praise.id
            },
            url: '/api/compliments'
        });
    }

});