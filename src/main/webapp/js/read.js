app.controller('ReadController', ['$scope', '$http', 'uiGridConstants',
function ($scope, $http, uiGridConstants) {

    $scope.gridOptions1 = {
        enableSorting: true,
        columnDefs: [
            { field: 'name' },
            { field: 'gender' },
            { field: 'company', enableSorting: false }
        ],
        onRegisterApi: function( gridApi ) {
            this.grid1Api = gridApi;
        },
        data: [{name: 'Ethel', gender: 'Female', company: 'Citi'},
               {name: 'Mike', gender: 'Male', company: 'Citi'}]
    };




    $scope.search = function() {
        var url = '/read?recordType=';
        $http.get(url + $scope.recordType + '&searchTerms=' + $scope.searchTerms).then(function (response) {
            console.log(response);
        }, function (error) {
            console.log(error);
        });
    };

    /*function getCxRow(record){
        return {
            biz_prod_id: record.buDetails.bizProdId,
            biz_unit_id: record.buDetails.bizUnitId,
            business_escalation_point_of_contact: record.buDetails.businessEscalationPointOfContact,
            business_id: record.buDetails.businessId,
            connectivity_protocol: record.buDetails.connectivityProtocol,
            //Add in the contacts checked, wherever that may be,
            country: record.buDetails.country,
            cs_instance: record.buDetails.csInstance,
            csi_id: record.buDetails.csiId,
            cw_uat_contact_name: record.buDetails.cwUatContactName,
            cw_version: record.buDetails.cwVersion,
            cx_business_greenzone: record.buDetails.cxBusinessGreenzone,
            cx_screening_business_unit_name: record.buDetails.cxScreeningBusinessUnitName,
            gom_compliant: record.buDetails.gomCompliant,
            impact_to_business: record.buDetails.impactToBusiness,
            interface_app_id: record.buDetails.interfaceAppId,
            interface_application_name: record.buDetails.interfaceApplicationName,
            operation_entity: record.buDetails.operationEntity,
            ops_compliance_contacts: record.buDetails.opsComplianceContacts,
            product_id: record.buDetails.productId,
            region: record.buDetails.region,
            ruleset_mapped: record.buDetails.rulesetMapped,
            sector: record.buDetails.sector,
            source_tech_contact: record.buDetails.sourceTechContact,
            timezone: record.buDetails.timezone,
            update_history: record.buDetails.updateHistory,
            wf_business_greenzone: record.buDetails.wfBusinessGreenZone,
            wf_business_unit_name_display_value: record.buDetails.wfBusinessUnitNameDisplayValue,
            workflow_flag: record.buDetails.workflowFlag,
            workflow_instance: record.buDetails.workflowInstance
        };
    }

    function getTxRow(record) {
        return {
            any_batch_component: record.buDetails.anyBatchComponent, //todo
            batches_or_peaks_for_realtime_volumes: record.buDetails.batchesOrPeaksForRealtimeVolumes,
            business_escalation_point_of_contact: record.buDetails.businessEscalationPointOfContact,
            business_hotline: record.buDetails.businessHotline,
            business_id: record.buDetails.businessId,
            connectivity_protocol: record.buDetails.connectivityProtocol,
            //Add in the contacts checked, wherever that may be,
            country: record.buDetails.country,
            cs_instance: record.buDetails.csInstance,
            csi_id: record.buDetails.csiId,
            daily_online_volumes_expected: record.buDetails.dailyOnlineVolumesExpected,
            escalation_path_1st_level_support: record.buDetails.escalationPath1stLevelSupport,
            escalation_path_2nd_level_support: record.buDetails.escalationPath2ndLevelSupport,
            first_level_escalation: record.buDetails.firstLevelEscalation,
            hotline_number: record.buDetails.hotlineNumber,
            impact_to_product_processor: record.buDetails.impactToProductProcessor,
            initial_screening_response_sla: record.buDetails.initialScreeningResponseSla,
            interface_app_id: record.buDetails.interfaceAppId,
            interface_application_name: record.buDetails.interfaceApplicationName,
            interface_connectivity_doc: record.buDetails.interfaceConnectivityDoc,
            operation_entity: record.buDetails.operationEntity,
            product_id: record.buDetails.productId,
            product_processor: record.buDetails.productProcessor,
            product_processor_group_dl: record.buDetails.productProcessorGroupDl,
            product_processor_screening_response_cutoff_time: record.buDetails.productProcessorScreeningResponseCutoffTime,
            product_processor_snow_group_name: record.buDetails.productProcessorSnowGroupName,
            product_processor_standard_greenzones: record.buDetails.productProcessorStandardGreenzones,
            region: record.buDetails.region,
            retry_mechanism: record.buDetails.retryMechanism,
            ruleset_mapped: record.buDetails.rulesetMapped,
            schedule_for_realtime_volumes: record.buDetails.scheduleForRealtimeVolumes,
            sector: record.buDetails.sector,
            source_tech_contacts: record.buDetails.sourceTechContacts,
            threshold_set_for_timeouts: record.buDetails.thresholdSetForTimeouts,
            tx_screening_business_unit_name: record.buDetails.txScreeningBusinessUnitName,
            unique_product_id: record.buDetails.uniqueProductId,
            update_history: record.buDetails.updateHistory,
            wf_business_greenzone: record.buDetails.wfBusinessGreenZone,
            workflow_flag: record.buDetails.workflowFlag,
            workflow_instance: record.buDetails.workflowInstance,
            workflow_operations_contacts: record.buDetails.workflowOperationsContacts,
            workflow_operations_work_schedule: record.buDetails.workflowOperationsWorkSchedule
        }
    }*/

    /*TODO: find the js datatype for: workflow_flag, cw_version, gom_compliant
    $scope.cx_table_columns = [
        { id: 'selected', key: 'id', label: '', width: 30, lockWidth: true, selector: true },
        { id: 'csi_id',lockWidth: true, key: 'csi_id', label: 'CSI ID [test]', sort: 'string', filter: 'like', template: '<strong>{{row[column.key]}}</strong>' },
        { id: 'cs_instance',lockWidth: true, key: 'cs_instance', label: 'CS Instance', sort: 'string', filter: 'like' },
        { id: 'business_id',lockWidth: true, key: 'business_id', label: 'BUSINESS ID', sort: 'string', filter: 'like' },
        { id: 'biz_unit_id',lockWidth: true, key: 'biz_unit_id', label: 'BIZ_UNIT_ID', sort: 'string', filter: 'like' },
        { id: 'product_id',lockWidth: true, key: 'product_id', label: 'PRODUCT_ID', sort: 'string', filter: 'like' },
        { id: 'biz_prod_id',lockWidth: true, key: 'biz_prod_id', label: 'BIZ_PROD_ID', sort: 'string', filter: 'like'},
        { id: 'cx_screening_business_unit_name',lockWidth: true, key: 'cx_screening_business_unit_name', label: 'Cx Screening Business Unit Name', sort: 'string', filter: 'like'},
        { id: 'cx_business_greenzone',lockWidth: true, key: 'cx_business_greenzone', label: 'Cx Business Greenzone', sort: 'string', filter: 'like'},
        { id: 'ruleset_mapped',lockWidth: true, key: 'ruleset_mapped', label: 'Ruleset Mapped', sort: 'string', filter: 'like'},
        { id: 'region',lockWidth: true, key: 'region', label: 'Region', sort: 'string', filter: 'like'},
        { id: 'country',lockWidth: true, key: 'country', label: 'Country', sort: 'string', filter: 'like'},
        { id: 'sector',lockWidth: true, key: 'sector', label: 'Sector', sort: 'string', filter: 'like'},
        { id: 'workflow_flag',lockWidth: true, key: 'workflow_flag', label: 'Workflow Flag', sort: 'string', filter: 'like'},
        { id: 'workflow_instance',lockWidth: true, key: 'workflow_instance', label: 'Workflow Instance', sort: 'string', filter: 'like'},
        { id: 'wf_business_unit_name_display_value',lockWidth: true, key: 'wf_business_unit_name_display_value', label: 'WF Business Unit Name Display Value', sort: 'string', filter: 'like'},
        { id: 'wf_business_greenzone',lockWidth: true, key: 'wf_business_greenzone', label: 'WF Business Greenzone', sort: 'string', filter: 'like'},
        { id: 'connectivity_protocol',lockWidth: true, key: 'connectivity_protocol', label: 'Connectivity Protocol', sort: 'string', filter: 'like'},
        { id: 'interface_app_id',lockWidth: true, key: 'interface_app_id', label: 'Interface App ID', sort: 'string', filter: 'like'},
        { id: 'interface_application_name',lockWidth: true, key: 'interface_application_name', label: 'Interface Application Name', sort: 'string', filter: 'like' },
        { id: 'operation_entity',lockWidth: true, key: 'operation_entity', label: 'Operation Entity', sort: 'string', filter: 'like' },
        { id: 'ops_compliance_contacts',lockWidth: true, key: 'ops_compliance_contacts', label: 'Ops Compliance Contacts', sort: 'string', filter: 'like' },
        { id: 'cw_version',lockWidth: true, key: 'cw_version', label: 'Cw Version', sort: 'string', filter: 'like' },
        { id: 'gom_compliant',lockWidth: true, key: 'gom_compliant', label: 'GOM Compliant?', sort: 'string', filter: 'like' },
        { id: 'cw_uat_contact_name',lockWidth: true, key: 'cw_uat_contact_name', label: 'Cw UAT Contact Name', sort: 'string', filter: 'like' },
        { id: 'source_tech_contact',lockWidth: true, key: 'source_tech_contact', label: 'Source Tech Contact', sort: 'string', filter: 'like' },
        { id: 'impact_to_business',lockWidth: true, key: 'impact_to_business', label: 'Impact To Business', sort: 'string', filter: 'like' },
        { id: 'business_escalation_point_of_contact',lockWidth: true, key: 'business_escalation_point_of_contact', label: 'Business Escalation Point Of Contact', sort: 'string', filter: 'like' },
        { id: 'timezone', lockWidth: true,key: 'timezone', label: 'Timezone', sort: 'string', filter: 'like' },
        { id: 'update_history', lockWidth: true,key: 'update_history', label: 'Update History', sort: 'string', filter: 'like' }
    ];

    $scope.tx_table_columns = [
        { id: 'selected', key: 'id', label: '', width: 30, lockWidth: true, selector: true },
        { id: 'csi_id', key: 'csi_id', label: 'CSI ID [test]', sort: 'string', filter: 'like',lockWidth: true, template: '<strong>{{row[column.key]}}</strong>' },
        { id: 'any_batch_component', key: 'any_batch_component', label: 'Any Batch Component',lockWidth: true, sort: 'string', filter: 'like' },
        { id: 'batches_or_peaks_for_realtime_volumes', key: 'batches_or_peaks_for_realtime_volumes',lockWidth: true, label: 'Batches or Peaks for Realtime Volumes', sort: 'string', filter: 'like' },
        { id: 'business_escalation_point_of_contact', key: 'business_escalation_point_of_contact',lockWidth: true, label: 'Business Escalation Point of Contact', sort: 'string', filter: 'like' },
        { id: 'business_hotline', key: 'business_hotline', label: 'Business Hotline', sort: 'string',lockWidth: true, filter: 'like' },
        { id: 'business_id', key: 'business_id', label: 'Business ID', sort: 'string',lockWidth: true, filter: 'like' },
        { id: 'connectivity_protocol', key: 'connectivity_protocol', label: 'Connectivity Protocol',lockWidth: true, sort: 'string', filter: 'like' },
        { id: 'contacts_checked', key: 'contacts_checked', label: 'Contacts Checked', sort: 'string',lockWidth: true, filter: 'like' },
        { id: 'country', key: 'country', label: 'Country', sort: 'string',lockWidth: true, filter: 'like' },
        { id: 'daily_online_volumes_expected',lockWidth: true, key: 'daily_online_volumes_expected', label: 'Daily Online Volumes Expected', sort: 'string', filter: 'like' },
        { id: 'escalation_path_1st_level_support',lockWidth: true, key: 'escalation_path_1st_level_support', label: 'Escalation Path 1st Level Support', sort: 'string', filter: 'like' },
        { id: 'escalation_path_2nd_level_support',lockWidth: true, key: 'escalation_path_2nd_level_support', label: 'Escalation Path 2nd Level Support', sort: 'string', filter: 'like' },
        { id: 'first_level_escalation',lockWidth: true, key: 'first_level_escalation', label: '1st Level Escalation', sort: 'string', filter: 'like' },
        { id: 'hotline_number',lockWidth: true, key: 'hotline_number', label: 'Hotline Number', sort: 'string', filter: 'like' },
        { id: 'impact_to_product_processor',lockWidth: true, key: 'impact_to_product_processor', label: 'Impact to Product Processor', sort: 'string', filter: 'like' },
        { id: 'initial_screening_response_sla',lockWidth: true, key: 'initial_screening_response_sla', label: 'Initial Screening Response SLA', sort: 'string', filter: 'like' },
        { id: 'interface_app_id',lockWidth: true, key: 'interface_app_id', label: 'Interface App ID', sort: 'string', filter: 'like' },
        { id: 'interface_application_name',lockWidth: true, key: 'interface_application_name', label: 'Interface Application Name', sort: 'string', filter: 'like' },
        { id: 'interface_connectivity_doc',lockWidth: true, key: 'interface_connectivity_doc', label: 'Interface Connectivity Doc', sort: 'string', filter: 'like' },
        { id: 'operation_entity',lockWidth: true, key: 'operation_entity', label: 'Operation Entity', sort: 'string', filter: 'like' },
        { id: 'product_id',lockWidth: true, key: 'product_id', label: 'Product ID', sort: 'string', filter: 'like' },
        { id: 'product_processor',lockWidth: true, key: 'product_processor', label: 'Product Processor', sort: 'string', filter: 'like' },
        { id: 'product_processor_group_dl',lockWidth: true, key: 'product_processor_group_dl', label: 'Product Processor Group DL', sort: 'string', filter: 'like' },
        { id: 'product_processor_screening_response_cutoff_time',lockWidth: true, key: 'product_processor_screening_response_cutoff_time', label: 'Product Processor Screening Response Cutoff Time', sort: 'string', filter: 'like' },
        { id: 'product_processor_snow_group_name',lockWidth: true, key: 'product_processor_snow_group_name', label: 'Product Processor Snow Group Name', sort: 'string', filter: 'like' },
        { id: 'product_processor_standard_greenzones',lockWidth: true, key: 'product_processor_standard_greenzones', label: 'Product Processor Standard Greenzones', sort: 'string', filter: 'like' },
        { id: 'region', key: 'region', label: 'Region',lockWidth: true, sort: 'string', filter: 'like' },
        { id: 'retry_mechanism', key: 'retry_mechanism',lockWidth: true, label: 'Retry Mechanism', sort: 'string', filter: 'like' },
        { id: 'ruleset_mapped', key: 'ruleset_mapped',lockWidth: true, label: 'Ruleset Mapped', sort: 'string', filter: 'like' },
        { id: 'schedule_for_realtime_volumes',lockWidth: true, key: 'schedule_for_realtime_volumes', label: 'Schedule for Realtime Volumes', sort: 'string', filter: 'like' },
        { id: 'second_level_escalation',lockWidth: true, key: 'second_level_escalation', label: 'Second Level Escalation', sort: 'string', filter: 'like' },
        { id: 'sector', key: 'sector',lockWidth: true, label: 'Sector', sort: 'string', filter: 'like' },
        { id: 'source_tech_contacts',lockWidth: true, key: 'source_tech_contacts', label: 'Source Tech Contacts', sort: 'string', filter: 'like' },
        { id: 'threshold_set_for_timeouts',lockWidth: true, key: 'threshold_set_for_timeouts', label: 'Threshold set for Timeouts', sort: 'string', filter: 'like' },
        { id: 'tx_screening_business_unit_name',lockWidth: true, key: 'tx_screening_business_unit_name', label: 'TX Screening Business Unit Name', sort: 'string', filter: 'like' },
        { id: 'unique_product_id',lockWidth: true, key: 'unique_product_id', label: 'Unique Product ID', sort: 'string', filter: 'like' },
        { id: 'update_history',lockWidth: true, key: 'update_history', label: 'Update History', sort: 'string', filter: 'like' },
        { id: 'wf_business_greenzone',lockWidth: true, key: 'wf_business_greenzone', label: 'WF Business Greenzone', sort: 'string', filter: 'like' },
        { id: 'workflow_flag',lockWidth: true, key: 'workflow_flag', label: 'Workflow Flag', sort: 'string', filter: 'like' },
        { id: 'workflow_instance',lockWidth: true, key: 'workflow_instance', label: 'Workflow Instance', sort: 'string', filter: 'like' },
        { id: 'workflow_operations_contacts',lockWidth: true, key: 'workflow_operations_contacts', label: 'Workflow Operations Contacts', sort: 'string', filter: 'like' },
        { id: 'workflow_operations_work_schedule',lockWidth: true, key: 'workflow_operations_work_schedule', label: 'Workflow Operations Work Schedule', sort: 'string', filter: 'like' }
    ];*/
}]);