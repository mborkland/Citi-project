(function () {
    'use strict';

    angular
        .module('app', ['ngRoute'])
        .config(config)
        .service('userService', UserService)
        .controller('AppController', AppController)
        .run(run);

    config.$inject = ['$routeProvider', '$locationProvider', '$httpProvider'];
    function config($routeProvider, $locationProvider, $httpProvider) {
        $routeProvider
            .when('/', {
                controller: 'HomeController',
                templateUrl: 'html/home.html',
                controllerAs: 'vm'
            })
            .when('/login', {
                controller: 'LoginController',
                templateUrl: 'html/login.html',
                controllerAs: 'vm'
            })
            .otherwise({ redirectTo: '/login' });

        //$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
    }

    run.$inject = ['$rootScope', '$location', '$http', '$window', 'userService'];
    function run($rootScope, $location, $http, $window, userService) {
        $rootScope.userService = userService;
        var userData = $window.sessionStorage.getItem('userData');
        if (userData) {
            $http.defaults.headers.common['Authorization'] = 'Basic ' + JSON.parse(userData).authData;
        }

        $rootScope.$on('$locationChangeStart', function (event, next, current) {
            var restrictedPage = $.inArray($location.path(), ['/login']) === -1;
            var loggedIn = $window.sessionStorage.getItem('userData');;
            if (restrictedPage && !loggedIn) {
                $location.path('/login');
            }
        });
    }

    function UserService() {
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
    }

    AppController.$inject = ['$http', '$window', '$scope', 'userService'];
    function AppController($http, $window, $scope, userService) {
        $scope.logout = function(){
            userService.clearAuthenticated();
            $window.sessionStorage.setItem('userData', '');
            $http.defaults.headers.common['Authorization'] = 'Basic';
        }
    }
})();