angular.module('myApp').controller('AdminController', function ($scope, HomeService, $log){
    $scope.greeting = 'Hello, world';
    init();
    function init(){
        HomeService.getAllUsers().then(function(res){
            var users = [];
            angular.forEach(res.data, function(item){
                users.push(item);
                $scope.users = users;
            });
        });
    }


    $scope.saveNewUser = function (){
        if($scope.passwordConfirm === $scope.praiser.password ){
            HomeService.saveNewUser($scope.praiser);
        }
        else{
            alert('Passwords don\'t match');
        }
    };
});
