app.controller('ArchiveController', ['$rootScope', '$scope', '$http', 'uiGridConstants', '$uibModal', '$compile', '$window',
function ($rootScope, $scope, $http, uiGridConstants, $uibModal, $compile, $window) {
    $scope.isUser = $rootScope.isUser;
    $scope.isAdmin = $rootScope.isAdmin;

    var xsw = 90;
    var xxsw = xsw / 2;
    var sw = 2 * xsw;
    var mw = 3 * xsw;
    var lw = 4 * xsw;
    var xlw = 4.5 * xsw;

    $scope.recordType = 'CUSTOMER';
    $scope.rowsSelected = [];

    //var numRandomRows = 100;
    $scope.rowData = [];
    //populateWithRandomRows(numRandomRows);
    populateWithArchiveRows();

    $scope.$watch('recordType', function(newValue, oldValue) {
        //populateWithRandomRows(numRandomRows);
        populateWithArchiveRows();
    });

    $scope.gridOptions1 = {
        enableHorizontalScrollbar: uiGridConstants.scrollbars.WHEN_NEEDED,
        enableVerticalScrollbar: uiGridConstants.scrollbars.WHEN_NEEDED,
        enableColumnResizing: true,
        enableSorting: true,
        enableFullRowSelection: true,
        selectionRowHeaderWidth: 35,
        rowHeight: 35,
        //paginationPageSizes: [10, 20, 50],
        enablePaginationControls: false,
        paginationPageSize: 15,
        showGridFooter:true,
        enableSelectAll: true,
        columnDefs: [
            {field: 'id', displayName: 'ID', width: xxsw, enableSorting: false, enableHiding: false},
            {field: 'csiId', displayName: 'CSI ID', width: xsw, enableHiding: false},
            {field: 'csInstance', displayName: 'CS Instance', width: sw, enableHiding: false},
            {field: 'businessId', displayName: 'BUSINESS ID', width: sw, enableHiding: false},
            {field: 'bizUnitId', displayName: 'BIZ UNIT ID', width: sw, enableHiding: false},
            {field: 'productId', displayName: 'PRODUCT ID', width: sw, enableHiding: false},
            {field: 'bizProdId', displayName: 'BIZ PROD ID', width: sw, enableHiding: false},
            {field: 'cxScreeningBusinessUnitName', displayName: 'Cx Screening Business Unit Name', width: lw, enableHiding: false},
            {field: 'cxBusinessGreenzone', displayName: 'Cx Business Greenzone', width: mw, enableHiding: false},
            {field: 'rulesetMapped', displayName: 'Ruleset Mapped', width: sw, enableHiding: false},
            {field: 'region', displayName: 'Region', width: xsw, enableHiding: false},
            {field: 'country', displayName: 'Country', width: sw, enableHiding: false},
            {field: 'sector', displayName: 'Sector', width: xsw, enableHiding: false},
            {field: 'workflowFlag', displayName: 'Workflow Flag', width: sw, enableHiding: false},
            {field: 'workflowInstance', displayName: 'Workflow Instance', width: sw, enableHiding: false},
            {field: 'wfBusinessUnitNameDisplayValue', displayName: 'WF Business Unit Name Display Value', width: lw, enableHiding: false},
            {field: 'wfBusinessGreenzone', displayName: 'WF Business Greenzone', width: mw, enableHiding: false},
            {field: 'connectivityProtocol', displayName: 'Connectivity Protocol', width: mw, enableHiding: false},
            {field: 'interfaceAppId', displayName: 'Interface App ID', width: sw, enableHiding: false},
            {field: 'interfaceApplicationName', displayName: 'Interface Application Name', width: mw, enableHiding: false},
            {field: 'operationEntity', displayName: 'Operation Entity', width: sw, enableHiding: false},
            {field: 'opsComplianceContacts', displayName: 'Ops Compliance Contacts', width: mw, enableHiding: false},
            {field: 'cwVersion', displayName: 'Cw V1/V2', width: 1.5 * xsw, enableHiding: false},
            {field: 'gomCompliant', displayName: 'GOM Compliant?', width: sw, enableHiding: false},
            {field: 'cwUatContactName', displayName: 'Cw UAT Contact Name', width: mw, enableHiding: false},
            {field: 'sourceTechContact', displayName: 'Source Tech Contact', width: mw, enableHiding: false},
            {field: 'impactToBusiness', displayName: 'Impact To Business', width: mw, enableHiding: false},
            {field: 'businessEscalationPointOfContact', displayName: 'Business Escalation Point of Contact', width: lw, enableHiding: false},
            {field: 'timezone', displayName: 'Timezone', width: sw, enableHiding: false},
            {field: 'updateHistory', displayName: 'Update History', width: sw, enableHiding: false,
                cellTemplate: '<div align="center"><a ng-click="grid.appScope.showUpdateHistoryModal(row.entity.id)"><img src="images/history-img.png" height="34" width="34"></a></div>'},
            {field: 'deletionDetails', displayName: 'Deletion Details', width: sw, enableHiding: false}
        ],
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
        enableFullRowSelection: true,
        selectionRowHeaderWidth: 35,
        rowHeight: 35,
        //paginationPageSizes: [10, 20, 50],
        enablePaginationControls: false,
        paginationPageSize: 15,
        showGridFooter:true,
        enableSelectAll: true,
        columnDefs: [
            {field: 'id', displayName: 'ID', width: xxsw, enableSorting: false, enableHiding: false},
            {field: 'businessId', displayName: 'BUSINESS ID', width: sw, enableHiding: false},
            {field: 'productId', displayName: 'PRODUCT ID', width: sw, enableHiding: false},
            {field: 'csiId', displayName: 'CSI ID', width: xsw, enableHiding: false},
            {field: 'uniqueProductId', displayName: 'Unique Product ID', width: sw, enableHiding: false},
            {field: 'txScreeningBusinessUnitName', displayName: 'Tx Screening Business Unit Name', width: lw, enableHiding: false},
            {field: 'rulesetMapped', displayName: 'Ruleset Mapped', width: sw, enableHiding: false},
            {field: 'region', displayName: 'Region', width: xsw, enableHiding: false},
            {field: 'country', displayName: 'Country', width: sw, enableHiding: false},
            {field: 'sector', displayName: 'Sector', width: xsw, enableHiding: false},
            {field: 'workflowFlag', displayName: 'Workflow Flag', width: sw, enableHiding: false},
            {field: 'workflowInstance', displayName: 'Workflow Instance', width: sw, enableHiding: false},
            {field: 'wfBusinessGreenzone', displayName: 'WF Business Greenzone', width: mw, enableHiding: false},
            {field: 'interfaceAppId', displayName: 'Interface App ID', width: sw, enableHiding: false},
            {field: 'interfaceApplicationName', displayName: 'Interface Application Name', width: mw, enableHiding: false},
            {field: 'operationEntity', displayName: 'Operation Entity', width: sw, enableHiding: false},
            {field: 'connectivityProtocol', displayName: 'Connectivity Protocol', width: mw, enableHiding: false},
            {field: 'workflowOperationsContacts', displayName: 'Workflow Operations Contacts', width: mw, enableHiding: false},
            {field: 'sourceTechContacts', displayName: 'Source Tech Contacts', width: mw, enableHiding: false},
            {field: 'businessHotline', displayName: 'Business Hotline', width: mw, enableHiding: false},
            {field: 'businessEscalationPointOfContact', displayName: 'Business Escalation Point of Contact', width: lw, enableHiding: false},
            {field: 'impactToProductProcessor', displayName: 'Impact to Product Processor', width: mw, enableHiding: false},
            {field: 'productProcessor', displayName: 'Product Processor', width: sw, enableHiding: false},
            {field: 'hotlineNumber', displayName: 'Hotline Number', width: sw, enableHiding: false},
            {field: 'escalationPath1stLevelSupport', displayName: 'Escalation Path 1st Level Support', width: lw, enableHiding: false},
            {field: 'escalationPath2ndLevelSupport', displayName: 'Escalation Path 2nd Level Support', width: lw, enableHiding: false},
            {field: 'firstLevelEscalation', displayName: '1st Level Escalation', width: mw, enableHiding: false},
            {field: 'secondLevelEscalation', displayName: '2nd Level Escalation', width: mw, enableHiding: false},
            {field: 'productProcessorGroupDl', displayName: 'Product Processor Group DL', width: mw, enableHiding: false},
            {field: 'productProcessorSnowGroupName', displayName: 'Product Processor SNOW Group Name', width: lw, enableHiding: false},
            {field: 'productProcessorScreeningResponseCutoffTime', displayName: 'Product Processor Screening Response Cutoff Time', width: xlw, enableHiding: false},
            {field: 'productProcessorStandardGreenzones', displayName: 'Product Processor Standard Greenzones', width: lw, enableHiding: false},
            {field: 'interfaceConnectivityDoc', displayName: 'Interface Connectivity Doc', width: mw, enableHiding: false},
            {field: 'retryMechanism', displayName: 'Retry Mechanism', width: sw, enableHiding: false},
            {field: 'dailyOnlineVolumesExpected', displayName: 'Daily Online Volumes Expected', width: mw, enableHiding: false},
            {field: 'scheduleForRealtimeVolumes', displayName: 'Schedule For Realtime Volumes', width: mw, enableHiding: false},
            {field: 'batchesOrPeaksForRealtimeVolumes', displayName: 'Batches or Peaks for Realtime Volumes', width: lw, enableHiding: false},
            {field: 'initialScreeningResponseSla', displayName: 'Initial Screening Response SLA', width: mw, enableHiding: false},
            {field: 'thresholdSetForTimeouts', displayName: 'Threshold Set for Timeouts', width: mw, enableHiding: false},
            {field: 'anyBatchComponent', displayName: 'Any Batch Component?', width: mw, enableHiding: false},
            {field: 'workflowOperationsWorkSchedule', displayName: 'Workflow Operations Work Schedule', width: lw, enableHiding: false},
            {field: 'updateHistory', displayName: 'Update History', width: sw, enableHiding: false,
                cellTemplate: '<div align="center"><a ng-click="grid.appScope.showUpdateHistoryModal(row.entity.id)"><img src="images/history-img.png" height="34" width="34"></a></div>'},
            {field: 'deletionDetails', displayName: 'Deletion Details', width: sw, enableHiding: false}
        ],
        onRegisterApi: function (gridApi) {
            $scope.grid2Api = gridApi;
        },
        data: $scope.rowData
    };

    $scope.areRowsSelected = function() {
        return ($scope.recordType === 'CUSTOMER' ? $scope.grid1Api.grid.selection.selectedCount : $scope.grid2Api.grid.selection.selectedCount) > 0;
    };

    function getUpdateHistory(id) {
        var updateHistory = null;
        angular.forEach($scope.rowData, function(value, key) {
            if (value.id === id) {
                updateHistory = value.updateHistory;
            }
        });

        return updateHistory;
    }

    $scope.showUpdateHistoryModal = function(id) {
        console.log('function called');
        var updateHistory = getUpdateHistory(id);
        var updateHistoryModalInstance = $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: 'html/update-history-modal.html',
            controller: 'ModalController',
            size: 'md',
            resolve: {
                modalData: {
                    updateHistoryData: updateHistory
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
            templateUrl: 'html/delete-confirmation-modal.html',
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
        //var selectedRowData = $scope.getSelectedRowData();
        var clearModalInstance = $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: 'html/clear-confirmation-modal.html',
            controller: 'ModalController',
            size: 'md',
            resolve: {
                modalData: {
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
            templateUrl: 'html/restore-confirmation-modal.html',
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
        setTimeout("location.reload(true);",timeoutPeriod);
    }

    $scope.exactMatch = false;

    $scope.search = function () {
        var url = '/read-archive?recordType=';
        if ($scope.searchTerms) {
            $http.get(url + $scope.recordType + '&searchTerms=' + $scope.searchTerms + '&exactMatch=' + $scope.exactMatch).then(function (response) {
                handleResponse(response, false);
                console.log(response);
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

    function handleResponse(response, isRandom) {
        $scope.rowData.length = 0;
        angular.forEach(response.data, function (value, key) {
            $scope.rowData.push(convertResponseData(value, $scope.recordType, isRandom));
        });
    }

    function populateWithArchiveRows() {
        var url = '/archive?recordType=';
        $http.get(url + $scope.recordType).then(function (response) {
            handleResponse(response, false);
        }, function (error) {
            console.log(error);
        });
    }

    function convertResponseData(record, recordType, isRandom) {
        if (recordType === 'CUSTOMER') {
            return convertCxData(record, isRandom);
        } else {
            return convertTxData(record, isRandom);
        }
    }

    function convertCxData(record, isRandom) {
        return {
            id: isRandom ? record[0] : record.id,
            bizProdId: isRandom ? record[1] : record.buDetails.bizProdId,
            bizUnitId: isRandom ? record[2] : record.buDetails.bizUnitId,
            businessEscalationPointOfContact: isRandom ? record[3] : record.buDetails.businessEscalationPointOfContact,
            businessId: isRandom ? record[4] : record.buDetails.businessId,
            connectivityProtocol: isRandom ? record[5] : record.buDetails.connectivityProtocol,
            country: isRandom ? record[6] : record.buDetails.country,
            csInstance: isRandom ? record[7] : record.buDetails.csInstance,
            csiId: isRandom ? record[8] : record.buDetails.csiId,
            cwUatContactName: isRandom ? record[9] : record.buDetails.cwUatContactName,
            cwVersion: isRandom ? record[10] : record.buDetails.cwVersion,
            cxBusinessGreenzone: isRandom ? record[11] : record.buDetails.cxBusinessGreenzone,
            cxScreeningBusinessUnitName: isRandom ? record[12] : record.buDetails.cxScreeningBusinessUnitName,
            gomCompliant: convertBooleanToChar(isRandom ? record[13] : record.buDetails.gomCompliant),
            impactToBusiness: isRandom ? record[14] : record.buDetails.impactToBusiness,
            interfaceAppId: isRandom ? record[15] : record.buDetails.interfaceAppId,
            interfaceApplicationName: isRandom ? record[16] : record.buDetails.interfaceApplicationName,
            operationEntity: isRandom ? record[17] : record.buDetails.operationEntity,
            opsComplianceContacts: isRandom ? record[18] : record.buDetails.opsComplianceContacts,
            productId: isRandom ? record[19] : record.buDetails.productId,
            region: isRandom ? record[20] : record.buDetails.region,
            rulesetMapped: isRandom ? record[21] : record.buDetails.rulesetMapped,
            sector: isRandom ? record[22] : record.buDetails.sector,
            sourceTechContact: isRandom ? record[23] : record.buDetails.sourceTechContact,
            timezone: isRandom ? record[24] : record.buDetails.timezone,
            updateHistory: isRandom ? record[25] : record.buDetails.updateHistory,
            wfBusinessGreenzone: isRandom ? record[26] : record.buDetails.wfBusinessGreenzone,
            wfBusinessUnitNameDisplayValue: isRandom ? record[27] : record.buDetails.wfBusinessUnitNameDisplayValue,
            workflowFlag: convertBooleanToChar(isRandom ? record[28] : record.buDetails.workflowFlag),
            workflowInstance: isRandom ? record[29] : record.buDetails.workflowInstance,
            deletionDetails: isRandom ? record[30] : record.deletionDetails
        };
    }

    function convertTxData(record, isRandom) {
        return {
            id: isRandom ? record[0] : record.id,
            anyBatchComponent: convertBooleanToChar(isRandom ? record[1] : record.buDetails.anyBatchComponent),
            batchesOrPeaksForRealtimeVolumes: isRandom ? record[2] : record.buDetails.batchesOrPeaksForRealtimeVolumes,
            businessEscalationPointOfContact: isRandom ? record[3] : record.buDetails.businessEscalationPointOfContact,
            businessHotline: isRandom ? record[4] : record.buDetails.businessHotline,
            businessId: isRandom ? record[5] : record.buDetails.businessId,
            connectivityProtocol: isRandom ? record[6] : record.buDetails.connectivityProtocol,
            country: isRandom ? record[7] : record.buDetails.country,
            csiId: isRandom ? record[8] : record.buDetails.csiId,
            dailyOnlineVolumesExpected: isRandom ? record[9] : record.buDetails.dailyOnlineVolumesExpected,
            escalationPath1stLevelSupport: isRandom ? record[10] : record.buDetails.escalationPath1stLevelSupport,
            escalationPath2ndLevelSupport: isRandom ? record[11] : record.buDetails.escalationPath2ndLevelSupport,
            firstLevelEscalation: isRandom ? record[12] : record.buDetails.firstLevelEscalation,
            hotlineNumber: isRandom ? record[13] : record.buDetails.hotlineNumber,
            impactToProductProcessor: isRandom ? record[14] : record.buDetails.impactToProductProcessor,
            initialScreeningResponseSla: isRandom ? record[15] : record.buDetails.initialScreeningResponseSla,
            interfaceAppId: isRandom ? record[16] : record.buDetails.interfaceAppId,
            interfaceApplicationName: isRandom ? record[17] : record.buDetails.interfaceApplicationName,
            interfaceConnectivityDoc: isRandom ? record[18] : record.buDetails.interfaceConnectivityDoc,
            operationEntity: isRandom ? record[19] : record.buDetails.operationEntity,
            productId: isRandom ? record[20] : record.buDetails.productId,
            productProcessor: isRandom ? record[21] : record.buDetails.productProcessor,
            productProcessorGroupDl: isRandom ? record[22] : record.buDetails.productProcessorGroupDl,
            productProcessorScreeningResponseCutoffTime: isRandom ? record[23] : record.buDetails.productProcessorScreeningResponseCutoffTime,
            productProcessorSnowGroupName: isRandom ? record[24] : record.buDetails.productProcessorSnowGroupName,
            productProcessorStandardGreenzones: isRandom ? record[25] : record.buDetails.productProcessorStandardGreenzones,
            region: isRandom ? record[26] : record.buDetails.region,
            retryMechanism: isRandom ? record[27] : record.buDetails.retryMechanism,
            rulesetMapped: isRandom ? record[28] : record.buDetails.rulesetMapped,
            scheduleForRealtimeVolumes: isRandom ? record[29] : record.buDetails.scheduleForRealtimeVolumes,
            secondLevelEscalation: isRandom ? record[30] : record.buDetails.secondLevelEscalation,
            sector: isRandom ? record[31] : record.buDetails.sector,
            sourceTechContacts: isRandom ? record[32] : record.buDetails.sourceTechContacts,
            thresholdSetForTimeouts: isRandom ? record[33] : record.buDetails.thresholdSetForTimeouts,
            txScreeningBusinessUnitName: isRandom ? record[34] : record.buDetails.txScreeningBusinessUnitName,
            uniqueProductId: isRandom ? record[35] : record.buDetails.uniqueProductId,
            updateHistory: isRandom ? record[36] : record.buDetails.updateHistory,
            wfBusinessGreenzone: isRandom ? record[37] : record.buDetails.wfBusinessGreenzone,
            workflowFlag: convertBooleanToChar(isRandom ? record[38] : record.buDetails.workflowFlag),
            workflowInstance: isRandom ? record[39] : record.buDetails.workflowInstance,
            workflowOperationsContacts: isRandom ? record[40] : record.buDetails.workflowOperationsContacts,
            workflowOperationsWorkSchedule: isRandom ? record[41] : record.buDetails.workflowOperationsWorkSchedule,
            deletionDetails: isRandom ? record[42] : record.deletionDetails
        };
    }

    function convertBooleanToChar(boolean) {
        return boolean ? 'Y' : 'N';
    }

}]);