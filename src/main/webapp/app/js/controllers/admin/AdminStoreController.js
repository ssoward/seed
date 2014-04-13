var app = angular.module('myApp').controller('AdminStoreController', function ($scope, AdminService, $log, $rootScope, $stateParams, $location, $state){
    $scope.userAdmin = false;

    function init(){
        AdminService.getLoggedInUser()
            .then(function(res){
                $scope.user = res.data;
                $scope.userAdmin = ($scope.user.auth == 'ROLE_ADMIN');
            })
            .then(function(){
                $scope.fetchAwards();
            });

    }

    //Need to init after all functions have been loaded into the scope.
    init();

    $scope.isActive = function(page){
        return $state.current.name == page;
    };

    $scope.getLink = function(page){
        $location.path(page);
    };

    $scope.fetchAwards = function() {
        AdminService.fetchAwards()
            .then(function(res){
                $scope.awards = res.data;
            });
    };

    $scope.uploadFile = function(files) {
        var fd = new FormData();
        //Take the first selected file
        fd.append("file", files[0]);

        AdminService.uploadFiles(fd)
            .then(function (res){
                $log.info('here');
                $log.info(res.data);
            }).then(function(){
                $scope.fetchAwards();
            });
    };

});
