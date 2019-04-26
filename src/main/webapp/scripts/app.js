'use strict';

var app = angular.module('app', ['ui.router', 'ngAnimate', 'ngTouch', 'ngCookies', 'ui.grid',
    'ui.grid.resizeColumns', 'ui.grid.pagination', 'ui.grid.selection', 'ui.grid.exporter', 'ui.bootstrap', 'ui.grid.cellNav',
    'ngPatternRestrict', 'ngMessages', 'shared.data']);

app.config(['$stateProvider', '$urlRouterProvider',
function ($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('/');

    $stateProvider
        .state('login', {
            url: '/',
            templateUrl: 'views/login.html',
            controller: 'LoginController'
        })
        .state('read', {
            url: '/read',
            templateUrl: 'views/read.html',
            controller: 'ReadController'
        })
        .state('create-cx', {
            url: '/create-cx',
            abstract: true,
            templateUrl: 'views/create-forms/create-cx/create-cx.html',
            controller: 'CreateController'
        })
        .state('create-cx.section1', {
            url: '',
            templateUrl: 'views/create-forms/create-cx/section1.html',
            controller: 'CreateController'
        })
        .state('create-cx.section2', {
            url: '/section2',
            templateUrl: 'views/create-forms/create-cx/section2.html',
            controller: 'CreateController'
        })
        .state('create-cx.section3', {
            url: '/section3',
            templateUrl: 'views/create-forms/create-cx/section3.html',
            controller: 'CreateController'
        })
        .state('create-tx', {
            url: '/create-tx',
            abstract: true,
            templateUrl: 'views/create-forms/create-tx/create-tx.html',
            controller: 'CreateController'
        })
        .state('create-tx.section1', {
            url: '',
            templateUrl: 'views/create-forms/create-tx/section1.html',
            controller: 'CreateController'
        })
        .state('create-tx.section2', {
            url: '/section2',
            templateUrl: 'views/create-forms/create-tx/section2.html',
            controller: 'CreateController'
        })
        .state('create-tx.section3', {
            url: '/section3',
            templateUrl: 'views/create-forms/create-tx/section3.html',
            controller: 'CreateController'
        })
        .state('create-tx.section4', {
            url: '/section4',
            templateUrl: 'views/create-forms/create-tx/section4.html',
            controller: 'CreateController'
        })
        .state('create-tx.section5', {
            url: '/section5',
            templateUrl: 'views/create-forms/create-tx/section5.html',
            controller: 'CreateController'
        })
        .state('create-tx.section6', {
            url: '/section6',
            templateUrl: 'views/create-forms/create-tx/section6.html',
            controller: 'CreateController'
        })
        .state('create-tx.section7', {
            url: '/section7',
            templateUrl: 'views/create-forms/create-tx/section7.html',
            controller: 'CreateController'
        })
        .state('archive', {
            url: '/archive',
            templateUrl: 'views/archive.html',
            controller: 'ArchiveController'
        })
        .state('creation-summary', {
            url: '/creation-summary',
            templateUrl: 'views/creation-summary.html',
            controller: 'CreationSummaryController',
            params: {
                recordType: '',
                soeid: '',
                data: {}
            }
        });
}]);

app.controller('AppController', ['$rootScope', '$scope', '$cookies', '$http', '$state',
function ($rootScope, $scope, $cookies, $http, $state) {
    $scope.isUser = $rootScope.isUser;
    $scope.isAdmin = $rootScope.isAdmin;

    $scope.logout = function() {
        $http.defaults.headers.common.Authorization = '';
        $cookies.remove('currentUser');
        $cookies.remove('token');
        $cookies.put('isUser', 'false');
        $cookies.put('isAdmin', 'false');
        $state.go('login');
    }
}]);

app.run(['$rootScope', '$state', '$cookies', '$http', function ($rootScope, $state, $cookies, $http) {
    $rootScope.isUser = function() {
        return $cookies.get('isUser') === 'true';
    };

    $rootScope.isAdmin = function() {
        return $cookies.get('isAdmin') === 'true';
    };

    if ($cookies.get('token')) {
        $http.defaults.headers.common.Authorization = 'Bearer ' + $cookies.get('token');
        $state.go('read');
    } else {
        $state.go('login');
    }
}]);