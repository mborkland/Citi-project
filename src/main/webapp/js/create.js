app.controller('CreateController', ['$scope', '$http', '$state', '$window', function($scope, $http, $state, $window) {
    $scope.cxData = $scope.cxData || {
        fields: [],
        requestor: ''
    }
    $scope.txData = $scope.txData || {
        fields: [],
        requestor: ''
    }

    $scope.duplicateRows = [];

    $scope.createCx = function() {
        var url = '/create?';
        angular.forEach($scope.cxData.fields, function(value, key) {
            url += 'fields=' + value + '&';
        });
        url += 'recordType=CUSTOMER&';
        url += 'requestor=' + $scope.cxData.requestor;
        $http.post(url).then(function (response) {
            console.log(response);
            alert(response.data.result);
            if(response.data.result == "Customer record created successfully") {
                $state.go('read');
            }
            else {
                $scope.cxDuplicateRecords();
                $window.parentScope = $scope;
                $window.open('html/duplicate-records.html', 'Duplicate Records', 'width=1000,height=600');
                //$scope.cxDuplicateRecords();
            }
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
            alert(response.data.result);
            if(response.data.result == "Transaction record created successfully") {
                $state.go('read');
            }
            else {
                $scope.txDuplicateRecords();
                $window.parentScope = $scope;
                $window.open('html/duplicate-records.html', 'Duplicate Records', 'width=1000,height=600');
            }
        }, function (error) {
            console.log(error);
            alert('There was an error');
            $state.go('read');
        });
    }

    /*$scope.duplicateRecords = function(recordType) {
            if(recordType === 'CUSTOMER') {
                $scope.cxDuplicateRecords();
            }
            else if(recordType === 'TRANSACTION') {
                $scope.txDuplicateRecords();
            }
    }*/

    $scope.cxDuplicateRecords = function() {
        var url = '/duplicate-records?';
        angular.forEach($scope.cxData.fields, function(value, key) {
            url += 'fields=' + value + '&';
        });
        url += 'recordType=CUSTOMER&';
        url += 'requestor=' + $scope.cxData.requestor;
        $http.get(url).then(function (response) {
            console.log(response);
            handleCxResponse(response);
        }, function (error) {
            console.log(error);
        });
    }

    $scope.txDuplicateRecords = function() {
        var url = '/duplicate-records?';
        angular.forEach($scope.txData.fields, function(value, key) {
            url += 'fields=' + value + '&';
        });
        url += 'recordType=TRANSACTION&';
        url += 'requestor=' + $scope.txData.requestor;
        $http.get(url).then(function (response) {
            handleTxResponse(response);
        }, function (error) {
            console.log(error);
        });
    }

    function handleCxResponse(response) {
        $scope.duplicateRows.length = 0;
        angular.forEach(response.data, function (value, key) {
            $scope.duplicateRows.push(convertResponseData(value, "CUSTOMER", false));
        });
        console.log($scope.duplicateRows);
    }

    function handleTxResponse(response) {
        $scope.duplicateRows.length = 0;
        angular.forEach(response.data, function (value, key) {
            $scope.duplicateRows.push(convertResponseData(value, "TRANSACTION", false));
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
            workflowInstance: isRandom ? record[29] : record.buDetails.workflowInstance
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
            workflowOperationsWorkSchedule: isRandom ? record[41] : record.buDetails.workflowOperationsWorkSchedule
        };
    }

    function convertBooleanToChar(boolean) {
        return boolean ? 'Y' : 'N';
    }

    function convertCharToBoolean(char) {
        return !!'Y';
    }
}]);