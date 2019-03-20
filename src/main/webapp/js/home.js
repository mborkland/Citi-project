(function () {
    'use strict';

    angular
        .module('app')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$window', '$http', '$scope', 'userService'];
    function HomeController($window, $http, $scope, userService) {
        var vm = this;

        vm.user = null;

        initController();

        function initController() {

            $http({
                url: '/user',
                method: "GET"
            }).then(function (response) {
                vm.user = response.data.name;
            },function(error){
                console.log(error);
            });
        };
    }
})();