'use strict';
var app = angular.module('update', ['ui.grid', 'ui.grid', 'ui.grid.resizeColumns', 'ui.grid.exporter',
    'ui.grid.edit', 'ui.grid.rowEdit', 'ui.grid.pagination', 'ui.bootstrap', 'shared.data']);

app.controller('AppController', ['$window', '$scope', 'uiGridConstants', '$interval', '$uibModal', 'tableService',
function($window, $scope, uiGridConstants, $interval, $uibModal, tableService) {
    $scope.recordType = $window.opener.parentScope.recordType;
    $scope.rowData = $window.opener.parentScope.getSelectedRowData();
    $scope.numberOfEditedRecords = 0;
    var updatedRows = [];

    function trackColumnUpdate(rowEntity, colDef, newValue, oldValue) {
        for (var i = 0; i < updatedRows.length; ++i) {
            if (updatedRows[i].id === rowEntity.id) {
                for (var j = 0; j < updatedRows[i].updatedFields.length; ++j) {
                    if (updatedRows[i].updatedFields[j].field === colDef.field) {
                        updatedRows[i].updatedFields[j].newValue = newValue;
                        return;
                    }
                }

                updatedRows[i].updatedFields.push({
                    field: colDef.field,
                    oldValue: oldValue,
                    newValue: newValue
                });

                return;
            }
        }

        updatedRows.push({
            id: rowEntity.id,
            updatedFields: [{
                field: colDef.field,
                oldValue: oldValue,
                newValue: newValue
            }]
        });
    }

    function getUpdatedFields(id) {
        for (var i = 0; i < updatedRows.length; ++i) {
            if (updatedRows[i].id === id) {
                return updatedRows[i].updatedFields;
            }
        }

        return null;
    }

    function convertTimestampToDate(timestamp) {
        var date = new Date(timestamp);
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var day = date.getDate();
        var hours = ('0' + date.getHours()).substr(-2);
        var minutes = ('0' + date.getMinutes()).substr(-2);
        var seconds = ('0' + date.getSeconds()).substr(-2);
        return year + '-' + month + '-' + day + ' ' + hours + ':' + minutes + ':' + seconds;
    }

    function aggregateUpdateData(dirtyRows) {
        var updatedRecords = [];
        angular.forEach(dirtyRows, function(value, key) {
            value.entity.creationDate = convertTimestampToDate(value.entity.creationDate);
            updatedRecords.push({
                entity: value.entity,
                updatedFields: getUpdatedFields(value.entity.id)
            });
        });
        $scope.numberOfEditedRecords = updatedRecords.length;
        return updatedRecords;
    }

    $scope.showHistoryModal = function(id) {
        var history = tableService.getHistory($scope.rowData, id);
        var historyModalInstance = $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '../modals/history.html',
            controller: function($uibModalInstance, $scope, modalData) {
                $scope.historyData = modalData.historyData;

                $scope.ok = function () {
                    $uibModalInstance.close();
                };
            },
            size: 'md',
            resolve: {
                modalData: {
                    historyData: history
                }
            }
        });
    };

    $scope.showUpdateConfimationModal = function() {
        var updateConfirmationModalInstance = $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: '../modals/update-confirmation.html',
            controller: function($uibModalInstance, $scope, modalData) {
                var num = modalData.numUpdatedRecords;

                $scope.successfulUpdateMessage = function () {
                    if (num === 1) {
                        return String(num) + " record has been edited.";
                    }
                    else {
                        return String(num) + " records have been edited.";
                    }
                };

                $scope.ok = function () {
                    $uibModalInstance.close();
                };
            },
            size: 'md',
            resolve: {
                modalData: {
                    numUpdatedRecords: $scope.numberOfEditedRecords
                }
            }
        });

        updateConfirmationModalInstance.result.then(
            function () {
                $window.close();
            },
            function () {
                $window.close();
            }
        );
    };

    var historyCellTemplate = '<div align="center"><a ng-click="grid.appScope.showHistoryModal(row.entity.id)"><img src="../../images/history-img.png" height="34" width="34"></a></div>';

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
        rowEditWaitInterval: -1,
        onRegisterApi: function (gridApi) {
            $scope.grid1Api = gridApi;
            gridApi.edit.on.afterCellEdit($scope, function(rowEntity, colDef, newValue, oldValue) {
                trackColumnUpdate(rowEntity, colDef, newValue, oldValue);
            });
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
        showGridFooter: true,
        rowEditWaitInterval: -1,
        onRegisterApi: function (gridApi) {
            $scope.grid2Api = gridApi;
            gridApi.edit.on.afterCellEdit($scope, function(rowEntity, colDef, newValue, oldValue) {
                trackColumnUpdate(rowEntity, colDef, newValue, oldValue);
            });
        },
        data: $scope.rowData
    };

    $scope.gridOptions1.columnDefs = $window.opener.parentScope.gridOptions1.columnDefs;
    $scope.gridOptions2.columnDefs = $window.opener.parentScope.gridOptions2.columnDefs;
    var uneditableFields = ['id', 'region', 'gomCompliant', 'workflowFlag', 'anyBatchComponent', 'history'];

    function customizeColumnDefs(columnDefs) {
        angular.forEach(columnDefs, function (value, key) {
            if (uneditableFields.includes(value.field)) {
                value.enableCellEdit = false;
                if (value.field === 'history') {
                    value.cellTemplate = historyCellTemplate;
                }
            } else {
                value.enableCellEdit = true;
            }
        });
    }

    customizeColumnDefs($scope.gridOptions1.columnDefs);
    customizeColumnDefs($scope.gridOptions2.columnDefs);

    $scope.cancel = function () {
        $window.close();
    };

    $scope.makeChanges = function () {
        var gridApi = $scope.recordType === 'CUSTOMER' ? $scope.grid1Api : $scope.grid2Api;
        var SOEID = angular.copy($scope.SOEID);
        var reason = angular.copy($scope.reason);
        $window.opener.parentScope.updateRecords(aggregateUpdateData(gridApi.rowEdit.getDirtyRows(gridApi.grid)), SOEID, reason);
        $scope.showUpdateConfimationModal();
    };

    $scope.checkSOEID = function () {
        return !($scope.SOEID);
    };

    $scope.checkReason = function () {
        return !($scope.reason);
    };

    $scope.validate = function () {
        return (($scope.checkReason()) || ($scope.checkSOEID()));
    };
}]);