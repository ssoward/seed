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
});