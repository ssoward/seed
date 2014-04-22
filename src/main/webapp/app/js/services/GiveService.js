angular.module('myApp').service('GiveService', function ($http, $log) {

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
            url: '/api/give/praises'
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

    this.awardDistributed = function(give){
        return $http({
            method: 'PUT',
            data: give,
            url: '/api/gives/distributed'
        });
    }

    this.fetchPurchasedLogs = function (){
        return $http({
            method: 'GET',
            url: '/api/gives/logs'
        });
    };

});