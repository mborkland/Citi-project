var app = angular.module('app',[]);

app.controller('RecordCRUDCtrl', ['$scope', 'RecordCRUDService', function ($scope, UserCRUDService) {

    $scope.createRecord = function () {

    }

}]);

app.service('RecordCRUDService', ['$http', function($http) {

    this.createRecord = function createRecord(type, fields, author) {
        return $http({
            method: 'POST',
            url: 'create',
            data: {type:type, fields:fields, author:author}
        });
    }

}]);