angular.module('myApp').controller('AdminController', function ($scope, HomeService, $log){
    $scope.greeting = 'Hello, world';
    init();
    function init(){
        $scope.roles = getRoles();
        HomeService.getLoggedInUser().then(function(res){
            $scope.user = res.data;
            HomeService.setUser($scope.user);
            $scope.userAdmin = ($scope.user.authorities[0].authority == 'ROLE_ADMIN');
            updateUsers();
        });
    }

    function updateUsers(){
        $scope.toggleClear();
        HomeService.getAllUsers().then(function(res){
            $scope.users = res.data;
            //if this is not an admin user, they can only update their own account
            if(!$scope.userAdmin){
                for(var i = 0; i < $scope.users.length; i++){
                    if($scope.users[i].email == $scope.user.username){
                        $scope.setUser($scope.users[i]);
                    }
                }
            }
        });
    }

    $scope.toggleClear = function (){
        $scope.editUser = false;
        $scope.praiser = null;
        $scope.passwordConfirm = null;
    }

    $scope.setUser = function (user){
        $scope.editUser = true;
        $scope.praiser = user;
        $scope.passwordConfirm = user.password;
    };

    $scope.saveNewUser = function (){
        if($scope.praiser && $scope.praiser.firstName && $scope.passwordConfirm === $scope.praiser.password ){
            HomeService.saveNewUser($scope.praiser).then(function(res){
                updateUsers();
            });
        }
        else{
            alert('Passwords don\'t match');
        }
    };

    $scope.deleteUser = function (user){
        HomeService.deleteUser(user).then(function(res){
            updateUsers();
        });
    };

    function getRoles(){
        return [
            {name: 'Admin', value: 'ROLE_ADMIN'},
            {name: 'Auth', value: 'ROLE_AUTH'}
        ]
    }
});
