angular.module('myApp').service('ComplimentService', function ($http, $log) {

    this.saveCompliments = function(compliment){
        return $http({
            method: 'POST',
            data: compliment,
            url: '/api/compliments'
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