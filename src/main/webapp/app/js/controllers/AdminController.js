angular.module('myApp').controller('AdminController', function ($scope, HomeService, $log){
    $scope.greeting = 'Hello, world';
    init();
    function init(){
        updateUsers();
    }


    function updateUsers(){
        $scope.praiser = null;
        $scope.passwordConfirm = null;
        HomeService.getAllUsers().then(function(res){
            $scope.users = res.data;
        });
    }

    $scope.setUser = function (user){
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
});
