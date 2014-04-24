angular.module('myApp').controller('AdminComplementsController', function ($scope, ComplementService, AdminService, $log, $rootScope){

    function init(){
        ComplementService.getAllComplements()
                .then(function(res){
                    $scope.complements = res.data;
                });
    };

    //Need to init after all functions have been loaded into the scope.
    init();

    $scope.alerts = [
//        { type: 'danger', msg: 'Oh snap! Change a few things up and try submitting again.' }
//        { type: 'success', msg: 'Well done! You successfully read this important alert message.' }
    ];

    $scope.save = function() {
        $scope.complement = {name:$scope.newComplement};

        ComplementService.saveComplements($scope.complement)
                .then(function(res){
                    ComplementService.getAllComplements()
                            .then(function(res){
                                $scope.complements = res.data;
                                $scope.newComplement = null;
                            });
                });
    };

    $scope.deleteComplement = function(comp) {
        ComplementService.deleteComplement(comp)
                .then(function(res){
                    ComplementService.getAllComplements()
                            .then(function(res){
                                $scope.complements = res.data;
                            });
                });
    };

    $scope.closeAlert = function(index) {
        $scope.alerts.splice(index, 1);
    };
});
