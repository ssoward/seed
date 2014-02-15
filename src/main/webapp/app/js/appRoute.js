var blApp = angular.module('myApp');

blApp.config(['$routeProvider', function($routeProvider) {

    $routeProvider.when('/view1', {
      templateUrl: 'partials/partial1.html',
      controller: 'HomeController'
  });

    $routeProvider.when('/view2', {
      templateUrl: 'partials/partial2.html',
      controller: 'HomeController'
  });

    $routeProvider.otherwise({redirectTo: '/view1'});
}]);
