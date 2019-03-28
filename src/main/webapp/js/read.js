app.controller('ReadController', ['$scope', '$http', function ($scope, $http) {
    $scope.search = function() {
        var url = '/read?recordType=';
        $http.get(url + $scope.recordType + '&searchTerms=' + $scope.searchTerms).then(function (response) {
            console.log(response);
        }, function (error) {
            console.log(error);
        });
    }

    /*function genRows(num){
        var retVal = [];
        for (var i=0; i < num; i++) {
            retVal.push(genRow(i));
        }
        return retVal;
    }*/

    // Generates a row with random data
    /*function genRow(id){

        var fnames = ['joe','fred','frank','jim','mike','gary','aziz'];
        var lnames = ['sterling','smith','erickson','burke','ansari'];
        var seed = Math.random();
        var seed2 = Math.random();
        var first_name = fnames[ Math.round( seed * (fnames.length -1) ) ];
        var last_name = lnames[ Math.round( seed * (lnames.length -1) ) ];

        return {
            id: id,
            selected: false,
            first_name: first_name,
            last_name: last_name,
            age: Math.ceil(seed * 75) + 15,
            height: Math.round( seed2 * 36 ) + 48,
            weight: Math.round( seed2 * 130 ) + 90,
            likes: Math.round(seed2 * seed * 1000000)
        };
    }*/

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
        { id: 'csi_id', key: 'csi_id', label: 'CSI ID', sort: 'string', filter: 'like' },
        { id: 'cs_instance', key: 'cs_instance', label: 'CS Instance', sort: 'string', filter: 'like' },
        { id: 'business_id', key: 'business_id', label: 'BUSINESS ID', sort: 'string', filter: 'like' },
        { id: 'biz_unit_id', key: 'biz_unit_id', label: 'BIZ_UNIT_ID', sort: 'string', filter: 'like' },
        { id: 'product_id', key: 'product_id', label: 'PRODUCT_ID', sort: 'string', filter: 'like' },
        { id: 'biz_prod_id', key: 'biz_prod_id', label: 'BIZ_PROD_ID', sort: 'string', filter: 'like'},
        { id: 'cx_screening_business_unit_name', key: 'cx_screening_business_unit_name', label: 'Cx Screening Business Unit Name', sort: 'string', filter: 'like'},
        { id: 'cx_business_greenzone', key: 'cx_business_greenzone', label: 'Cx Business Greenzone', sort: 'string', filter: 'like'}/*,
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
        { id: 'requestor', key: 'requestor', label: 'Requestor', sort: 'string', filter: 'like' }*/
    ];

    // Table data
    /*$scope.my_table_data = genRows(100);*/


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