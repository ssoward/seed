angular.module('myApp').config(function ($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('/home');

    $stateProvider
            .state('home', {
                url: '/home',
                templateUrl: 'partials/home.html',
                controller: 'HomeController'
            })
            .state('store', {
                url: '/store',
                templateUrl: 'partials/store.html',
                controller: 'StoreController'
            });

    //ADMIN states
    $stateProvider
            .state('admin', {
                url: '/admin',
                abstract: true,
                templateUrl: 'partials/admin/admin.html'
            })
//            .state('admin.home', {
//                url: '/home',
//                templateUrl: 'partials/admin.html',
//                controller: 'AdminController'
//            })
            .state('admin.users', {
                url: '/users',
                templateUrl: 'partials/admin/employeeAdmin.html',
                controller: 'AdminUsersController'
            })
            .state('admin.compliments', {
                url: '/compliments',
                templateUrl: 'partials/admin/complimentAdmin.html',
                controller: 'AdminComplimentsController'
            })
            .state('admin.give', {
                url: '/give',
                templateUrl: 'partials/admin/pointsToGiveAdmin.html',
                controller: 'AdminComplimentsController'
            })
            .state('admin.store', {
                url: '/store',
                templateUrl: 'partials/admin/storeAdmin.html',
                controller: 'AdminStoreController'
            })
    ;

});
