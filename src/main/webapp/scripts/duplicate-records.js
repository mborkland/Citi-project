'use strict';
var app = angular.module('duplicate-records', ['ui.grid', 'ui.grid', 'ui.grid.resizeColumns', 'ui.grid.pagination',
    'ui.bootstrap', 'shared.data']);

app.controller('AppController', ['$window', '$scope', 'uiGridConstants', '$interval', '$uibModal', 'tableService',
function($window, $scope, uiGridConstants, $interval, $uibModal, tableService) {
    $scope.recordType = $window.opener.parentScope.recordType;
    $scope.rowData = $window.opener.parentScope.duplicateRows;
    var historyCellTemplate = '<div align="center"><a ng-click="grid.appScope.showHistoryModal(row.entity.id)"><img src="../../images/history-img.png" height="34" width="34"></a></div>';
    var cxColumnDefs = tableService.cxColumnDefs;
    cxColumnDefs[cxColumnDefs.length - 2].cellTemplate = historyCellTemplate;
    var txColumnDefs = tableService.txColumnDefs;
    txColumnDefs[txColumnDefs.length - 2].cellTemplate = historyCellTemplate;

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
        columnDefs: cxColumnDefs,
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
        showGridFooter: true,
        columnDefs: txColumnDefs,
        onRegisterApi: function (gridApi) {
            $scope.grid2Api = gridApi;
        },
        data: $scope.rowData
    };

    $scope.forceCreate = function() {
        $window.opener.parentScope.forceCreate();
        $window.close();
    };

    $scope.cancel = function () {
        $window.close();
    };
}]);