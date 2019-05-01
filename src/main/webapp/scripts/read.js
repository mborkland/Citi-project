app.controller('ReadController', ['$rootScope', '$scope', '$http', 'uiGridConstants', '$uibModal', '$compile', '$window', '$timeout', 'tableService',
function ($rootScope, $scope, $http, uiGridConstants, $uibModal, $compile, $window, $timeout, tableService) {
    $scope.isUser = $rootScope.isUser;
    $scope.isAdmin = $rootScope.isAdmin;
    $scope.recordType = 'CUSTOMER';
    $scope.rowsSelected = [];
    var numRecentRows = 100;
    $scope.rowData = [];
    populateWithRecentRows(numRecentRows);

    $scope.$watch('recordType', function(newValue, oldValue) {
        populateWithRecentRows(numRecentRows);
    });

    $scope.gridOptions1 = {
        enableHorizontalScrollbar: uiGridConstants.scrollbars.WHEN_NEEDED,
        enableVerticalScrollbar: uiGridConstants.scrollbars.WHEN_NEEDED,
        enableColumnResizing: true,
        enableSorting: true,
        selectionRowHeaderWidth: 35,
        rowHeight: 35,
        enablePaginationControls: false,
        paginationPageSize: 15,
        showGridFooter: true,
        enableSelectAll: true,
        columnDefs: tableService.cxColumnDefs,
        onRegisterApi: function (gridApi) {
            $scope.grid1Api = gridApi;
        },
        data: $scope.rowData
    };

    $scope.gridOptions2 = {
        enableHorizontalScrollbar: uiGridConstants.scrollbars.WHEN_NEEDED,
        enableVerticalScrollbar: uiGridConstants.scrollbars.WHEN_NEEDED,
        enableColumnResizing: true,
        enableSorting: true,
        selectionRowHeaderWidth: 35,
        rowHeight: 35,
        enablePaginationControls: false,
        paginationPageSize: 15,
        showGridFooter:true,
        enableSelectAll: true,
        columnDefs: tableService.txColumnDefs,
        onRegisterApi: function (gridApi) {
            $scope.grid2Api = gridApi;
        },
        data: $scope.rowData
    };

    $scope.export = function() {
        $scope.recordType === 'CUSTOMER' ? $scope.grid1Api.exporter.csvExport('selected', 'all') : $scope.grid2Api.exporter.csvExport('selected', 'all');
    };

    $scope.areRowsSelected = function() {
        return $scope.recordType === 'CUSTOMER' ? tableService.areRowsSelected($scope.grid1Api) : tableService.areRowsSelected($scope.grid2Api);
    };

    $scope.showHistoryModal = function(id) {
        var history = tableService.getHistory($scope.rowData, id);
        var historyModalInstance = $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: 'views/modals/history.html',
            controller: 'ModalController',
            size: 'md',
            resolve: {
                modalData: {
                    historyData: history
                }
            }
        });
    };

    $scope.showUpdateWindow = function() {
        var selectedRowData = $scope.getSelectedRowData();
        $window.parentScope = $scope;
        $window.open('views/windows/update.html', 'Update Records', 'width=1000,height=600');
    };

    $scope.showDeleteModal = function() {
        var selectedRowData = $scope.getSelectedRowData();
        var deleteModalInstance = $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: 'views/modals/delete-confirmation.html',
            controller: 'ModalController',
            size: 'md',
            resolve: {
                modalData: {
                    deleteData: selectedRowData,
                    recordType: $scope.recordType
                }
            }
        });

        deleteModalInstance.result.then(function() {
            $scope.showDeleteConfirmationModal($uibModal);
        });
    };

    $scope.showDeleteConfirmationModal = function(modal) {
        var count = $scope.getSelectedRowData().length;
        var deleteConfirmationModalInstance = modal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: 'views/modals/delete-confirmation2.html',
            controller: 'ModalController',
            size: 'md',
            resolve: {
                modalData: {
                    num: count
                }
            }
        });

        deleteConfirmationModalInstance.result.then(
            function () {
                tableService.timedRefresh(3000);
            },
            function () {
                tableService.timedRefresh(3000);
            }
        );
    };

    $scope.or = true;
    $scope.exactMatch = false;

    $scope.search = function () {
        var url = '/read?recordType=';
        if ($scope.searchTerms) {
            $http.get(url + $scope.recordType + '&searchTerms=' + $scope.searchTerms + '&exactMatch=' + $scope.exactMatch
                + '&or=' + $scope.or).then(function (response) {
                handleResponse(response, false);
            }, function (error) {
                console.log(error);
            });
        } else {
            populateWithRecentRows(numRecentRows);
        }
    };

    $scope.getSelectedRowData = function () {
        var grid1 = $scope.recordType === 'CUSTOMER' ? $scope.grid1Api.grid.rows : $scope.grid2Api.grid.rows;
        var selected = [];
        for (var i = 0; i < grid1.length; i++) {
            if (grid1[i].isSelected === true) {
                selected.push(grid1[i].entity);
            }
        }
        return selected;
    };

    function handleResponse(response, isRecent) {
        $scope.rowData.length = 0;
        angular.forEach(response.data, function (value, key) {
            $scope.rowData.push(convertResponseData(value, $scope.recordType, isRecent));
        });
    }

    function populateWithRecentRows(numRecentRows) {
        var url = '/read-recent?recordType=';
        $http.get(url + $scope.recordType + '&numRecentRecords=' + numRecentRows).then(function (response) {
            handleResponse(response, true);
        }, function (error) {
            console.log(error);
        });
    }

    function convertResponseData(record, recordType, isRecent) {
        if (recordType === 'CUSTOMER') {
            return tableService.convertCxData(record, isRecent);
        } else {
            return tableService.convertTxData(record, isRecent);
        }
    }

    function jsonifyUpdatedRecord(updatedRecord, SOEID, newReason) {
        var jsonifiedRecord = {
            record: {
                id: updatedRecord.entity.id,
                creationDate: updatedRecord.entity.creationDate,
                buDetails: {}
            },
            soeid: SOEID,
            reason: newReason,
            updatedFields: updatedRecord.updatedFields
        };

        angular.forEach(updatedRecord.entity, function(value, key) {
            if (key !== '$$hashKey' && key !== 'id' && key !== 'creationDate') {
                var convertedValue = tableService.booleanFields.includes(key) ? convertCharToBoolean(value) : value;
                jsonifiedRecord.record.buDetails[key] = convertedValue;
            }
        });

        return jsonifiedRecord;
    }

    function jsonifyUpdatedRecords(updatedRecords, SOEID, newReason) {
        var jsonified = [];
        angular.forEach(updatedRecords, function (value, key) {
            jsonified.push(jsonifyUpdatedRecord(value, SOEID, newReason));
        });

        return jsonified;
    }

    $scope.updateRecords = function (updatedRecords, SOEID, newReason) {
        var url = $scope.recordType === "CUSTOMER" ? '/update-customer' : '/update-transaction';
        var data = jsonifyUpdatedRecords(updatedRecords, SOEID, newReason);
        $http({
            method: 'PATCH',
            url: url,
            data: data
        }).then (function (response) {
            tableService.timedRefresh(3000);
        }, function (error) {
            console.log(error);
        });
    };

    function convertCharToBoolean(char) {
        return !!'Y';
    }
}]);