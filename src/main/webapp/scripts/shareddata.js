var app = angular.module('shared.data', []);
app.factory('tableService', function($window, $timeout) {
    var service = {};
    service.booleanFields = ['gomCompliant', 'workflowFlag', 'anyBatchComponent'];
    var historyCellTemplate = '<div align="center"><a ng-click="grid.appScope.showHistoryModal(row.entity.id)"><img src="images/history-img.png" height="34" width="34"></a></div>';

    service.xsmWidth = 90;
    service.smWidth = 2 * service.xsmWidth;
    service.mdWidth = 3 * service.xsmWidth;
    service.lgWidth = 4 * service.xsmWidth;
    service.xlgWidth = 4.5 * service.xsmWidth;

    service.cxColumnDefs = [
        {field: 'id', displayName: 'ID', width: service.xsmWidth, enableSorting: false, enableHiding: false},
        {field: 'csiId', displayName: 'CSI ID', width: service.xsmWidth, enableHiding: false},
        {field: 'csInstance', displayName: 'CS Instance', width: service.smWidth, enableHiding: false},
        {field: 'businessId', displayName: 'BUSINESS ID', width: service.smWidth, enableHiding: false},
        {field: 'bizUnitId', displayName: 'BIZ UNIT ID', width: service.smWidth, enableHiding: false},
        {field: 'productId', displayName: 'PRODUCT ID', width: service.smWidth, enableHiding: false},
        {field: 'bizProdId', displayName: 'BIZ PROD ID', width: service.smWidth, enableHiding: false},
        {field: 'cxScreeningBusinessUnitName', displayName: 'Cx Screening Business Unit Name', width: service.lgWidth, enableHiding: false},
        {field: 'cxBusinessGreenzone', displayName: 'Cx Business Greenzone', width: service.mdWidth, enableHiding: false},
        {field: 'rulesetMapped', displayName: 'Ruleset Mapped', width: service.smWidth, enableHiding: false},
        {field: 'region', displayName: 'Region', width: service.xsmWidth, enableHiding: false},
        {field: 'country', displayName: 'Country', width: service.smWidth, enableHiding: false},
        {field: 'sector', displayName: 'Sector', width: service.xsmWidth, enableHiding: false},
        {field: 'workflowFlag', displayName: 'Workflow Flag', width: service.smWidth, enableHiding: false},
        {field: 'workflowInstance', displayName: 'Workflow Instance', width: service.smWidth, enableHiding: false},
        {field: 'wfBusinessUnitNameDisplayValue', displayName: 'WF Business Unit Name Display Value', width: service.lgWidth, enableHiding: false},
        {field: 'wfBusinessGreenzone', displayName: 'WF Business Greenzone', width: service.mdWidth, enableHiding: false},
        {field: 'connectivityProtocol', displayName: 'Connectivity Protocol', width: service.mdWidth, enableHiding: false},
        {field: 'interfaceAppId', displayName: 'Interface App ID', width: service.smWidth, enableHiding: false},
        {field: 'interfaceApplicationName', displayName: 'Interface Application Name', width: service.mdWidth, enableHiding: false},
        {field: 'operationEntity', displayName: 'Operation Entity', width: service.smWidth, enableHiding: false},
        {field: 'opsComplianceContacts', displayName: 'Ops Compliance Contacts', width: service.mdWidth, enableHiding: false},
        {field: 'cwVersion', displayName: 'Cw V1/V2', width: 1.5 * service.xsmWidth, enableHiding: false},
        {field: 'gomCompliant', displayName: 'GOM Compliant?', width: service.smWidth, enableHiding: false},
        {field: 'cwUatContactName', displayName: 'Cw UAT Contact Name', width: service.mdWidth, enableHiding: false},
        {field: 'sourceTechContact', displayName: 'Source Tech Contact', width: service.mdWidth, enableHiding: false},
        {field: 'impactToBusiness', displayName: 'Impact To Business', width: service.mdWidth, enableHiding: false},
        {field: 'businessEscalationPointOfContact', displayName: 'Business Escalation Point of Contact', width: service.lgWidth, enableHiding: false},
        {field: 'timezone', displayName: 'Timezone', width: service.smWidth, enableHiding: false},
        {field: 'history', displayName: 'History', width: service.smWidth, enableHiding: false, cellTemplate: historyCellTemplate},
        {field: 'creationDate', visible: false}
    ];

    service.txColumnDefs = [
        {field: 'id', displayName: 'ID', width: service.xsmWidth, enableSorting: false, enableHiding: false},
        {field: 'businessId', displayName: 'BUSINESS ID', width: service.smWidth, enableHiding: false},
        {field: 'productId', displayName: 'PRODUCT ID', width: service.smWidth, enableHiding: false},
        {field: 'csiId', displayName: 'CSI ID', width: service.xsmWidth, enableHiding: false},
        {field: 'uniqueProductId', displayName: 'Unique Product ID', width: service.smWidth, enableHiding: false},
        {field: 'txScreeningBusinessUnitName', displayName: 'Tx Screening Business Unit Name', width: service.lgWidth, enableHiding: false},
        {field: 'rulesetMapped', displayName: 'Ruleset Mapped', width: service.smWidth, enableHiding: false},
        {field: 'region', displayName: 'Region', width: service.xsmWidth, enableHiding: false},
        {field: 'country', displayName: 'Country', width: service.smWidth, enableHiding: false},
        {field: 'sector', displayName: 'Sector', width: service.xsmWidth, enableHiding: false},
        {field: 'workflowFlag', displayName: 'Workflow Flag', width: service.smWidth, enableHiding: false},
        {field: 'workflowInstance', displayName: 'Workflow Instance', width: service.smWidth, enableHiding: false},
        {field: 'wfBusinessGreenzone', displayName: 'WF Business Greenzone', width: service.mdWidth, enableHiding: false},
        {field: 'interfaceAppId', displayName: 'Interface App ID', width: service.smWidth, enableHiding: false},
        {field: 'interfaceApplicationName', displayName: 'Interface Application Name', width: service.mdWidth, enableHiding: false},
        {field: 'operationEntity', displayName: 'Operation Entity', width: service.smWidth, enableHiding: false},
        {field: 'connectivityProtocol', displayName: 'Connectivity Protocol', width: service.mdWidth, enableHiding: false},
        {field: 'workflowOperationsContacts', displayName: 'Workflow Operations Contacts', width: service.mdWidth, enableHiding: false},
        {field: 'sourceTechContacts', displayName: 'Source Tech Contacts', width: service.mdWidth, enableHiding: false},
        {field: 'businessHotline', displayName: 'Business Hotline', width: service.mdWidth, enableHiding: false},
        {field: 'businessEscalationPointOfContact', displayName: 'Business Escalation Point of Contact', width: service.lgWidth, enableHiding: false},
        {field: 'impactToProductProcessor', displayName: 'Impact to Product Processor', width: service.mdWidth, enableHiding: false},
        {field: 'productProcessor', displayName: 'Product Processor', width: service.smWidth, enableHiding: false},
        {field: 'hotlineNumber', displayName: 'Hotline Number', width: service.smWidth, enableHiding: false},
        {field: 'escalationPath1stLevelSupport', displayName: 'Escalation Path 1st Level Support', width: service.lgWidth, enableHiding: false},
        {field: 'escalationPath2ndLevelSupport', displayName: 'Escalation Path 2nd Level Support', width: service.lgWidth, enableHiding: false},
        {field: 'firstLevelEscalation', displayName: '1st Level Escalation', width: service.mdWidth, enableHiding: false},
        {field: 'secondLevelEscalation', displayName: '2nd Level Escalation', width: service.mdWidth, enableHiding: false},
        {field: 'productProcessorGroupDl', displayName: 'Product Processor Group DL', width: service.mdWidth, enableHiding: false},
        {field: 'productProcessorSnowGroupName', displayName: 'Product Processor SNOW Group Name', width: service.lgWidth, enableHiding: false},
        {field: 'productProcessorScreeningResponseCutoffTime', displayName: 'Product Processor Screening Response Cutoff Time', width: service.xlgWidth, enableHiding: false},
        {field: 'productProcessorStandardGreenzones', displayName: 'Product Processor Standard Greenzones', width: service.lgWidth, enableHiding: false},
        {field: 'interfaceConnectivityDoc', displayName: 'Interface Connectivity Doc', width: service.mdWidth, enableHiding: false},
        {field: 'retryMechanism', displayName: 'Retry Mechanism', width: service.smWidth, enableHiding: false},
        {field: 'dailyOnlineVolumesExpected', displayName: 'Daily Online Volumes Expected', width: service.mdWidth, enableHiding: false},
        {field: 'scheduleForRealtimeVolumes', displayName: 'Schedule For Realtime Volumes', width: service.mdWidth, enableHiding: false},
        {field: 'batchesOrPeaksForRealtimeVolumes', displayName: 'Batches or Peaks for Realtime Volumes', width: service.lgWidth, enableHiding: false},
        {field: 'initialScreeningResponseSla', displayName: 'Initial Screening Response SLA', width: service.mdWidth, enableHiding: false},
        {field: 'thresholdSetForTimeouts', displayName: 'Threshold Set for Timeouts', width: service.mdWidth, enableHiding: false},
        {field: 'anyBatchComponent', displayName: 'Any Batch Component?', width: service.mdWidth, enableHiding: false},
        {field: 'workflowOperationsWorkSchedule', displayName: 'Workflow Operations Work Schedule', width: service.lgWidth, enableHiding: false},
        {field: 'history', displayName: 'History', width: service.smWidth, enableHiding: false, cellTemplate: historyCellTemplate},
        {field: 'creationDate', visible: false}
    ];

    service.convertBooleanToChar = function (boolean) {
        return boolean ? 'Y' : 'N';
    };

    service.convertCxData = function (record, isRecent) {
        var cxData = {
            id: isRecent ? record[0] : record.id,
            creationDate: isRecent ? record[30] : record.creationDate
        };

        if (isRecent) {
            cxData.bizProdId = record[1];
            cxData.bizUnitId = record[2];
            cxData.businessEscalationPointOfContact = record[3];
            cxData.businessId = record[4];
            cxData.connectivityProtocol = record[5];
            cxData.country = record[6];
            cxData.csInstance = record[7];
            cxData.csiId = record[8];
            cxData.cwUatContactName = record[9];
            cxData.cwVersion = record[10];
            cxData.cxBusinessGreenzone = record[11];
            cxData.cxScreeningBusinessUnitName = record[12];
            cxData.gomCompliant = service.convertBooleanToChar(record[13]);
            cxData.history = record[14];
            cxData.impactToBusiness = record[15];
            cxData.interfaceAppId = record[16];
            cxData.interfaceApplicationName = record[17];
            cxData.operationEntity = record[18];
            cxData.opsComplianceContacts = record[19];
            cxData.productId = record[20];
            cxData.region = record[21];
            cxData.rulesetMapped = record[22];
            cxData.sector = record[23];
            cxData.sourceTechContact = record[24];
            cxData.timezone = record[25];
            cxData.wfBusinessGreenzone = record[26];
            cxData.wfBusinessUnitNameDisplayValue = record[27];
            cxData.workflowFlag = service.convertBooleanToChar(record[28]);
            cxData.workflowInstance = record[29];
        } else {
            angular.forEach(record.buDetails, function (value, key) {
                cxData[key] = service.booleanFields.includes(key) ? service.convertBooleanToChar(value) : value;
            });
        }

        return cxData;
    };

    service.convertTxData = function (record, isRecent) {
        var txData = {
            id: isRecent ? record[0] : record.id,
            creationDate: isRecent ? record[42] : record.creationDate
        };

        if (isRecent) {
            txData.anyBatchComponent = service.convertBooleanToChar(record[1]);
            txData.batchesOrPeaksForRealtimeVolumes = record[2];
            txData.businessEscalationPointOfContact = record[3];
            txData.businessHotline = record[4];
            txData.businessId = record[5];
            txData.connectivityProtocol = record[6];
            txData.country = record[7];
            txData.csiId = record[8];
            txData.dailyOnlineVolumesExpected = record[9];
            txData.escalationPath1stLevelSupport = record[10];
            txData.escalationPath2ndLevelSupport = record[11];
            txData.firstLevelEscalation = record[12];
            txData.history = record[13];
            txData.hotlineNumber = record[14];
            txData.impactToProductProcessor = record[15];
            txData.initialScreeningResponseSla = record[16];
            txData.interfaceAppId = record[17];
            txData.interfaceApplicationName = record[18];
            txData.interfaceConnectivityDoc = record[19];
            txData.operationEntity = record[20];
            txData.productId = record[21];
            txData.productProcessor = record[22];
            txData.productProcessorGroupDl = record[23];
            txData.productProcessorScreeningResponseCutoffTime = record[24];
            txData.productProcessorSnowGroupName = record[25];
            txData.productProcessorStandardGreenzones = record[26];
            txData.region = record[27];
            txData.retryMechanism = record[28];
            txData.rulesetMapped = record[29];
            txData.scheduleForRealtimeVolumes = record[30];
            txData.secondLevelEscalation = record[31];
            txData.sector = record[32];
            txData.sourceTechContacts = record[33];
            txData.thresholdSetForTimeouts = record[34];
            txData.txScreeningBusinessUnitName = record[35];
            txData.uniqueProductId = record[36];
            txData.wfBusinessGreenzone = record[37];
            txData.workflowFlag = service.convertBooleanToChar(record[38]);
            txData.workflowInstance = record[39];
            txData.workflowOperationsContacts = record[40];
            txData.workflowOperationsWorkSchedule = record[41];
        } else {
            angular.forEach(record.buDetails, function (value, key) {
                txData[key] = service.booleanFields.includes(key) ? service.convertBooleanToChar(value) : value;
            });
        }

        return txData;
    };

    service.getHistory = function(rowData, id) {
        var history = null;
        angular.forEach(rowData, function (value, key) {
            if (value.id === id) {
                history = value.history;
            }
        });

        return history ? history.split('#') : null;
    };

    service.areRowsSelected = function(gridApi) {
        return gridApi ? gridApi.grid.selection.selectedCount > 0 : false;
    };

    service.timedRefresh = function(timeoutPeriod) {
        $timeout(function() {
            $window.location.reload(true);
        }, timeoutPeriod);
    };



    return service;
});