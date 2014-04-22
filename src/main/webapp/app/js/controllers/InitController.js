angular.module('myApp').controller('InitController', function ($scope, GiveService, AdminService, $log){
    $scope.userAdmin = false;
    init();
    function init(){
        AdminService.getLoggedInUser()
                .then(function(res){
                    $scope.user = res.data;
                    $scope.userAdmin = ($scope.user.auth == 'ROLE_ADMIN');
                });
    }
});
