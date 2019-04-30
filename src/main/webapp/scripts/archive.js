app.controller('ArchiveController', ['$rootScope', '$scope', '$http', '$timeout', 'uiGridConstants', '$uibModal', 'tableService', '$window',
function ($rootScope, $scope, $http, $timeout, uiGridConstants, $uibModal, tableService, $window) {
    $scope.isUser = $rootScope.isUser;
    $scope.isAdmin = $rootScope.isAdmin;
    $scope.recordType = 'CUSTOMER';
    $scope.rowsSelected = [];
    var deletionDetailsCellTemplate = '<div align="center" style="margin-top: 3px;"><a ng-click="grid.appScope.showDeletionDetailsModal(row.entity.id)"><img src="images/deletion-details-img.png" height="28" width="26"></a></div>';

    $scope.rowData = [];
    populateWithArchiveRows();

    $scope.$watch('recordType', function(newValue, oldValue) {
        populateWithArchiveRows();
    });

    var deletionDetailsField = {
        field: 'deletionDetails', displayName: 'Deletion Details', width: tableService.smWidth, enableHiding: false,
        cellTemplate: deletionDetailsCellTemplate
    };

    var cxColumnDefs = tableService.cxColumnDefs.concat([deletionDetailsField]);

    $scope.gridOptions1 = {
        enableHorizontalScrollbar: uiGridConstants.scrollbars.WHEN_NEEDED,
        enableVerticalScrollbar: uiGridConstants.scrollbars.WHEN_NEEDED,
        enableColumnResizing: true,
        enableSorting: true,
        enableFullRowSelection: false,
        selectionRowHeaderWidth: 35,
        rowHeight: 35,
        enablePaginationControls: false,
        paginationPageSize: 15,
        showGridFooter:true,
        enableSelectAll: true,
        columnDefs: cxColumnDefs,
        onRegisterApi: function (gridApi) {
            $scope.grid1Api = gridApi;
        },
        data: $scope.rowData
    };

    var txColumnDefs = tableService.txColumnDefs.concat([deletionDetailsField]);

    $scope.gridOptions2 = {
        enableHorizontalScrollbar: uiGridConstants.scrollbars.WHEN_NEEDED,
        enableVerticalScrollbar: uiGridConstants.scrollbars.WHEN_NEEDED,
        enableColumnResizing: true,
        enableSorting: true,
        enableFullRowSelection: true,
        selectionRowHeaderWidth: 35,
        rowHeight: 35,
        enablePaginationControls: false,
        paginationPageSize: 15,
        showGridFooter:true,
        enableSelectAll: true,
        columnDefs: txColumnDefs,
        onRegisterApi: function (gridApi) {
            $scope.grid2Api = gridApi;
        },
        data: $scope.rowData
    };

    $scope.areRowsSelected = function() {
        return ($scope.recordType === 'CUSTOMER' ? $scope.grid1Api.grid.selection.selectedCount : $scope.grid2Api.grid.selection.selectedCount) > 0;
    };

    function getDeletionDetails(id) {
        var deletionDetails = null;
        angular.forEach($scope.rowData, function(value, key) {
            if (value.id === id) {
                deletionDetails = value.deletionDetails;
            }
        });

        return deletionDetails;
    }

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

    $scope.showDeletionDetailsModal = function(id) {
        var deletionDetails = getDeletionDetails(id);
        var deletionDetailsModalInstance = $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: 'views/modals/deletion-details.html',
            controller: 'ModalController',
            size: 'md',
            resolve: {
                modalData: {
                    deletionDetailsData: deletionDetails
                }
            }
        });
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
           timedRefresh(3000);
        });
    };

    $scope.showClearModal = function() {
        var selectedRowData = $scope.getSelectedRowData();
        var clearModalInstance = $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: 'views/modals/clear-confirmation.html',
            controller: 'ModalController',
            size: 'md',
            resolve: {
                modalData: {
                    clearData: selectedRowData,
                    recordType: $scope.recordType
                }
            }
        });

        clearModalInstance.result.then(function() {
            timedRefresh(3000);
        });
    };

    $scope.showRestoreModal = function() {
        var selectedRowData = $scope.getSelectedRowData();
        var restoreModalInstance = $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: 'views/modals/restore-confirmation.html',
            controller: 'ModalController',
            size: 'md',
            resolve: {
                modalData: {
                    restoreData: selectedRowData,
                    recordType: $scope.recordType
                }
            }
        });

        restoreModalInstance.result.then(function() {
            timedRefresh(3000);
        });
    };

    function timedRefresh(timeoutPeriod) {
        $timeout(function() {
            $window.location.reload(true);
        }, timeoutPeriod);
    }

    $scope.or = true;
    $scope.exactMatch = false;

    $scope.search = function () {
        var url = '/read-archive?recordType=';
        if ($scope.searchTerms) {
            $http.get(url + $scope.recordType + '&searchTerms=' + $scope.searchTerms + '&exactMatch=' + $scope.exactMatch
                + '&or=' + $scope.or).then(function (response) {
                handleResponse(response);
            }, function (error) {
                console.log(error);
            });
        } else {
            populateWithArchiveRows();
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

    function handleResponse(response) {
        $scope.rowData.length = 0;
        angular.forEach(response.data, function (value, key) {
            $scope.rowData.push(convertResponseData(value, $scope.recordType));
        });
    }

    function populateWithArchiveRows() {
        var url = '/archive?recordType=';
        $http.get(url + $scope.recordType).then(function (response) {
            handleResponse(response);
        }, function (error) {
            console.log(error);
        });
    }

    function convertResponseData(record, recordType) {
        var data = recordType === 'CUSTOMER' ? tableService.convertCxData(record, false) : tableService.convertTxData(record, false);
        data.deletionDetails = record.deletionDetails;
        return data;
    }
}]);