app.controller('ModalController', ['$scope', '$http', '$bootstrap4Modal', function($scope, $http, $bootstrap4Modal) {
    $scope.hide = function () {
        $bootstrap4Modal.hide();
    };

    $scope.checkSOEID = function () {
        return !($scope.SOEID);
    };

    $scope.delete = function () {
        var url2 = '&recordType=';
        var url3 = '&requestor=';
        var deletes = $scope.modalData.dataFromParent;
        console.log("These are the records to be deleted:");
        console.log(deletes);
        var url1 = '/delete?';
        for (var i = 0; i < deletes.length; i++) {
            url1 = url1 + '&ids=' + deletes[i].id;
        }
        $http.delete(url1 + url2 + $scope.modalData.recordTypeDelete + url3 + $scope.SOEID).then (function (response) {
            console.log(response);
        }, function (error) {
            console.log(error);
        });
        $scope.hide();
    };
}]);