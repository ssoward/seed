angular.module('myApp').controller('HomeController', function ($scope, PraiseService, AdminService, $log){
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

    $scope.compliments = [
        {id:1,name:'Kite Flying - Going above and beyond!'},
        {id:2,name:'Great support when needed it the most!'},
        {id:3,name:'Putting Families first!'},
        {id:4,name:'Super Hero Clinical Skills'},
        {id:4,name:'Other'}
    ];

//    $scope.history = [
//        {date:' 11 Apr 2013', praiser:'Scott', praisee:'Larry', praise:$scope.compliments[0].name},
//        {date:' 11 Apr 2013', praiser:'Mike' , praisee:'Myra' , praise:$scope.compliments[1].name},
//        {date:' 11 Apr 2013', praiser:'Ike'  , praisee:'Adam' , praise:$scope.compliments[2].name},
//        {date:' 11 Apr 2013', praiser:'Lala' , praisee:'Anna' , praise:$scope.compliments[3].name}
//    ];

    function init(){
        getUser();
        getPraises();
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
            $scope.praise.praise = $scope.newPraise;
            $scope.praise.praisee = $scope.newPraisee;
            $scope.praise.comment = '';
            $scope.praise.praiseDt = new Date();

            PraiseService.savePraise($scope.praise).then(function(res){
                $scope.showMessage('success', 'Successfully saved praise for '+ $scope.newPraisee+'.');
                getUser();
                getPraises();
            });
        }
    };

    init();

});
