angular.module('myApp').controller('AdminComplimentsController', function ($scope, ComplimentService, AdminService, $log, $rootScope){

    function init(){
        ComplimentService.getAllCompliments()
                .then(function(res){
                    $scope.compliments = res.data;
                });
    }

    //Need to init after all functions have been loaded into the scope.
    init();

    $scope.alerts = [
//        { type: 'danger', msg: 'Oh snap! Change a few things up and try submitting again.' }
//        { type: 'success', msg: 'Well done! You successfully read this important alert message.' }
    ];

    $scope.save = function() {
        $log.debug($scope.newCompliment);
        $scope.compliment = {name:$scope.newCompliment};

        ComplimentService.saveCompliments($scope.compliment)
                .then(function(res){
                    ComplimentService.getAllCompliments()
                            .then(function(res){
                                $scope.compliments = res.data;
                                $scope.newCompliment = null;
                            });
                });
    };

    $scope.deleteCompliment = function(comp) {
        ComplimentService.deleteCompliment(comp)
                .then(function(res){
                    ComplimentService.getAllCompliments()
                            .then(function(res){
                                $scope.compliments = res.data;
                            });
                });

    }
    $scope.closeAlert = function(index) {
        $scope.alerts.splice(index, 1);
    };
});
