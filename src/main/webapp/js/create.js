app.controller('CreateController', ['$scope', '$http', '$state', '$window', '$uibModal',
function($scope, $http, $state, $window, $uibModal) {
    $scope.cxBuDetails = $scope.cxBuDetails || {};
    $scope.txBuDetails = $scope.txBuDetails || {};
    $scope.requestorData = $scope.requestorData || {};
    $scope.pattern = "^[a-zA-Z0-9.:;, ]*$";

    $scope.duplicateRows = [];

    $scope.isFormValid = function(ngForm) {
        $scope.$broadcast('$validate');
        if(!ngForm.$invalid) {
            return true;
        }
        else {
            ngForm.$dirty = true;
            for(var i in ngForm) {
                if(ngForm[i] && ngForm[i].hasOwnProperty && ngForm[i].hasOwnProperty('$dirty')) {
                    ngForm[i].$dirty = true;
                }
            }
        }
    }

    $scope.nextButton = function(ngForm) {
        if($scope.isFormValid(ngForm)) {
            if($state.current.name === 'create-cx.section1')
                $state.go('create-cx.section2');
            else if($state.current.name === 'create-cx.section2')
                $state.go('create-cx.section3');
            else if($state.current.name === 'create-tx.section1')
                $state.go('create-tx.section2');
            else if($state.current.name === 'create-tx.section2')
                $state.go('create-tx.section3');
            else if($state.current.name === 'create-tx.section3')
                $state.go('create-tx.section4');
            else if($state.current.name === 'create-tx.section4')
                $state.go('create-tx.section5');
            else if($state.current.name === 'create-tx.section5')
                $state.go('create-tx.section6');
            else if($state.current.name === 'create-tx.section6')
                $state.go('create-tx.section7');
        }
        else {
            $state.go($state.current.name);
        }
    }

    $scope.forceCreate = function() {
        if ($scope.recordType === 'CUSTOMER') {
            $scope.createCx(true);
        } else {
            $scope.createTx(true);
        }
    };

    $scope.createCx = function(force) {
        var data = {
            buDetails: $scope.cxBuDetails,
            requestor: $scope.requestorData.requestor
        };

        if ($scope.isFormValid($scope['section3'])) {
            if (force) {
                $http({
                    method: 'POST',
                    url: '/create-cx',
                    data: data
                }).then(function (response) {
                    console.log(response);
                    alert(response.data.result);
                    $state.go('read');
                }, function (error) {
                    console.log(error);
                    alert('There was an error');
                });
            } else {
                $scope.recordType = 'CUSTOMER';
                $http({
                    method: 'POST',
                    url: '/duplicate-cx',
                    data: $scope.cxBuDetails
                }).then(function (response) {
                    console.log(response);
                    if (response.data.length) {
                        $scope.duplicateRows.length = 0;
                        angular.forEach(response.data, function (value, key) {
                            $scope.duplicateRows.push(convertResponseData(value, false));
                        });
                        showDuplicateModal($scope.forceCreate, $scope.openDuplicateWindow);
                    } else {
                        $scope.createCx(true);
                    }
                }, function (error) {
                    console.log(error);
                });
            }
        }
    };

    $scope.createTx = function(force) {
        var data = {
            buDetails: $scope.txBuDetails,
            requestor: $scope.requestorData.requestor
        };

        if ($scope.isFormValid($scope['section7'])) {
            if (force) {
                $http({
                    method: 'POST',
                    url: '/create-tx',
                    data: data
                }).then(function (response) {
                    console.log(response);
                    alert(response.data.result);
                    $state.go('read');
                }, function (error) {
                    console.log(error);
                    alert('There was an error');
                });
            } else {
                $scope.recordType = 'TRANSACTION';
                $http({
                    method: 'POST',
                    url: '/duplicate-tx',
                    data: $scope.txBuDetails
                }).then(function (response) {
                    console.log(response);
                    if (response.data.length) {
                        $scope.duplicateRows.length = 0;
                        angular.forEach(response.data, function (value, key) {
                            $scope.duplicateRows.push(convertResponseData(value, false));
                        });
                        showDuplicateModal($scope.forceCreate, $scope.openDuplicateWindow);
                    } else {
                        $scope.createTx(true);
                    }
                }, function (error) {
                    console.log(error);
                });
            }
        }
    };

    $scope.openDuplicateWindow = function() {
        $window.parentScope = $scope;
        $window.open('html/duplicate-records.html', 'Duplicate Records', 'width=1000,height=600');
    };

    function showDuplicateModal(forceCreate, openDuplicateWindow) {
        $scope.numDuplicates = $scope.duplicateRows.length;
        var duplicateModalInstance = $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: 'html/duplicate-modal.html',
            controller: function ($scope) {
                $scope.forceCreate = function() {
                    $scope.$close();
                    forceCreate();
                };
                $scope.openDuplicateWindow = function() {
                    $scope.$close();
                    openDuplicateWindow();
                }
            },
            scope: $scope,
            size: 'md'
        });
    }

    $scope.showHistoryModal = function(id) {
        var history = getHistory(id);
        var historyModalInstance = $uibModal.open({
            animation: true,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: 'html/history-modal.html',
            controller: 'ModalController',
            size: 'md',
            resolve: {
                modalData: {
                    historyData: history
                }
            }
        });
    };

    function convertResponseData(record, isRandom) {
        if ($scope.recordType === 'CUSTOMER') {
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
            history: isRandom ? record[14] : record.buDetails.history,
            impactToBusiness: isRandom ? record[15] : record.buDetails.impactToBusiness,
            interfaceAppId: isRandom ? record[16] : record.buDetails.interfaceAppId,
            interfaceApplicationName: isRandom ? record[17] : record.buDetails.interfaceApplicationName,
            operationEntity: isRandom ? record[18] : record.buDetails.operationEntity,
            opsComplianceContacts: isRandom ? record[19] : record.buDetails.opsComplianceContacts,
            productId: isRandom ? record[20] : record.buDetails.productId,
            region: isRandom ? record[21] : record.buDetails.region,
            rulesetMapped: isRandom ? record[22] : record.buDetails.rulesetMapped,
            sector: isRandom ? record[23] : record.buDetails.sector,
            sourceTechContact: isRandom ? record[24] : record.buDetails.sourceTechContact,
            timezone: isRandom ? record[25] : record.buDetails.timezone,
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
            history: isRandom ? record[13] : record.buDetails.history,
            hotlineNumber: isRandom ? record[14] : record.buDetails.hotlineNumber,
            impactToProductProcessor: isRandom ? record[15] : record.buDetails.impactToProductProcessor,
            initialScreeningResponseSla: isRandom ? record[16] : record.buDetails.initialScreeningResponseSla,
            interfaceAppId: isRandom ? record[17] : record.buDetails.interfaceAppId,
            interfaceApplicationName: isRandom ? record[18] : record.buDetails.interfaceApplicationName,
            interfaceConnectivityDoc: isRandom ? record[19] : record.buDetails.interfaceConnectivityDoc,
            operationEntity: isRandom ? record[20] : record.buDetails.operationEntity,
            productId: isRandom ? record[21] : record.buDetails.productId,
            productProcessor: isRandom ? record[22] : record.buDetails.productProcessor,
            productProcessorGroupDl: isRandom ? record[23] : record.buDetails.productProcessorGroupDl,
            productProcessorScreeningResponseCutoffTime: isRandom ? record[24] : record.buDetails.productProcessorScreeningResponseCutoffTime,
            productProcessorSnowGroupName: isRandom ? record[25] : record.buDetails.productProcessorSnowGroupName,
            productProcessorStandardGreenzones: isRandom ? record[26] : record.buDetails.productProcessorStandardGreenzones,
            region: isRandom ? record[27] : record.buDetails.region,
            retryMechanism: isRandom ? record[28] : record.buDetails.retryMechanism,
            rulesetMapped: isRandom ? record[29] : record.buDetails.rulesetMapped,
            scheduleForRealtimeVolumes: isRandom ? record[30] : record.buDetails.scheduleForRealtimeVolumes,
            secondLevelEscalation: isRandom ? record[31] : record.buDetails.secondLevelEscalation,
            sector: isRandom ? record[32] : record.buDetails.sector,
            sourceTechContacts: isRandom ? record[33] : record.buDetails.sourceTechContacts,
            thresholdSetForTimeouts: isRandom ? record[34] : record.buDetails.thresholdSetForTimeouts,
            txScreeningBusinessUnitName: isRandom ? record[35] : record.buDetails.txScreeningBusinessUnitName,
            uniqueProductId: isRandom ? record[36] : record.buDetails.uniqueProductId,
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