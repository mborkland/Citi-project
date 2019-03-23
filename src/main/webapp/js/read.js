app.controller('ReadController', ['$scope', '$http', function ($scope, $http) {
    var url = '/read?recordType=';
    $scope.search = function() {
        $http.get(url + $scope.recordType + '&searchTerms=' + $scope.searchTerms).then(function (response) {
            console.log(response);
        }, function (error) {
            console.log(error);
        });
    }
}]);