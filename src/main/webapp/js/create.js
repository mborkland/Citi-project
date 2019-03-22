app.controller('CreateController', ['$scope', '$http', function($scope, $http) {
    $scope.fields = [];
    $scope.createCx = function() {
        var url = '/create?';
        angular.forEach($scope.fields, function(value, key) {
            url += 'fields=' + value + '&';
        });
        url += 'recordType=CUSTOMER&';
        url += 'requestor=' + $scope.requestor;
        $http.post(url).then(function (response) {
            console.log(response);
        }, function (error) {
            console.log(error);
        });
    }
}]);