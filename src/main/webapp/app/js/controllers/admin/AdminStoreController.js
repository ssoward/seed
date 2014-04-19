var app = angular.module('myApp').controller('AdminStoreController', function ($scope, AwardService, AdminService, $log, $rootScope, $stateParams, $location, $state){
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
        AwardService.fetchAwards()
            .then(function(res){
                $scope.awards = res.data;
            });
    };

    $scope.deleteAward = function(award) {
        AwardService.deleteAward(award)
            .then(function(res){
                return res;
            })
            .then(function(){
                $scope.fetchAwards();
            });
    };

    $scope.uploadFile = function(files) {
        var fd = new FormData();
        //Take the first selected file
        fd.append("file", files[0]);

        AwardService.uploadFiles(fd)
            .then(function (res){
            })
            .then(function(){
                $scope.fetchAwards();
            });
    };

    $scope.updateAward = function(value) {
        AwardService.updateAward(value);
        //console.log('Saving title ' + value.cost);
//        alert('Saving title ' + value);
    };

    $scope.cancelEdit = function(value) {
//        console.log('Canceled editing', value);
        //alert('Canceled editing of ' + value);
    };

});
