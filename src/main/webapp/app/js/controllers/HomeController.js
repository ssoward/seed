angular.module('myApp').controller('HomeController', function ($scope, ComplimentService, PraiseService, AdminService, $log){
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
        getCompliments();
    }

    function getCompliments(){
        ComplimentService.getAllCompliments()
                .then(function(res){
                    $scope.compliments = res.data;
                });
    }

    function getMembers(){
        AdminService.getAllUsers().then(function(res){
            $scope.members = res.data;
        });
    }

    function getPraises(){
        PraiseService.getAllPraises().then(function(res){
            $scope.praises = res.data;
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
        PraiseService.deletePraise(praise).then(function(res){
            $scope.showMessage('success', 'Successfully deleted praise.');
            getPraises();
        });
    }

    $scope.savePraise = function (){

        if($scope.newPraise || $scope.newPraisee){
            $scope.praise = {};
            $scope.praise.id = null;
            $scope.praise.praiser= $scope.user.email;
            $scope.praise.praise =  $scope.newPraise;
            $scope.praise.praisee = $scope.newPraisee;
            $scope.praise.comment = $scope.newComment;
            $scope.praise.praiseDt = new Date();
            $log.debug($scope.praise.comment);

            PraiseService.savePraise($scope.praise).then(function(res){
                $scope.showMessage('success', 'Successfully saved praise for '+ $scope.newPraisee+'.');
                getUser();
                getPraises();
                clearPraise();
            });
        }
    };

    init();

});
