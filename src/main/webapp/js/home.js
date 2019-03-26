app.controller('HomeController', ['$rootScope', '$state', function ($rootScope, $state) {
    if ($rootScope.currentUser === undefined) {
        $state.go('login');
    } else {
        $state.go('read');
    }
}]);