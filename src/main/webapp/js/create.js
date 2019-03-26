app.controller('CreateController', ['$scope', '$http', '$state', function($scope, $http, $state) {
    $scope.cxData = $scope.cxData || {
        fields: [],
        requestor: ''
    }
    $scope.txData = $scope.txData || {
        fields: [],
        requestor: ''
    }

    $scope.createCx = function() {
        var url = '/create?';
        angular.forEach($scope.cxData.fields, function(value, key) {
            url += 'fields=' + value + '&';
        });
        url += 'recordType=CUSTOMER&';
        url += 'requestor=' + $scope.cxData.requestor;
        $http.post(url).then(function (response) {
            console.log(response);
            alert('Customer screening record created successfully');
            $state.go('read');
        }, function (error) {
            console.log(error);
            alert('There was an error');
            $state.go('read');
        });
    }

    $scope.createTx = function() {
        var url = '/create?';
        angular.forEach($scope.txData.fields, function(value, key) {
            url += 'fields=' + value + '&';
        });
        url += 'recordType=TRANSACTION&';
        url += 'requestor=' + $scope.txData.requestor;
        $http.post(url).then(function (response) {
            console.log(response);
            alert('Transaction screening record created successfully');
            $state.go('read');
        }, function (error) {
            console.log(error);
            alert('There was an error');
            $state.go('read');
        });
    }
}]);