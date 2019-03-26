app.controller('LoginController', ['$rootScope', '$scope', '$http', '$state', 'UserService',
function($rootScope, $scope, $http, $state, UserService) {
    if ($rootScope.currentUser) {
        $state.go('home');
    }

    $scope.login = function() {
        var url = '/user/login?username=' + $scope.username + '&password=' + $scope.password;
        $http.post(url).then(function (response) {
            if (response.data.token) {
                var token = response.data.token;
                UserService.setAuthenticated();
                $http.defaults.headers.common.Authorization = 'Bearer ' + token;
                return $http.get('/user/current');
            } else {
                alert('Username or password is incorrect');
            }
        }).then(function (response) {
            var user = response.data;
            $rootScope.currentUser = user;
            angular.forEach(user.roles, function (value, key) {
                if (value === 'ROLE_ADMIN') {
                    UserService.setAdmin();
                }
            });

            $state.go('home');
        });
    }
}]);