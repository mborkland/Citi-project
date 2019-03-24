app.controller('CreateController', ['$scope', '$http', 'NUM_CUSTOMER_FIELDS', 'NUM_TRANSACTION_FIELDS',
function($scope, $http, NUM_CUSTOMER_FIELDS, NUM_TRANSACTION_FIELDS) {
    $scope.cxFields = new Array(NUM_CUSTOMER_FIELDS);
    $scope.txFields = new Array(NUM_TRANSACTION_FIELDS);

    $scope.createCx = function() {
        //uncomment below to create a record just by clicking submit button (comment out the rest before http.post call)
        //var url = '/create?fields=155093&fields=AML-NS%20BytyScreening%20NAM&fields=GTS_USCARDS&fields=usgtb&fields=GTSUSWEEKLYTS2&fields=usgSTT2wp&fields=GTS%20US%20COMMERCIAL&fields=24%2F7&fields=ISP%20NAM%20US%20GTS%20COMM%20CARD%20WEEKLY&fields=NAM&fields=USA&fields=ISP&fields=Y&fields=Cw%20NAM&fields=GTS%20US%20COMM%20WKLY%20TS&fields=24%2F7&fields=NDM&fields=34897&fields=Commercial%20Cards%20File%20Delivery&fields=Business%20Ops&fields=Borkland%2C%20Michael&fields=V2&fields=Y&fields=Khalil%2C%20Christopher&fields=Bhat%2C%20Sanjeeth&fields=Business%20cannot%20open%20accounts%20until%20file%20has%20been%20screened.&fields=Winkler-Malcom%2C%20Ethan%2C&fields=NAM&recordType=CUSTOMER&requestor=mborkland%40mail.usf.edu';
        var url = '/create?';
        angular.forEach($scope.cxFields, function(value, key) {
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

    $scope.createTx = function() {
        var url = '/create?';
        angular.forEach($scope.txFields, function(value, key) {
            url += 'fields=' + value + '&';
        });
        url += 'recordType=TRANSACTION&';
        url += 'requestor=' + $scope.requestor;
        $http.post(url).then(function (response) {
            console.log(response);
        }, function (error) {
            console.log(error);
        });
    }
}]);