angular.module('myApp').controller('HomeController', function ($scope, ComplementService, GiveService, AdminService, $log){
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
    }

    $scope.showMessage = function(typee, msgg){
        $scope.alerts = [];
        $scope.alerts.push({type: typee, msg: msgg});
    }

    function init(){
        getUser();
        getPraises();
        getComplements();
    }

    function getComplements(){
        ComplementService.getAllComplements()
                .then(function(res){
                    $scope.complements = res.data;
                });
    }

    function getMembers(){
        AdminService.getAllUsers().then(function(res){
            $scope.members = res.data;
            for(var i = 0; i< $scope.members.length; i++){
                var mem = $scope.members[i];
                if(mem.email == $scope.user.email){
                    $scope.members.splice(i, 1);
                }
            }
        });
    }

    function getPraises(){
        GiveService.getAllPraises().then(function(res){
            $scope.gives = res.data;
        });
    }

    function clearPraise(){
        $scope.newPraise = null;
        $scope.newPraisee= null;
        $scope.newComment= null;
    }

    function getUser(){
        $scope.user = AdminService.getUser();
        AdminService.getLoggedInUser().then(function(res){
            $scope.user = res.data;
            getMembers();
        });
    }

    $scope.deletePraise = function(praise){
        GiveService.deletePraise(praise).then(function(res){
            $scope.showMessage('success', 'Successfully deleted praise.');
            getPraises();
        });
    }

    $scope.savePraise = function (){
        if($scope.newPraise || $scope.newPraisee){
            $scope.give = {};
            $scope.give.id = null;
            $scope.give.user = $scope.user.email;
            $scope.give.complement =  $scope.newPraise;
            $scope.give.givenTo = $scope.newPraisee.email;
            $scope.give.comment = $scope.newComment;
            $scope.give.givenDt = new Date();

            GiveService.savePraise($scope.give).then(function(res){
                $scope.showMessage('success', 'Successfully saved praise for '+ $scope.newPraisee.firstName+' '+$scope.newPraisee.lastName+'.');
                getUser();
                getPraises();
                clearPraise();
            });
        }
    };

    init();

});
