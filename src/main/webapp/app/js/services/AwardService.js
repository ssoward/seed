angular.module('myApp').service('AwardService', function ($http, $log) {

    this.updateAward = function(award){
        return $http({
            method: 'PUT',
            data: award,
            url: '/api/awards'
        });
    }

    this.getAllComplements = function (){
        return $http({
            method: 'GET',
            url: '/api/complements'
        });
    };

    this.deleteAward = function(award){
        return $http({
            method: 'DELETE',
            params: {
                id: award.id
            },
            url: '/api/awards'
        });
    };

    this.fetchAwards = function(){
        return $http({
            method: 'GET',
            url: '/api/awards'
        });
    };

    this.uploadFiles = function(fd){
        return $http({
            method: 'POST',
            url: '/api/content-files/upload',
            data: fd,
            withCredentials: true,
            headers: {'Content-Type': undefined },
            transformRequest: angular.identity
        });
    };

});