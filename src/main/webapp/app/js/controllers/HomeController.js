angular.module('myApp').controller('HomeController', function ($scope, HomeService, $log){
    $scope.greeting = 'Hello, world';
    init();
    function init(){
        HomeService.getLoggedInUser().then(function(res){
            $scope.user = res.data;
            HomeService.setUser($scope.user);
        });
    }
});
