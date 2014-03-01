angular.module('myApp').controller('AdminController', function ($scope, HomeService, $log, $rootScope){
    $scope.greeting = 'Hello, world';
    init();
    function init(){
        $scope.roles = getRoles();
        HomeService.getLoggedInUser().then(function(res){
            $scope.user = res.data;
            $scope.userAdmin = ($scope.user.authorities[0].authority == 'ROLE_ADMIN');
            updateUsers();
        });
        $scope.$on('divButton:clicked', function(event, message){
            alert(message);
        });

        $scope.numbers = getNumbers();
    }

    $scope.alerts = [
//        { type: 'danger', msg: 'Oh snap! Change a few things up and try submitting again.' },
//        { type: 'success', msg: 'Well done! You successfully read this important alert message.' }
    ];

    $scope.closeAlert = function(index) {
        $scope.alerts.splice(index, 1);
    };

    function updateUsers(){
        $scope.toggleClear();
        HomeService.getAllUsers().then(function(res){
            $scope.users = res.data;
            //if this is not an admin user, they can only update their own account
            if(!$scope.userAdmin){
                for(var i = 0; i < $scope.users.length; i++){
                    if($scope.users[i].email == $scope.user.username){
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
        HomeService.saveCount(praiser).then(function(res){
//            $rootScope.$broadcast('divButton:clicked', 'hello world via event');
            //updateUsers();
            $scope.showMessage('success', 'Successfully saved count for user '+ praiser.firstName+'.');
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
        $scope.clearMessage();
        $scope.editUser = true;
        $scope.praiser = user;
        $scope.passwordConfirm = user.password;
    };

    $scope.saveNewUser = function (){
        if($scope.praiser && $scope.praiser.firstName && $scope.passwordConfirm === $scope.praiser.password ){
            HomeService.saveNewUser($scope.praiser).then(function(res){
                $scope.showMessage('success', 'Successfully saved user '+ $scope.praiser.firstName+'.');
                updateUsers();
            });
        }
        else{
            alert('Passwords don\'t match');
        }
    };

    $scope.deleteUser = function (user){
        HomeService.deleteUser(user).then(function(res){
            updateUsers();
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
        for(var i = 0; i < 101; i++){
            numbers.push({name: i+'', value: i+''});
        }
        return numbers;
    }
});
