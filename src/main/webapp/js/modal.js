app.controller('ModalController', ['$scope', '$http', '$uibModalInstance', 'modalData',
function($scope, $http, $uibModalInstance, modalData) {
    $scope.updateHistoryData = modalData.updateHistoryData;
    $scope.deletionDetailsData = modalData.deletionDetailsData;
    var deleteData = modalData.deleteData;
    var restoreData = modalData.restoreData;
    var clearData = modalData.clearData;

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

    $scope.clear = function() {
        var url = '/clear?recordType=' + modalData.recordType;
        angular.forEach(clearData, function(value, key) {
           url += ('&ids=' + value.id);
        });
        $http.delete(url).then (function (response) {
            console.log(response);
        }, function (error) {
            console.log(error);
        });
        $scope.ok();
    };

    $scope.restore = function() {
        var url = '/restore?recordType=' + modalData.recordType + '&requestor=' + $scope.SOEID;
        angular.forEach(restoreData, function(value, key) {
            url += ('&ids=' + value.id);
        });
        $http.patch(url).then (function (response) {
            console.log(response);
        }, function (error) {
            console.log(error);
        });
        $scope.ok();
    };
}]);