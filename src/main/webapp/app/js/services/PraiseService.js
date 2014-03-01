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
            url: '/praises'
        });
    };
});