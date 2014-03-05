angular.module('myApp').controller('AdminComplimentsController', function ($scope, AdminService, $log, $rootScope){

    function init(){
    }

    //Need to init after all functions have been loaded into the scope.
    init();

    $scope.alerts = [
//        { type: 'danger', msg: 'Oh snap! Change a few things up and try submitting again.' }
//        { type: 'success', msg: 'Well done! You successfully read this important alert message.' }
    ];

    $scope.closeAlert = function(index) {
        $scope.alerts.splice(index, 1);
    };
});
