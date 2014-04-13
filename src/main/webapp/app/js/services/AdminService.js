angular.module('myApp').service('AdminService', function ($http, $log) {
    var user = null;

    this.getLoggedInUser = function (){
        return $http({
            method: 'GET',
            url: '/api/loggedIn'
        }).then(function(res){
            user = res.data;
            return res;
        });
    };

    this.getAllUsers = function (){
        return $http({
            method: 'GET',
            url: '/api/employees'
        });
    };

    this.setUser = function (pro){
        user = pro;
    };

    this.getUser = function (){
        return user;
    };

    this.saveNewUser = function(praiser){
        return $http({
            method: 'POST',
            data: praiser,
            url: '/api/employee'
        });
    }

    this.fetchAwards = function(){
        return $http({
            method: 'GET',
            url: '/api/awards'
        });
    }
    this.uploadFiles = function(fd){
        return $http({
            method: 'POST',
            url: '/api/content-files/upload',
            data: fd,
            withCredentials: true,
            headers: {'Content-Type': undefined },
            transformRequest: angular.identity
        });
    }

    this.saveCount = function(praiser){
        return $http({
            method: 'PUT',
            data: praiser,
            url: '/api/employee/count'
        });
    }

    this.deleteUser = function(praiser){
        return $http({
            method: 'DELETE',
            params: {
                username: praiser.email
            },
            url: '/api/employee'
        });
    }

});