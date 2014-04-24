angular.module('myApp').controller('StoreController', function ($scope, ComplementService, BuckService, AdminService, $log, AwardService){
    $scope.greeting = 'Hello, world';

    $scope.alerts = [
//        { type: 'danger', msg: 'Oh snap! Change a few things up and try submitting again.' },
//        { type: 'success', msg: 'Well done! You successfully read this important alert message.' }
    ];

    $scope.closeAlert = function(index) {
        $scope.alerts.splice(index, 1);
    };

    $scope.clearMessage = function(){
        $scope.alerts = [];
    };

    $scope.showMessage = function(typee, msgg){
        $scope.alerts = [];
        $scope.alerts.push({type: typee, msg: msgg});
    };

    function init(){
        $scope.fetchAwards()
            .then(function(res){
                getUser();
            });
    }

    function getUser(){
        $scope.user = AdminService.getUser();
        AdminService.getLoggedInUser().then(function(res){
            $scope.user = res.data;
        });
    }

    $scope.buyAward = function(awa) {
        if(awa && awa.cost > $scope.user.unspentBucks){
            $scope.showMessage('danger', 'Insufficient funds. You have '+$scope.user.unspentBucks+' bucks to spend and '+awa.title +' costs '+ awa.cost+'.');
        }else{
            BuckService.decrementBucks(awa)
                .then(function(res){
                    getUser();
                });
        }
    };

    $scope.fetchAwards = function() {
        return AwardService.fetchAwards()
            .then(function(res){
                $scope.awards = res.data;
            });
    };

    init();

});
