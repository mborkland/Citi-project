app.controller('ReadController', ['$scope', '$http', function ($scope, $http) {
    $scope.search = function() {
        var url = '/read?recordType=';
        $http.get(url + $scope.recordType + '&searchTerms=' + $scope.searchTerms).then(function (response) {
            console.log(response);
        }, function (error) {
            console.log(error);
        });
    }

    function genRows(num){
        var retVal = [];
        for (var i=0; i < num; i++) {
            retVal.push(genRow(i));
        }
        return retVal;
    }

    // Generates a row with random data
    function genRow(id){

        var fnames = ['joe','fred','frank','jim','mike','gary','aziz'];
        var lnames = ['sterling','smith','erickson','burke','ansari'];
        var seed = Math.random();
        var seed2 = Math.random();
        var first_name = fnames[ Math.round( seed * (fnames.length -1) ) ];
        var last_name = lnames[ Math.round( seed * (lnames.length -1) ) ];

        return {
            id: id,
            selected: false,
            first_name: first_name,
            last_name: last_name,
            age: Math.ceil(seed * 75) + 15,
            height: Math.round( seed2 * 36 ) + 48,
            weight: Math.round( seed2 * 130 ) + 90,
            likes: Math.round(seed2 * seed * 1000000)
        };
    }

    $scope.my_table_columns = [
        { id: 'selected', key: 'id', label: '', width: 30, lockWidth: true, selector: true },
        //{ id: 'selected', key: 'id', label: '', width: 30, lockWidth: true, selector: true, selectObject: true },
        { id: 'ID', key: 'id', label: 'id', sort: 'number', filter: 'number' },
        { id: 'first_name', key: 'first_name', label: 'First Name', sort: 'string', filter: 'like', template: '<strong>{{row[column.key]}}</strong>' },
        { id: 'last_name', key: 'last_name', label: 'Last Name', sort: 'string', filter: 'like' },
        { id: 'age', key: 'age', label: 'Age', sort: 'number', filter: 'number' },
        { id: 'height', key: 'height', label: 'Height', filter: 'number', sort: 'number' },
    ];

    // Table data
    $scope.my_table_data = genRows(100);


    // Selected rows
    $scope.my_selected_rows = [];

    // table options
    $scope.my_table_options = {
        rowLimit: 10
    };
    $scope.my_table_options_paginate = angular.extend({}, $scope.my_table_options, {
        pagingStrategy: 'PAGINATE',
        rowsPerPage: 8
    });
}]);