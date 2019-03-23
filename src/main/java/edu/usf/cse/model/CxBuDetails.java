package edu.usf.cse.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CxBuDetails implements BuDetails {

    @Column(name="CSI_ID")
    private Integer csiId;

    @Column(name="CS_Instance")
    private String csInstance;

    @Column(name="BUSINESS_ID")
    private String businessId;

    @Column(name="BIZ_UNIT_ID")
    private String bizUnitId;

    @Column(name="PRODUCT_ID")
    private String productId;

    @Column(name="BIZ_PROD_ID")
    private String bizProdId;

    @Column(name="Cx_Screening_Business_Unit_Name")
    private String cxScreeningBusinessUnitName;

    @Column(name="Cx_Business_Greenzone")
    private String cxBusinessGreenzone;

    @Column(name="Ruleset_Mapped")
    private String rulesetMapped;

    @Column(name="REGION")
    private String region;

    @Column(name="COUNTRY")
    private String country;

    @Column(name="SECTOR")
    private String sector;

    @Column(name="Workflow_Flag")
    private Boolean workflowFlag;

    @Column(name="Workflow_Instance")
    private String workflowInstance;

    @Column(name="WF_Business_Unit_Name_Display_Value")
    private String wfBusinessUnitNameDisplayValue;

    @Column(name="WF_Business_Greenzone")
    private String wfBusinessGreenzone;

    @Column(name="Connectivity_Protocol")
    private String connectivityProtocol;

    @Column(name="Interface_App_ID")
    private Integer interfaceAppId;

    @Column(name="Interface_Application_Name")
    private String interfaceApplicationName;

    @Column(name="Operation_Entity")
    private String operationEntity;

    @Column(name="Ops_Compliance_Contacts")
    private String opsComplianceContacts;

    @Column(name="Cw_V1_V2")
    private String cwVersion;

    @Column(name="GOM_Compliant")
    private Boolean gomCompliant;

    @Column(name="Cw_UAT_Contact_Name")
    private String cwUatContactName;

    @Column(name="Source_Tech_Contact")
    private String sourceTechContact;

    @Column(name="Impact_to_Business")
    private String impactToBusiness;

    @Column(name="Business_Escalation_Point_of_Contact")
    private String businessEscalationPointOfContact;

    @Column(name="Timezone")
    private String timezone;

    @Column(name="Contacts_Checked")
    private Boolean contactsChecked;

    @Column(name="Update_History")
    private String updateHistory;

    public Integer getCsiId() {
        return csiId;
    }

    public void setCsiId(Integer csiId) {
        this.csiId = csiId;
    }

    public String getCsInstance() {
        return csInstance;
    }

    public void setCsInstance(String csInstance) {
        this.csInstance = csInstance;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getBizUnitId() {
        return bizUnitId;
    }

    public void setBizUnitId(String bizUnitId) {
        this.bizUnitId = bizUnitId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getBizProdId() {
        return bizProdId;
    }

    public void setBizProdId(String bizProdId) {
        this.bizProdId = bizProdId;
    }

    public String getCxScreeningBusinessUnitName() {
        return cxScreeningBusinessUnitName;
    }

    public void setCxScreeningBusinessUnitName(String cxScreeningBusinessUnitName) {
        this.cxScreeningBusinessUnitName = cxScreeningBusinessUnitName;
    }

    public String getCxBusinessGreenzone() {
        return cxBusinessGreenzone;
    }

    public void setCxBusinessGreenzone(String cxBusinessGreenzone) {
        this.cxBusinessGreenzone = cxBusinessGreenzone;
    }

    public String getRulesetMapped() {
        return rulesetMapped;
    }

    public void setRulesetMapped(String rulesetMapped) {
        this.rulesetMapped = rulesetMapped;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public Boolean getWorkflowFlag() {
        return workflowFlag;
    }

    public void setWorkflowFlag(Boolean workflowFlag) {
        this.workflowFlag = workflowFlag;
    }

    public String getWorkflowInstance() {
        return workflowInstance;
    }

    public void setWorkflowInstance(String workflowInstance) {
        this.workflowInstance = workflowInstance;
    }

    public String getWfBusinessUnitNameDisplayValue() {
        return wfBusinessUnitNameDisplayValue;
    }

    public void setWfBusinessUnitNameDisplayValue(String wfBusinessUnitNameDisplayValue) {
        this.wfBusinessUnitNameDisplayValue = wfBusinessUnitNameDisplayValue;
    }

    public String getWfBusinessGreenzone() {
        return wfBusinessGreenzone;
    }

    public void setWfBusinessGreenzone(String wfBusinessGreenzone) {
        this.wfBusinessGreenzone = wfBusinessGreenzone;
    }

    public String getConnectivityProtocol() {
        return connectivityProtocol;
    }

    public void setConnectivityProtocol(String connectivityProtocol) {
        this.connectivityProtocol = connectivityProtocol;
    }

    public Integer getInterfaceAppId() {
        return interfaceAppId;
    }

    public void setInterfaceAppId(Integer interfaceAppId) {
        this.interfaceAppId = interfaceAppId;
    }

    public String getInterfaceApplicationName() {
        return interfaceApplicationName;
    }

    public void setInterfaceApplicationName(String interfaceApplicationName) {
        this.interfaceApplicationName = interfaceApplicationName;
    }

    public String getOperationEntity() {
        return operationEntity;
    }

    public void setOperationEntity(String operationEntity) {
        this.operationEntity = operationEntity;
    }

    public String getOpsComplianceContacts() {
        return opsComplianceContacts;
    }

    public void setOpsComplianceContacts(String opsComplianceContacts) {
        this.opsComplianceContacts = opsComplianceContacts;
    }

    public String getCwVersion() {
        return cwVersion;
    }

    public void setCwVersion(String cwVersion) {
        this.cwVersion = cwVersion;
    }

    public Boolean getGomCompliant() {
        return gomCompliant;
    }

    public void setGomCompliant(Boolean gomCompliant) {
        this.gomCompliant = gomCompliant;
    }

    public String getCwUatContactName() {
        return cwUatContactName;
    }

    public void setCwUatContactName(String cwUatContactName) {
        this.cwUatContactName = cwUatContactName;
    }

    public String getSourceTechContact() {
        return sourceTechContact;
    }

    public void setSourceTechContact(String sourceTechContact) {
        this.sourceTechContact = sourceTechContact;
    }

    public String getImpactToBusiness() {
        return impactToBusiness;
    }

    public void setImpactToBusiness(String impactToBusiness) {
        this.impactToBusiness = impactToBusiness;
    }

    public String getBusinessEscalationPointOfContact() {
        return businessEscalationPointOfContact;
    }

    public void setBusinessEscalationPointOfContact(String businessEscalationPointOfContact) {
        this.businessEscalationPointOfContact = businessEscalationPointOfContact;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Boolean getContactsChecked() {
        return contactsChecked;
    }

    public void setContactsChecked(Boolean contactsChecked) {
        this.contactsChecked = contactsChecked;
    }

    public String getUpdateHistory() {
        return updateHistory;
    }

    public void setUpdateHistory(String updateHistory) {
        this.updateHistory = updateHistory;
    }
}
