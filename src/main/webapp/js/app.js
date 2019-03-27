'use strict';

var app = angular.module('app', ['ui.router', 'apMesa']);

app.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('/');

    $stateProvider
        .state('home', {
            url: '/',
            templateUrl: 'html/home.html',
            controller: 'HomeController'
        })
        .state('login', {
            url: '/login',
            templateUrl: 'html/login.html',
            controller: 'LoginController'
        })
        .state('read', {
            url: '/read',
            templateUrl: 'html/read.html',
            controller: 'ReadController'
        })
        .state('create-cx', {
            url: '/create-cx',
            abstract: true,
            templateUrl: 'html/create/create-cx/create-cx.html',
            controller: 'CreateController',
        })
        .state('create-cx.section1', {
            url: '',
            templateUrl: 'html/create/create-cx/section1.html',
            controller: 'CreateController'
        })
        .state('create-cx.section2', {
            url: '/section2',
            templateUrl: 'html/create/create-cx/section2.html',
            controller: 'CreateController'
        })
        .state('create-cx.section3', {
            url: '/section3',
            templateUrl: 'html/create/create-cx/section3.html',
            controller: 'CreateController'
        })
        .state('create-tx', {
            url: '/create-tx',
            abstract: true,
            templateUrl: 'html/create/create-tx/create-tx.html',
            controller: 'CreateController'
        })
        .state('create-tx.section1', {
            url: '',
            templateUrl: 'html/create/create-tx/section1.html',
            controller: 'CreateController'
        })
        .state('create-tx.section2', {
            url: '/section2',
            templateUrl: 'html/create/create-tx/section2.html',
            controller: 'CreateController'
        })
        .state('create-tx.section3', {
            url: '/section3',
            templateUrl: 'html/create/create-tx/section3.html',
            controller: 'CreateController'
        })
        .state('create-tx.section4', {
            url: '/section4',
            templateUrl: 'html/create/create-tx/section4.html',
            controller: 'CreateController'
        })
        .state('create-tx.section5', {
            url: '/section5',
            templateUrl: 'html/create/create-tx/section5.html',
            controller: 'CreateController'
        })
        .state('create-tx.section6', {
            url: '/section6',
            templateUrl: 'html/create/create-tx/section6.html',
            controller: 'CreateController'
        })
        .state('create-tx.section7', {
            url: '/section7',
            templateUrl: 'html/create/create-tx/section7.html',
            controller: 'CreateController'
        });
}]);

app.service('UserService', function () {
    var authenticated = false;
    var admin = false;

    return {
        getAuthenticated: function() {
            return authenticated;
        },
        setAuthenticated: function() {
            authenticated = true;
        },
        clearAuthenticated: function() {
            authenticated = false;
        },
        getAdmin: function () {
            return admin;
        },
        setAdmin: function () {
            admin = true;
        },
        clearAdmin: function () {
            admin = false;
        }
    };
});

app.controller('AppController', ['$rootScope', '$http', '$scope', '$state', 'UserService',
function($rootScope, $http, $scope, $state, UserService) {
    $scope.logout = function() {
        $http.defaults.headers.common.Authorization = '';
        delete $rootScope.currentUser;
        UserService.clearAuthenticated();
        UserService.clearAdmin();
        $state.go('login');
    }
}]);

app.run(['$rootScope', 'UserService',
function run($rootScope, UserService) {
    $rootScope.UserService = UserService;
}]);
