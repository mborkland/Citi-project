'use strict';

var app = angular.module('app', ['ui.router']);

app.factory('TokenStore', function ($window) {
    var storageKey = 'auth_token';
    return {
        save: function (token) {
            return $window.localStorage.setItem(storageKey, token);
        },
        get: function () {
            return $window.localStorage.getItem(storageKey);
        },
        clear: function () {
            return $window.localStorage.removeItem(storageKey);
        }
    };
});

app.factory('authInterceptor', function ($rootScope, $q, TokenStore) {
    return {
        request: function (config) {
            config.headers = config.headers || {};
            if (TokenStore.get()) {
                config.headers['X-Auth-Token'] = TokenStore.get();
            }
            return config;
        },
        response: function (response) {
            if (response.status === 401) {
                // handle the case where the user is not authenticated
            }
            return response || $q.when(response);
        }
    };
});

app.config(['$stateProvider', '$urlRouterProvider', '$httpProvider', function ($stateProvider, $urlRouterProvider, $httpProvider) {

    $httpProvider.interceptors.push('authInterceptor');
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

app.controller('AppController', ['$rootScope', '$scope', '$state', 'TokenStore', 'UserService',
function($rootScope, $scope, $state, TokenStore, UserService) {
    $scope.logout = function () {
        TokenStore.clear();
        UserService.clearAuthenticated();
        UserService.clearAdmin();
        delete $rootScope.currentUser;
        $state.go('login');
    }
}]);

app.run(['$rootScope', '$location', '$http', '$window', 'UserService',
function run($rootScope, $location, $http, $window, UserService) {
    $rootScope.UserService = UserService;
}]);
