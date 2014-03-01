angular.module('myApp').service('PraiseService', function ($http, $log) {

    this.savePraise = function(praise){
        return $http({
            method: 'POST',
            data: praise,
            url: '/api/praise'
        });
    }

    this.getAllPraises = function (){
        return $http({
            method: 'GET',
            url: '/api/praises'
        });
    };

    this.deletePraise = function(praise){
        return $http({
            method: 'DELETE',
            params: {
                id: praise.id
            },
            url: '/api/praise'
        });
    }

});