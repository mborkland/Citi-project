app.controller('LoginController', ['$rootScope', '$scope', '$http', '$state', 'TokenStore', 'UserService',
function($rootScope, $scope, $http, $state, TokenStore, UserService) {
    if ($rootScope.currentUser) {
        $state.go('home');
    }

    $scope.login = function() {
        $http({
            url: '/user/login',
            method: 'POST',
            data: {'username': $scope.username, 'password': $scope.password}
        }).then(function (response) {
            var authToken = response.headers()['x-auth-token'];
            if (authToken) {
                UserService.setAuthenticated();
                TokenStore.save(authToken);
                return $http.get('/user/current');
            }
        }).then(function (response) {
            var user = response.data;
            $rootScope.currentUser = user;
            angular.forEach(user.authorities, function (value, key) {
                if (value.authority === 'ROLE_ADMIN') {
                    UserService.setAdmin();
                }
            });

            $state.go('home');
        });
    }
}]);