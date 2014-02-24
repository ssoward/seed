angular.module('myApp').controller('HomeController', function ($scope, HomeService, $log){
    $scope.greeting = 'Hello, world';

    $scope.compliments = [
        {id:1,name:'Kite Flying - Going above and beyond!'},
        {id:2,name:'Great support when needed it the most!'},
        {id:3,name:'Putting Families first!'},
        {id:4,name:'Super Hero Clinical Skills'}
    ];

    $scope.members = [
        {id:1,name:'Mike David Soward'},
        {id:2,name:'Adam Michael Soward'},
        {id:3,name:'Joshua Scott Soward'},
        {id:4,name:'Ivy Lauren Soward'},
        {id:5,name:'Micah David Soward'}
    ];

    $scope.history = [
        {date:' 11 Apr 2013', praiser:'Scott', praisee:'Larry', praise:$scope.compliments[0].name},
        {date:' 11 Apr 2013', praiser:'Mike' , praisee:'Myra' , praise:$scope.compliments[1].name},
        {date:' 11 Apr 2013', praiser:'Ike'  , praisee:'Adam' , praise:$scope.compliments[2].name},
        {date:' 11 Apr 2013', praiser:'Lala' , praisee:'Anna' , praise:$scope.compliments[3].name}
    ];

    init();
    function init(){
        HomeService.getLoggedInUser().then(function(res){
            $scope.user = res.data;
            HomeService.setUser($scope.user);
        });
    }

    $scope.savePraise = function (){
        $log.debug($scope.newPraisee);
        $log.debug($scope.newPraise);

        //        if($scope.proposal.releaseReason.id || $scope.proposal.releaseReasonOtherDesc){
//            BishopService.saveProposalReleaseReason($scope.proposal.releaseReason.id, $scope.proposal.releaseReasonOtherDesc);
//        }
    };

});
