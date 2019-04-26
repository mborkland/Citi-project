app.controller('CreationSummaryController', ['$scope', '$stateParams', 'tableService', function ($scope, $stateParams, tableService) {
    $scope.recordType = $stateParams.recordType;
    $scope.data = (function() {
        var data = {};
        angular.forEach($stateParams.data, function (value, key) {
            data[key] = tableService.booleanFields.includes(key) ? tableService.convertBooleanToChar(value) : value;
        });
        return data;
    })();
    $scope.soeid = $stateParams.soeid;

    $scope.rows = (function (){
        var rows = [];
        if ($scope.recordType === 'CUSTOMER') {
            rows.push([
                {title: 'CSI ID', data: $scope.data.csiId},
                {title: 'CS Instance', data: $scope.data.csInstance},
                {title: 'BUSINESS_ID', data: $scope.data.businessId},
                {title: 'BIZ_UNIT_ID', data: $scope.data.bizUnitId}
            ]);
            rows.push([
                {title: 'PRODUCT_ID', data: $scope.data.productId},
                {title: 'BIZ_PROD_ID', data: $scope.data.bizProdId},
                {title: 'Cx Screening Business Unit Name', data: $scope.data.cxScreeningBusinessUnitName},
                {title: 'Cx Business Greenzone', data: $scope.data.cxBusinessGreenzone}
            ]);
            rows.push([
                {title: 'Ruleset Mapped', data: $scope.data.rulesetMapped},
                {title: 'Region', data: $scope.data.region},
                {title: 'Country', data: $scope.data.country},
                {title: 'Sector', data: $scope.data.sector}
            ]);
            rows.push([
                {title: 'Workflow Flag', data: $scope.data.workflowFlag},
                {title: 'Workflow Instance', data: $scope.data.workflowInstance},
                {title: 'WF Business Unit Name Display Value', data: $scope.data.wfBusinessUnitNameDisplayValue},
                {title: 'WF Business Greenzone', data: $scope.data.wfBusinessGreenzone}
            ]);
            rows.push([
                {title: 'Connectivity Protocol', data: $scope.data.connectivityProtocol},
                {title: 'Interface App ID', data: $scope.data.interfaceAppId},
                {title: 'Interface Application Name', data: $scope.data.interfaceApplicationName},
                {title: 'Operation Entity', data: $scope.data.operationEntity}
            ]);
            rows.push([
                {title: 'Ops Compliance Contacts', data: $scope.data.opsComplianceContacts},
                {title: 'Cw Version', data: $scope.data.cwVersion},
                {title: 'GOM Compliant?', data: $scope.data.gomCompliant},
                {title: 'Cw UAT Contact Name', data: $scope.data.cwUatContactName}
            ]);
            rows.push([
                {title: 'Source Tech Contact', data: $scope.data.sourceTechContact},
                {title: 'Impact to Business', data: $scope.data.impactToBusiness},
                {title: 'Business Escalation Point of Contact', data: $scope.data.businessEscalationPointOfContact},
                {title: 'Timezone', data: $scope.data.country}
            ]);
        } else {
            rows.push([
                {title: 'Business ID', data: $scope.data.businessId},
                {title: 'Product ID', data: $scope.data.productId},
                {title: 'CSI ID', data: $scope.data.csiId},
                {title: 'Unique_Product_ID/Workflow BU ID', data: $scope.data.uniqueProductId}
            ]);
            rows.push([
                {title: 'Tx Screening Business Unit Name', data: $scope.data.txScreeningBusinessUnitName},
                {title: 'Ruleset Mapped', data: $scope.data.rulesetMapped},
                {title: 'Region', data: $scope.data.region},
                {title: 'Country', data: $scope.data.country}
            ]);
            rows.push([
                {title: 'Sector', data: $scope.data.sector},
                {title: 'Workflow Flag', data: $scope.data.workflowFlag},
                {title: 'Workflow Instance', data: $scope.data.workflowInstance},
                {title: 'WF Business Greenzone', data: $scope.data.wfBusinessGreenzone}
            ]);
            rows.push([
                {title: 'Interface App ID', data: $scope.data.interfaceAppId},
                {title: 'Interface Application Name', data: $scope.data.interfaceApplicationName},
                {title: 'Operation Entity', data: $scope.data.operationEntity},
                {title: 'Connectivity Protocol', data: $scope.data.connectivityProtocol}
            ]);
            rows.push([
                {title: 'Workflow Operations Contacts', data: $scope.data.workflowOperationsContacts},
                {title: 'Source Tech Contacts', data: $scope.data.sourceTechContacts},
                {title: 'Business Hotline', data: $scope.data.businessHotline},
                {title: 'Business Escalation Point of Contact', data: $scope.data.businessEscalationPointOfContact}
            ]);
            rows.push([
                {title: 'Impact to Product Processor', data: $scope.data.impactToProductProcessor},
                {title: 'Product Processor', data: $scope.data.productProcessor},
                {title: 'Hotline Number', data: $scope.data.hotlineNumber},
                {title: 'Escalation Path 1st Level Support', data: $scope.data.escalationPath1stLevelSupport}
            ]);
            rows.push([
                {title: 'Escalation Path 2nd Level Support', data: $scope.data.escalationPath2ndLevelSupport},
                {title: '1st Level Escalation: Application Manager', data: $scope.data.firstLevelEscalation},
                {title: '2nd Level Escalation: One Level Above Application Manager', data: $scope.data.secondLevelEscalation},
                {title: 'Product Processor Group DL', data: $scope.data.productProcessorGroupDl}
            ]);
            rows.push([
                {title: 'Product Processor SNOW Group Name', data: $scope.data.productProcessorSnowGroupName},
                {title: 'Product Processor Screening Response Cutoff Time', data: $scope.data.productProcessorScreeningResponseCutoffTime},
                {title: 'Product Processor Standard Greenzones', data: $scope.data.productProcessorStandardGreenzones},
                {title: 'Interface/Connectivity Doc (LINK)', data: $scope.data.interfaceConnectivityDoc}
            ]);
            rows.push([
                {title: 'Retry Mechanism (Y/N)', data: $scope.data.retryMechanism},
                {title: 'Daily Online Volumes Expected', data: $scope.data.dailyOnlineVolumesExpected},
                {title: 'Schedule for Realtime Volumes', data: $scope.data.scheduleForRealtimeVolumes},
                {title: 'Batches or Peaks for Realtime Volumes', data: $scope.data.batchesOrPeaksForRealtimeVolumes}
            ]);
            rows.push([
                {title: 'Initial Screening Response SLA', data: $scope.data.initialScreeningResponseSla},
                {title: 'Threshold Set for Timeouts', data: $scope.data.thresholdSetForTimeouts},
                {title: 'Any Batch Component (Y/N)', data: $scope.data.anyBatchComponent},
                {title: 'Workflow Operations Work Schedule (EST)', data: $scope.data.workflowOperationsWorkSchedule}
            ]);
        }

        return rows;
    })();
}]);