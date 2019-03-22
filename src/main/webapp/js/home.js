app.controller('HomeController', ['$rootScope', '$state', '$http', 'TokenStore',
function ($rootScope, $state, $http, TokenStore) {
    if ($rootScope.currentUser === undefined) {
        if (TokenStore.get()) {
            $http({
                url: '/user/current',
                method: 'GET'
            }).then(function (response) {
                $rootScope.currentUser = response.data;
            }, function(error) {
                console.log(error);
            });
        } else {
            $state.go('login');
        }
    } else {
        $state.go('read');
    }
}]);