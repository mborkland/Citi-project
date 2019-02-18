var app = angular.module('app',[]);

app.controller('RecordCRUDCtrl', ['$scope', 'RecordCRUDService', function ($scope, UserCRUDService) {

    $scope.createRecord = function () {

    }

    $scope.deleteRecord = function () {

    };

    $scope.updateRecord = function () {

    };

}]);

app.service('RecordCRUDService', ['$http', function($http) {

    this.createRecord = function createRecord(type, fields, requestor) {
        return $http({
            method: 'POST',
            url: 'create',
            data: {type:type, fields:fields, requestor:requestor}
        });
    }

    this.deleteRecord = function deleteRecord(type, fields, requestor) {
        return $http({
            method: 'DELETE',
            url: 'delete',
            data: {type:type, fields:fields, requestor:requestor}
        });
    };

    this.updateRecord = function updateRecord(type, fields, requestor) {
        return $http({
            method: 'PATCH',
            url: 'update',
            data: {type:type, fields:fields, requestor:requestor}
        });
    };

}]);