angular.module('myApp').controller('AdminAwardsLogController', function ($scope, AdminService, GiveService, $log, $rootScope, $stateParams, $location, $state){
    $scope.userAdmin = false;

    function init(){
        AdminService.getLoggedInUser()
            .then(function(res){
                $scope.user = res.data;
                $scope.userAdmin = ($scope.user.auth == 'ROLE_ADMIN');
                return res;
            })
            .then(function(res){
                $scope.fetchPurchasedLogs();
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

    $scope.getChecked = function(give){
        $log.info(give.id);
    };

    $scope.fetchPurchasedLogs = function() {
        GiveService.fetchPurchasedLogs()
            .then(function(res){
                $scope.gives = res.data;
//                angular.forEach($scope.gives, function(give) {
//                   give.distributed = give.awardReceivedDt;
//                });
            });
    };

    $scope.awardDistributed = function(give) {
        GiveService.awardDistributed(give)
            .then(function(res){
                $scope.gives = res.data;
            })
            .then(function(res){
                $scope.fetchPurchasedLogs();
            });
    };

});
