angular.module('myApp').controller('AdminUsersController', function ($scope, AdminService, $log, $rootScope){
    $scope.greeting = 'Hello, world';
    $scope.newUsersShow = false;

    function init(){
        $scope.roles = getRoles();
        $scope.user = AdminService.getUser();
        if($scope.user){
            $scope.updateUsers();
        }else{
            AdminService.getLoggedInUser().then(function(res){
                $scope.user = res.data;
                $scope.updateUsers();
            });
        }
//        $scope.$on('divButton:clicked', function(event, message){
//            alert(message);
//        });

        $scope.numbers = getNumbers();
    }

    $scope.alerts = [
//        { type: 'danger', msg: 'Oh snap! Change a few things up and try submitting again.' },
//        { type: 'success', msg: 'Well done! You successfully read this important alert message.' }
    ];

    $scope.toggleNewUser = function() {
        $scope.newUsersShow = !$scope.newUsersShow;
        if(!$scope.newUserShow){
            $scope.toggleClear();
        }
    };

    $scope.closeAlert = function(index) {
        $scope.alerts.splice(index, 1);
    };

    $scope.updateUsers = function(){
        $scope.userAdmin = ($scope.user.auth == 'ROLE_ADMIN');
        $scope.toggleClear();
        AdminService.getAllUsers().then(function(res){
            $scope.users = res.data;
            //if this is not an admin user, they can only update their own account
            if(!$scope.userAdmin){
                for(var i = 0; i < $scope.users.length; i++){
                    if($scope.users[i].email == $scope.user.email){
                        $scope.setUser($scope.users[i]);
                    }
                }
            }
        });
    };

    $scope.toggleClear = function (){
        $scope.editUser = false;
        $scope.praiser = null;
        $scope.passwordConfirm = null;
    };

    $scope.updateCount = function (praiser){
        AdminService.saveCount(praiser).then(function(res){
//            $rootScope.$broadcast('divButton:clicked', 'hello world via event');
            //updateUsers();
            $scope.showMessage('success', 'Successfully saved count for '+ praiser.firstName+' to '+ praiser.count +'.');
        });
    };

    $scope.clearMessage = function(){
        $scope.alerts = [];
    }

    $scope.showMessage = function(typee, msgg){
        $scope.alerts = [];
        $scope.alerts.push({type: typee, msg: msgg});
    }

    $scope.setUser = function (user){
        $scope.newUsersShow = true;
        $scope.editUser = true;
        $scope.praiser = user;
        $scope.passwordConfirm = user.password;
    };

    $scope.saveNewUser = function (){
        if($scope.praiser && $scope.praiser.firstName && $scope.passwordConfirm === $scope.praiser.password ){
            AdminService.saveNewUser($scope.praiser).then(function(res){
                $scope.showMessage('success', 'Successfully saved user '+ $scope.praiser.firstName+'.');
                $scope.updateUsers();
            });
        }
        else{
            alert('Passwords don\'t match');
        }
    };

    $scope.deleteUser = function (user){
        AdminService.deleteUser(user).then(function(res){
            $scope.showMessage('success', 'Successfully deleted user '+ user.firstName+'.');
            $scope.updateUsers();
        });
    };

    function getRoles(){
        return [
            {name: 'Admin', value: 'ROLE_ADMIN'},
            {name: 'Auth', value: 'ROLE_AUTH'}
        ]
    }

    function getNumbers(){
        var numbers = [];
        for(var i = 0; i < 11; i++){
            numbers.push({name: i+'', value: i+''});
        }
        return numbers;
    }

    //Need to init after all functions have been loaded into the scope.
    init();
});
