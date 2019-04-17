'use strict';

var app = angular.module('app', ['ui.router', 'ngAnimate', 'ngTouch', 'ngCookies', 'ui.grid', 'ui.grid.resizeColumns',
    'ui.grid.pagination', 'ui.grid.selection', 'ui.grid.exporter', 'ui.bootstrap', 'ui.grid.cellNav', 'ngPatternRestrict', 'ngMessages']);

app.config(['$stateProvider', '$urlRouterProvider',
function ($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('/');

    $stateProvider
        .state('login', {
            url: '/',
            templateUrl: 'html/login.html',
            controller: 'LoginController'
        })
        .state('read', {
            url: '/read',
            templateUrl: 'html/read.html',
            controller: 'ReadController'
        })
        .state('create-cx', {
            url: '/create-cx',
            abstract: true,
            templateUrl: 'html/create/create-cx/create-cx.html',
            controller: 'CreateController'
        })
        .state('create-cx.section1', {
            url: '',
            templateUrl: 'html/create/create-cx/section1.html',
            controller: 'CreateController'
        })
        .state('create-cx.section2', {
            url: '/section2',
            templateUrl: 'html/create/create-cx/section2.html',
            controller: 'CreateController'
        })
        .state('create-cx.section3', {
            url: '/section3',
            templateUrl: 'html/create/create-cx/section3.html',
            controller: 'CreateController'
        })
        .state('create-tx', {
            url: '/create-tx',
            abstract: true,
            templateUrl: 'html/create/create-tx/create-tx.html',
            controller: 'CreateController'
        })
        .state('create-tx.section1', {
            url: '',
            templateUrl: 'html/create/create-tx/section1.html',
            controller: 'CreateController'
        })
        .state('create-tx.section2', {
            url: '/section2',
            templateUrl: 'html/create/create-tx/section2.html',
            controller: 'CreateController'
        })
        .state('create-tx.section3', {
            url: '/section3',
            templateUrl: 'html/create/create-tx/section3.html',
            controller: 'CreateController'
        })
        .state('create-tx.section4', {
            url: '/section4',
            templateUrl: 'html/create/create-tx/section4.html',
            controller: 'CreateController'
        })
        .state('create-tx.section5', {
            url: '/section5',
            templateUrl: 'html/create/create-tx/section5.html',
            controller: 'CreateController'
        })
        .state('create-tx.section6', {
            url: '/section6',
            templateUrl: 'html/create/create-tx/section6.html',
            controller: 'CreateController'
        })
        .state('create-tx.section7', {
            url: '/section7',
            templateUrl: 'html/create/create-tx/section7.html',
            controller: 'CreateController'
        })
        .state('archive', {
            url: '/archive',
            templateUrl: 'html/archive.html',
            controller: 'ArchiveController'
        });
}]);

app.controller('AppController', ['$rootScope', '$scope', '$cookies', '$http', '$state',
function ($rootScope, $scope, $cookies, $http, $state) {
    $scope.isUser = $rootScope.isUser;
    $scope.isAdmin = $rootScope.isAdmin;

    $scope.logout = function() {
        $http.defaults.headers.common.Authorization = '';
        $cookies.remove('currentUser');
        $cookies.remove('token');
        $cookies.put('isUser', 'false');
        $cookies.put('isAdmin', 'false');
        $state.go('login');
    }
}]);

app.run(['$rootScope', '$state', '$cookies', '$http', function ($rootScope, $state, $cookies, $http) {
    $rootScope.isUser = function() {
        return $cookies.get('isUser') === 'true';
    }

    $rootScope.isAdmin = function() {
        return $cookies.get('isAdmin') === 'true';
    }

    if ($cookies.get('token')) {
        $http.defaults.headers.common.Authorization = 'Bearer ' + $cookies.get('token');
        $state.go('read');
    } else {
        $state.go('login');
    }
}]);

