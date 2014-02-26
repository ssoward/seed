angular.module('myApp').service('HomeService', function ($http, $log) {
    var user = {};

    this.getLoggedInUser = function (){
        return $http({
            method: 'GET',
            url: '/api/loggedIn'
        });
    };

    this.getAllUsers = function (){
        return $http({
            method: 'GET',
            url: '/users'
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
            url: '/api/praiser'
        });
    }

    this.deleteUser = function(praiser){
        return $http({
            method: 'DELETE',
            params: {
                username: praiser.email
            },
            url: '/api/praiser'
        });
    }

});