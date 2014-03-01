angular.module('myApp').controller('HomeController', function ($scope, PraiseService, AdminService, $log){
    $scope.greeting = 'Hello, world';

    $scope.compliments = [
        {id:1,name:'Kite Flying - Going above and beyond!'},
        {id:2,name:'Great support when needed it the most!'},
        {id:3,name:'Putting Families first!'},
        {id:4,name:'Super Hero Clinical Skills'},
        {id:4,name:'Other'}
    ];

    $scope.history = [
        {date:' 11 Apr 2013', praiser:'Scott', praisee:'Larry', praise:$scope.compliments[0].name},
        {date:' 11 Apr 2013', praiser:'Mike' , praisee:'Myra' , praise:$scope.compliments[1].name},
        {date:' 11 Apr 2013', praiser:'Ike'  , praisee:'Adam' , praise:$scope.compliments[2].name},
        {date:' 11 Apr 2013', praiser:'Lala' , praisee:'Anna' , praise:$scope.compliments[3].name}
    ];

    function init(){
        getUser();
    }

    function getMembers(){
        AdminService.getAllUsers().then(function(res){
            $scope.members = res.data;
        });
    }

    function getUser(){
        $scope.user = AdminService.getUser();
        if(!$scope.user){
            AdminService.getLoggedInUser().then(function(res){
                $scope.user = res.data;
                getMembers();
            });
        }else{
            getMembers();
        }
    }

    $scope.savePraise = function (){

        if($scope.newPraise || $scope.newPraiser){
            $scope.praise = {};
            $scope.praise.id = null;
            $scope.praise.praiser= $scope.newPraiser;
            $scope.praise.praise = $scope.newPraise;
            $scope.praise.praisee = $scope.user.email;
            $scope.praise.comment = '';
            $scope.praise.praiseDt = new Date();

            PraiseService.savePraise($scope.praise).then(function(res){
                $scope.members = res.data;
            });
        }
    };

    init();

});