app.factory('tableService', function() {
    var service = {};
    service.booleanFields = ['gomCompliant', 'workflowFlag', 'anyBatchComponent'];
    var historyCellTemplate = '<div align="center"><a ng-click="grid.appScope.showHistoryModal(row.entity.id)"><img src="images/history-img.png" height="34" width="34"></a></div>';

    var xsmWidth = 90;
    var smWidth = 2 * xsmWidth;
    var mdWidth = 3 * xsmWidth;
    var lgWidth = 4 * xsmWidth;
    var xlgWidth = 4.5 * xsmWidth;
    
    service.cxColumnDefs = [
        {field: 'id', displayName: 'ID', width: xsmWidth, enableSorting: false, enableHiding: false},
        {field: 'csiId', displayName: 'CSI ID', width: xsmWidth, enableHiding: false},
        {field: 'csInstance', displayName: 'CS Instance', width: smWidth, enableHiding: false},
        {field: 'businessId', displayName: 'BUSINESS ID', width: smWidth, enableHiding: false},
        {field: 'bizUnitId', displayName: 'BIZ UNIT ID', width: smWidth, enableHiding: false},
        {field: 'productId', displayName: 'PRODUCT ID', width: smWidth, enableHiding: false},
        {field: 'bizProdId', displayName: 'BIZ PROD ID', width: smWidth, enableHiding: false},
        {field: 'cxScreeningBusinessUnitName', displayName: 'Cx Screening Business Unit Name', width: lgWidth, enableHiding: false},
        {field: 'cxBusinessGreenzone', displayName: 'Cx Business Greenzone', width: mdWidth, enableHiding: false},
        {field: 'rulesetMapped', displayName: 'Ruleset Mapped', width: smWidth, enableHiding: false},
        {field: 'region', displayName: 'Region', width: xsmWidth, enableHiding: false},
        {field: 'country', displayName: 'Country', width: smWidth, enableHiding: false},
        {field: 'sector', displayName: 'Sector', width: xsmWidth, enableHiding: false},
        {field: 'workflowFlag', displayName: 'Workflow Flag', width: smWidth, enableHiding: false},
        {field: 'workflowInstance', displayName: 'Workflow Instance', width: smWidth, enableHiding: false},
        {field: 'wfBusinessUnitNameDisplayValue', displayName: 'WF Business Unit Name Display Value', width: lgWidth, enableHiding: false},
        {field: 'wfBusinessGreenzone', displayName: 'WF Business Greenzone', width: mdWidth, enableHiding: false},
        {field: 'connectivityProtocol', displayName: 'Connectivity Protocol', width: mdWidth, enableHiding: false},
        {field: 'interfaceAppId', displayName: 'Interface App ID', width: smWidth, enableHiding: false},
        {field: 'interfaceApplicationName', displayName: 'Interface Application Name', width: mdWidth, enableHiding: false},
        {field: 'operationEntity', displayName: 'Operation Entity', width: smWidth, enableHiding: false},
        {field: 'opsComplianceContacts', displayName: 'Ops Compliance Contacts', width: mdWidth, enableHiding: false},
        {field: 'cwVersion', displayName: 'Cw V1/V2', width: 1.5 * xsmWidth, enableHiding: false},
        {field: 'gomCompliant', displayName: 'GOM Compliant?', width: smWidth, enableHiding: false},
        {field: 'cwUatContactName', displayName: 'Cw UAT Contact Name', width: mdWidth, enableHiding: false},
        {field: 'sourceTechContact', displayName: 'Source Tech Contact', width: mdWidth, enableHiding: false},
        {field: 'impactToBusiness', displayName: 'Impact To Business', width: mdWidth, enableHiding: false},
        {field: 'businessEscalationPointOfContact', displayName: 'Business Escalation Point of Contact', width: lgWidth, enableHiding: false},
        {field: 'timezone', displayName: 'Timezone', width: smWidth, enableHiding: false},
        {field: 'history', displayName: 'History', width: smWidth, enableHiding: false,
            cellTemplate: historyCellTemplate}
    ];
    
    service.txColumnDefs = [
        {field: 'id', displayName: 'ID', width: xsmWidth, enableSorting: false, enableHiding: false},
        {field: 'businessId', displayName: 'BUSINESS ID', width: smWidth, enableHiding: false},
        {field: 'productId', displayName: 'PRODUCT ID', width: smWidth, enableHiding: false},
        {field: 'csiId', displayName: 'CSI ID', width: xsmWidth, enableHiding: false},
        {field: 'uniqueProductId', displayName: 'Unique Product ID', width: smWidth, enableHiding: false},
        {field: 'txScreeningBusinessUnitName', displayName: 'Tx Screening Business Unit Name', width: lgWidth, enableHiding: false},
        {field: 'rulesetMapped', displayName: 'Ruleset Mapped', width: smWidth, enableHiding: false},
        {field: 'region', displayName: 'Region', width: xsmWidth, enableHiding: false},
        {field: 'country', displayName: 'Country', width: smWidth, enableHiding: false},
        {field: 'sector', displayName: 'Sector', width: xsmWidth, enableHiding: false},
        {field: 'workflowFlag', displayName: 'Workflow Flag', width: smWidth, enableHiding: false},
        {field: 'workflowInstance', displayName: 'Workflow Instance', width: smWidth, enableHiding: false},
        {field: 'wfBusinessGreenzone', displayName: 'WF Business Greenzone', width: mdWidth, enableHiding: false},
        {field: 'interfaceAppId', displayName: 'Interface App ID', width: smWidth, enableHiding: false},
        {field: 'interfaceApplicationName', displayName: 'Interface Application Name', width: mdWidth, enableHiding: false},
        {field: 'operationEntity', displayName: 'Operation Entity', width: smWidth, enableHiding: false},
        {field: 'connectivityProtocol', displayName: 'Connectivity Protocol', width: mdWidth, enableHiding: false},
        {field: 'workflowOperationsContacts', displayName: 'Workflow Operations Contacts', width: mdWidth, enableHiding: false},
        {field: 'sourceTechContacts', displayName: 'Source Tech Contacts', width: mdWidth, enableHiding: false},
        {field: 'businessHotline', displayName: 'Business Hotline', width: mdWidth, enableHiding: false},
        {field: 'businessEscalationPointOfContact', displayName: 'Business Escalation Point of Contact', width: lgWidth, enableHiding: false},
        {field: 'impactToProductProcessor', displayName: 'Impact to Product Processor', width: mdWidth, enableHiding: false},
        {field: 'productProcessor', displayName: 'Product Processor', width: smWidth, enableHiding: false},
        {field: 'hotlineNumber', displayName: 'Hotline Number', width: smWidth, enableHiding: false},
        {field: 'escalationPath1stLevelSupport', displayName: 'Escalation Path 1st Level Support', width: lgWidth, enableHiding: false},
        {field: 'escalationPath2ndLevelSupport', displayName: 'Escalation Path 2nd Level Support', width: lgWidth, enableHiding: false},
        {field: 'firstLevelEscalation', displayName: '1st Level Escalation', width: mdWidth, enableHiding: false},
        {field: 'secondLevelEscalation', displayName: '2nd Level Escalation', width: mdWidth, enableHiding: false},
        {field: 'productProcessorGroupDl', displayName: 'Product Processor Group DL', width: mdWidth, enableHiding: false},
        {field: 'productProcessorSnowGroupName', displayName: 'Product Processor SNOW Group Name', width: lgWidth, enableHiding: false},
        {field: 'productProcessorScreeningResponseCutoffTime', displayName: 'Product Processor Screening Response Cutoff Time', width: xlgWidth, enableHiding: false},
        {field: 'productProcessorStandardGreenzones', displayName: 'Product Processor Standard Greenzones', width: lgWidth, enableHiding: false},
        {field: 'interfaceConnectivityDoc', displayName: 'Interface Connectivity Doc', width: mdWidth, enableHiding: false},
        {field: 'retryMechanism', displayName: 'Retry Mechanism', width: smWidth, enableHiding: false},
        {field: 'dailyOnlineVolumesExpected', displayName: 'Daily Online Volumes Expected', width: mdWidth, enableHiding: false},
        {field: 'scheduleForRealtimeVolumes', displayName: 'Schedule For Realtime Volumes', width: mdWidth, enableHiding: false},
        {field: 'batchesOrPeaksForRealtimeVolumes', displayName: 'Batches or Peaks for Realtime Volumes', width: lgWidth, enableHiding: false},
        {field: 'initialScreeningResponseSla', displayName: 'Initial Screening Response SLA', width: mdWidth, enableHiding: false},
        {field: 'thresholdSetForTimeouts', displayName: 'Threshold Set for Timeouts', width: mdWidth, enableHiding: false},
        {field: 'anyBatchComponent', displayName: 'Any Batch Component?', width: mdWidth, enableHiding: false},
        {field: 'workflowOperationsWorkSchedule', displayName: 'Workflow Operations Work Schedule', width: lgWidth, enableHiding: false},
        {field: 'history', displayName: 'History', width: smWidth, enableHiding: false,
            cellTemplate: historyCellTemplate}
    ];

    function convertBooleanToChar(boolean) {
        return boolean ? 'Y' : 'N';
    }

    service.convertCxData = function (record, isRecent) {
        var cxData = {
            id: isRecent ? record[0] : record.id
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
            cxData.gomCompliant = convertBooleanToChar(record[13]);
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
            cxData.workflowFlag = convertBooleanToChar(record[28]);
            cxData.workflowInstance = record[29];
        } else {
            angular.forEach(record.buDetails, function (value, key) {
                cxData[key] = value;
            });
        }
        
        return cxData;
    };
    
    service.convertTxData = function (record, isRecent) {
        var txData = {
            id: isRecent ? record[0] : record.id
        };
        
        if (isRecent) {
            txData.anyBatchComponent = convertBooleanToChar(record[1]);
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
            txData.workflowFlag = convertBooleanToChar(record[38]);
            txData.workflowInstance = record[39];
            txData.workflowOperationsContacts = record[40];
            txData.workflowOperationsWorkSchedule = record[41];
        } else {
            angular.forEach(record.buDetails, function (value, key) {
                txData[key] = value;
            });
        }
        
        return txData;
    };

    return service;
});