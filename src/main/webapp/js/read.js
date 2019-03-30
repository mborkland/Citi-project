app.controller('ReadController', ['$scope', '$http', function ($scope, $http) {
    $scope.search = function() {
        var url = '/read?recordType=';
        $http.get(url + $scope.recordType + '&searchTerms=' + $scope.searchTerms).then(function (response) {
            console.log(response);
            let list = [];
            for (let i=0; i < response.data.length; i++) {
                list.push(genCxRow(response.data[i]));
            }
            $scope.my_table_data = list;
        }, function (error) {
            console.log(error);
        });
    };

    /*function genCxRows(){
        var retVal = [];
        for (var i=0; i < $scope.results.data.length; i++) {
            retVal.push(genCxRow($scope.results.data[i]));
        }
        return retVal;
    }*/

    // Generates a row with random data

    function genCxRow(record){
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

    function genTxRow(record) {
        return {
            any_batch_component: record.buDetails, //todo
            batches_or_peaks_for_realtime_volumes: record.buDetails,
            business_escalation_point_of_contact: record.buDetails.businessEscalationPointOfContact,
            business_hotline: record.buDetails,
            business_id: record.buDetails.businessId,
            connectivity_protocol: record.buDetails.connectivityProtocol,
            //Add in the contacts checked, wherever that may be,
            country: record.buDetails.country,
            cs_instance: record.buDetails.csInstance,
            csi_id: record.buDetails.csiId,
            daily_online_volumes_expected: record.buDetails,
            escalation_path_1st_level_support: record.buDetails,
            escalation_path_2nd_level_support: record.buDetails,
            first_level_escalation: record.buDetails,
            hotline_number: record.buDetails,
            impact_to_product_processor: record.buDetails,
            initial_screening_response_sla: record.buDetails,
            interface_app_id: record.buDetails.interfaceAppId,
            interface_application_name: record.buDetails.interfaceApplicationName,
            interface_connectivity_doc: record.buDetails,
            operation_entity: record.buDetails.operationEntity,
            product_id: record.buDetails.productId,
            product_processor: record.buDetails,
            product_processor_group_dl: record.buDetails,
            product_processor_screening_response_cutoff_time: record.buDetails,
            product_processor_snow_group_name: record.buDetails,
            product_processor_standard_greenzones: record.buDetails,
            region: record.buDetails.region,
            retry_mechanism: record.buDetails,
            ruleset_mapped: record.buDetails.rulesetMapped,
            schedule_for_realtime_volumes: record.buDetails,
            sector: record.buDetails.sector,
            source_tech_contacts: record.buDetails,
            threshold_set_for_timeouts: record.buDetails,
            tx_screening_business_unit_name: record.buDetails,
            unique_product_id: record.buDetails,
            update_history: record.buDetails.updateHistory,
            wf_business_greenzone: record.buDetails.wfBusinessGreenZone,
            workflow_flag: record.buDetails.workflowFlag,
            workflow_instance: record.buDetails.workflowInstance,
            workflow_operations_contacts: record.buDetails,
            workflow_operations_work_schedule: record.buDetails
        }
    }
    /*$scope.my_table_columns = [
        { id: 'selected', key: 'id', label: '', width: 30, lockWidth: true, selector: true },
        //{ id: 'selected', key: 'id', label: '', width: 30, lockWidth: true, selector: true, selectObject: true },
        { id: 'ID', key: 'id', label: 'id', sort: 'number', filter: 'number' },
        { id: 'first_name', key: 'first_name', label: 'First Name', sort: 'string', filter: 'like', template: '<strong>{{row[column.key]}}</strong>' },
        { id: 'last_name', key: 'last_name', label: 'Last Name', sort: 'string', filter: 'like' },
        { id: 'age', key: 'age', label: 'Age', sort: 'number', filter: 'number' },
        { id: 'height', key: 'height', label: 'Height', filter: 'number', sort: 'number' },
    ];*/

    //TODO: find the js datatype for: workflow_flag, cw_version, gom_compliant
    $scope.my_table_columns = [
        { id: 'selected', key: 'id', label: '', width: 30, lockWidth: true, selector: true },
        { id: 'csi_id', key: 'csi_id', label: 'CSI ID [test]', sort: 'string', filter: 'like', template: '<strong>{{row[column.key]}}</strong>' },
        { id: 'cs_instance', key: 'cs_instance', label: 'CS Instance', sort: 'string', filter: 'like' },
        { id: 'business_id', key: 'business_id', label: 'BUSINESS ID', sort: 'string', filter: 'like' },
        { id: 'biz_unit_id', key: 'biz_unit_id', label: 'BIZ_UNIT_ID', sort: 'string', filter: 'like' },
        { id: 'product_id', key: 'product_id', label: 'PRODUCT_ID', sort: 'string', filter: 'like' },
        { id: 'biz_prod_id', key: 'biz_prod_id', label: 'BIZ_PROD_ID', sort: 'string', filter: 'like'},
        { id: 'cx_screening_business_unit_name', key: 'cx_screening_business_unit_name', label: 'Cx Screening Business Unit Name', sort: 'string', filter: 'like'},
        { id: 'cx_business_greenzone', key: 'cx_business_greenzone', label: 'Cx Business Greenzone', sort: 'string', filter: 'like'},
        { id: 'ruleset_mapped', key: 'ruleset_mapped', label: 'Ruleset Mapped', sort: 'string', filter: 'like'},
        { id: 'region', key: 'region', label: 'Region', sort: 'string', filter: 'like'},
        { id: 'country', key: 'country', label: 'Country', sort: 'string', filter: 'like'},
        { id: 'sector', key: 'sector', label: 'Sector', sort: 'string', filter: 'like'},
        { id: 'workflow_flag', key: 'workflow_flag', label: 'Workflow Flag', sort: 'string', filter: 'like'},
        { id: 'workflow_instance', key: 'workflow_instance', label: 'Workflow Instance', sort: 'string', filter: 'like'},
        { id: 'wf_business_unit_name_display_value', key: 'wf_business_unit_name_display_value', label: 'WF Business Unit Name Display Value', sort: 'string', filter: 'like'},
        { id: 'wf_business_greenzone', key: 'wf_business_greenzone', label: 'WF Business Greenzone', sort: 'string', filter: 'like'},
        { id: 'connectivity_protocol', key: 'connectivity_protocol', label: 'Connectivity Protocol', sort: 'string', filter: 'like'},
        { id: 'interface_app_id', key: 'interface_app_id', label: 'Interface App ID', sort: 'string', filter: 'like'},
        { id: 'interface_application_name', key: 'interface_application_name', label: 'Interface Application Name', sort: 'string', filter: 'like' },
        { id: 'operation_entity', key: 'operation_entity', label: 'Operation Entity', sort: 'string', filter: 'like' },
        { id: 'ops_compliance_contacts', key: 'ops_compliance_contacts', label: 'Ops Compliance Contacts', sort: 'string', filter: 'like' },
        { id: 'cw_version', key: 'cw_version', label: 'Cw Version', sort: 'string', filter: 'like' },
        { id: 'gom_compliant', key: 'gom_compliant', label: 'GOM Compliant?', sort: 'string', filter: 'like' },
        { id: 'cw_uat_contact_name', key: 'cw_uat_contact_name', label: 'Cw UAT Contact Name', sort: 'string', filter: 'like' },
        { id: 'source_tech_contact', key: 'source_tech_contact', label: 'Source Tech Contact', sort: 'string', filter: 'like' },
        { id: 'impact_to_business', key: 'impact_to_business', label: 'Impact To Business', sort: 'string', filter: 'like' },
        { id: 'business_escalation_point_of_contact', key: 'business_escalation_point_of_contact', label: 'Business Escalation Point Of Contact', sort: 'string', filter: 'like' },
        { id: 'timezone', key: 'timezone', label: 'Timezone', sort: 'string', filter: 'like' },
        { id: 'update_history', key: 'update_history', label: 'Update History', sort: 'string', filter: 'like' }
    ];

    /*$scope.p-3 = {
        padding: $spacer;
    }*/



    // Selected rows
    $scope.my_selected_rows = [];

    // table options
    $scope.my_table_options = {
        rowLimit: 10
    };
    $scope.my_table_options_paginate = angular.extend({}, $scope.my_table_options, {
        pagingStrategy: 'PAGINATE',
        rowsPerPage: 8
    });
}]);