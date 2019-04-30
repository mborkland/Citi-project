app.controller('LoginController', ['$scope', '$http', '$state', '$cookies',
function($scope, $http, $state, $cookies) {
    if ($cookies.get('token')) {
        $state.go('read');
    }

    if ($cookies.get('username')) {
        $scope.username = $cookies.get('username');
        $scope.rememberMe = true;
    }

    $scope.login = function() {
        $scope.rememberMe ? saveUsername($scope.username) : clearUsername();
        var url = '/user/login?username=' + $scope.username + '&password=' + $scope.password;
        $http.post(url).then(function (response) {
            if (response.data.token) {
                var token = response.data.token;
                $http.defaults.headers.common.Authorization = 'Bearer ' + token;
                $cookies.put('token', token);
                $cookies.put('isUser', 'true');
                return $http.get('/user/current');
            }
        }, function (error) {
            console.log(error);
            alert('Username and/or password is incorrect');
        }).then(function (response) {
            if (response) {
                var user = response.data;
                $cookies.put('currentUser', JSON.stringify(user));
                angular.forEach(user.roles, function (value, key) {
                    if (value === 'ROLE_ADMIN') {
                        $cookies.put('isAdmin', 'true');
                    }
                });

                $state.go('read');
            }
        }, function (error) {
            console.log(error);
        });
    };

    function saveUsername(username) {
        $cookies.put('username', username);
        $scope.rememberMe = true;
    }

    function clearUsername() {
        $cookies.remove('username');
        $scope.rememberMe = false;
    }
}]);