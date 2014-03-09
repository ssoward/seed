angular.module('myApp').controller('AdminController', function ($scope, AdminService, $log, $rootScope, $stateParams, $location, $state){

    function init(){
    }

    //Need to init after all functions have been loaded into the scope.
    init();

    $scope.isActive = function(page){
        return $state.current.name == page;
    };

    $scope.getLink = function(page){
        $location.path(page);
    };
});
