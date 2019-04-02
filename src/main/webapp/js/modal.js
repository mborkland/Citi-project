app.controller('ModalController', ['$scope', '$bootstrap4Modal', function($scope, $bootstrap4Modal) {
    $scope.hide = function () {
        $bootstrap4Modal.hide();
    }
}]);