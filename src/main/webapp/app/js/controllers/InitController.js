angular.module('myApp').controller('InitController', function ($scope, PraiseService, AdminService, $log){
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
