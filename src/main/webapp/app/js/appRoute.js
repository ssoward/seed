var blApp = angular.module('myApp');

blApp.config(['$routeProvider', function($routeProvider) {

    $routeProvider.when('/view1', {
      templateUrl: 'partials/partial1.html',
      controller: 'ControllerHome'
  });

    $routeProvider.when('/view2', {
      templateUrl: 'partials/partial2.html',
      controller: 'MyCtrl2'
  });

    $routeProvider.otherwise({redirectTo: '/view1'});
}]);
