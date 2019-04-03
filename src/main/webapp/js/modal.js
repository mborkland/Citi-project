app.controller('ModalController', ['$scope', '$http', '$uibModalInstance', 'modalData',
function($scope, $http, $uibModalInstance, modalData) {
    $scope.updateHistoryData = modalData.updateHistoryData;
    var deleteData = modalData.deleteData;

    $scope.ok = function () {
        $uibModalInstance.close();
    };

    $scope.cancel = function() {
        $uibModalInstance.dismiss();
    };

    $scope.checkSOEID = function () {
        return !($scope.SOEID);
    };

    $scope.delete = function () {
        var url = '/delete?recordType=' + modalData.recordType + '&requestor=' + $scope.SOEID;
        angular.forEach(deleteData, function(value, key) {
            url += ('&ids=' + value.id);
        });
        $http.delete(url).then (function (response) {
            console.log(response);
        }, function (error) {
            console.log(error);
        });
        $scope.ok();
    };
}]);