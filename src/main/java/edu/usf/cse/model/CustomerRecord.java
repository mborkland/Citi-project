package edu.usf.cse.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CustomerRecord {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="ID")
    private Integer id;

    @Column(name="CSI ID")
    private String csiId;

    @Column(name="CS Instance")
    private String csInstance;

    @Column(name="BUSINESS_ID")
    private String businessId;

    @Column(name="BIZ_UNIT_ID")
    private String bizUnitId;

    @Column(name="PRODUCT_ID")
    private String productId;

    @Column(name="BIZ_PROD_ID")
    private String bizProdId;

    @Column(name="Cx Business Unit Name")
    private String cxBusinessUnitName;

    @Column(name="Cx Business Unit Name Display Value")
    private String cxBusinessUnitNameDisplayValue;

    @Column(name="Cx Business Greenzone")
    private String cxBusinessGreenzone;

    @Column(name="Ruleset Mapped")
    private String rulesetMapped;

    @Column(name="REGION")
    private String region;

    @Column(name="COUNTRY")
    private String country;

    @Column(name="SECTOR")
    private String sector;

    @Column(name="Workflow Flag")
    private String workflowFlag;

    @Column(name="Workflow Instance")
    private String workflowInstance;

    @Column(name="WF Business Unit Name")
    private String wfBusinessUnitName;

    @Column(name="WF Business Unit Name Display Value")
    private String wfBusinesUnitNameDisplayValue;

    @Column(name="WF Business Greenzone")
    private String wfBusinessGreenzone;

    @Column(name="Interface Des.")
    private String interfaceDescription;

    @Column(name="Interface App ID")
    private String interfaceAppId;

    @Column(name="Interface Application Name")
    private String interfaceApplicationName;

    @Column(name="Operation Entity")
    private String operationEntity;

    @Column(name="CBUSOL")
    private String cbusol;

    @Column(name="Ops/Compliance Contacts")
    private String opsComplianceContacts;

    @Column(name="Cw V1/V2")
    private String cwVersion;

    @Column(name="GOM Compliant?")
    private String gomCompliant;

    @Column(name="Cw UAT Contacts")
    private String cwUatContacts;

    @Column(name="Source Tech Contacts")
    private String sourceTechContacts;

    @Column(name="Impact to Business")
    private String impactToBusiness;

    @Column(name="Business Escalation Contacts")
    private String businessEscalationContacts;

    @Column(name="Timezone")
    private String timezone;

    @Column(name="Impact to Product Processor")
    private String impactToProductProcessor;

    @Column(name="Product Processor Escalation Contacts")
    private String productProcessorEscalationContacts;

    @Column(name="Product Processor Group DL")
    private String productProcessorGroupDl;

    @Column(name="Product Processor SNOW Group Name")
    private String productProcessorSnowGroupName;

    @Column(name="Product Processor SLA")
    private String productProcessorSla;

    @Column(name="Frequency")
    private String frequency;

    @Column(name="File Scheduled Date and Time")
    private String fileScheduledDateAndTime;

    @Column(name="File 2nd Scheduled Date and Time")
    private String file2ndScheduledDateAndTime;

    @Column(name="Avg Volume")
    private String averageVolume;

    @Column(name="Avg Runtime")
    private String averageRuntime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCsiId() {
        return csiId;
    }

    public void setCsiId(String csiId) {
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

    public String getCxBusinessUnitName() {
        return cxBusinessUnitName;
    }

    public void setCxBusinessUnitName(String cxBusinessUnitName) {
        this.cxBusinessUnitName = cxBusinessUnitName;
    }

    public String getCxBusinessUnitNameDisplayValue() {
        return cxBusinessUnitNameDisplayValue;
    }

    public void setCxBusinessUnitNameDisplayValue(String cxBusinessUnitNameDisplayValue) {
        this.cxBusinessUnitNameDisplayValue = cxBusinessUnitNameDisplayValue;
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

    public String  getWorkflowFlag() {
        return workflowFlag;
    }

    public void setWorkflowFlag(String  workflowFlag) {
        this.workflowFlag = workflowFlag;
    }

    public String getWorkflowInstance() {
        return workflowInstance;
    }

    public void setWorkflowInstance(String workflowInstance) {
        this.workflowInstance = workflowInstance;
    }

    public String getWfBusinessUnitName() {
        return wfBusinessUnitName;
    }

    public void setWfBusinessUnitName(String wfBusinessUnitName) {
        this.wfBusinessUnitName = wfBusinessUnitName;
    }

    public String getWfBusinesUnitNameDisplayValue() {
        return wfBusinesUnitNameDisplayValue;
    }

    public void setWfBusinesUnitNameDisplayValue(String wfBusinesUnitNameDisplayValue) {
        this.wfBusinesUnitNameDisplayValue = wfBusinesUnitNameDisplayValue;
    }

    public String getWfBusinessGreenzone() {
        return wfBusinessGreenzone;
    }

    public void setWfBusinessGreenzone(String wfBusinessGreenzone) {
        this.wfBusinessGreenzone = wfBusinessGreenzone;
    }

    public String getInterfaceDescription() {
        return interfaceDescription;
    }

    public void setInterfaceDescription(String interfaceDescription) {
        this.interfaceDescription = interfaceDescription;
    }

    public String getInterfaceAppId() {
        return interfaceAppId;
    }

    public void setInterfaceAppId(String interfaceAppId) {
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

    public String getCbusol() {
        return cbusol;
    }

    public void setCbusol(String cbusol) {
        this.cbusol = cbusol;
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

    public String  getGomCompliant() {
        return gomCompliant;
    }

    public void setGomCompliant(String  gomCompliant) {
        this.gomCompliant = gomCompliant;
    }

    public String getCwUatContacts() {
        return cwUatContacts;
    }

    public void setCwUatContacts(String cwUatContacts) {
        this.cwUatContacts = cwUatContacts;
    }

    public String getSourceTechContacts() {
        return sourceTechContacts;
    }

    public void setSourceTechContacts(String sourceTechContacts) {
        this.sourceTechContacts = sourceTechContacts;
    }

    public String getImpactToBusiness() {
        return impactToBusiness;
    }

    public void setImpactToBusiness(String impactToBusiness) {
        this.impactToBusiness = impactToBusiness;
    }

    public String getBusinessEscalationContacts() {
        return businessEscalationContacts;
    }

    public void setBusinessEscalationContacts(String businessEscalationContacts) {
        this.businessEscalationContacts = businessEscalationContacts;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getImpactToProductProcessor() {
        return impactToProductProcessor;
    }

    public void setImpactToProductProcessor(String impactToProductProcessor) {
        this.impactToProductProcessor = impactToProductProcessor;
    }

    public String getProductProcessorEscalationContacts() {
        return productProcessorEscalationContacts;
    }

    public void setProductProcessorEscalationContacts(String productProcessorEscalationContacts) {
        this.productProcessorEscalationContacts = productProcessorEscalationContacts;
    }

    public String getProductProcessorGroupDl() {
        return productProcessorGroupDl;
    }

    public void setProductProcessorGroupDl(String productProcessorGroupDl) {
        this.productProcessorGroupDl = productProcessorGroupDl;
    }

    public String getProductProcessorSnowGroupName() {
        return productProcessorSnowGroupName;
    }

    public void setProductProcessorSnowGroupName(String productProcessorSnowGroupName) {
        this.productProcessorSnowGroupName = productProcessorSnowGroupName;
    }

    public String getProductProcessorSla() {
        return productProcessorSla;
    }

    public void setProductProcessorSla(String productProcessorSla) {
        this.productProcessorSla = productProcessorSla;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getFileScheduledDateAndTime() {
        return fileScheduledDateAndTime;
    }

    public void setFileScheduledDateAndTime(String fileScheduledDateAndTime) {
        this.fileScheduledDateAndTime = fileScheduledDateAndTime;
    }

    public String getAverageVolume() {
        return averageVolume;
    }

    public void setAverageVolume(String averageVolume) {
        this.averageVolume = averageVolume;
    }

    public String getAverageRuntime() {
        return averageRuntime;
    }

    public void setAverageRuntime(String averageRuntime) {
        this.averageRuntime = averageRuntime;
    }

    public String getFile2ndScheduledDateAndTime() {
        return file2ndScheduledDateAndTime;
    }

    public void setFile2ndScheduledDateAndTime(String file2ndScheduledDateAndTime) {
        this.file2ndScheduledDateAndTime = file2ndScheduledDateAndTime;
    }
}
