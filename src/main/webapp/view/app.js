var app = angular.module('app',[]);

app.controller('RecordCRUDCtrl', ['$scope', 'RecordCRUDService', function ($scope, UserCRUDService) {

    $scope.createRecord = function () {

    };
    
    $scope.updateRecord = function () {
    	
    };

}]);

app.service('RecordCRUDService', ['$http', function($http) {

    this.createRecord = function createRecord(type, fields, author) {
        return $http({
            method: 'POST',
            url: 'create',
            data: {type:type, fields:fields, author:author}
        });
    };
    
    this.updateRecord = function updateRecord(type, fields, author) {
    	return $http({
    		method: 'PATCH',
    		url: 'update',
    		data: {type:type, fields:fields, author:author}
    	});
    };

}]);