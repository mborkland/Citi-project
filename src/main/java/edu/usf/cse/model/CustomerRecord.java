package edu.usf.cse.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CustomerRecord {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private int csiId;

    private String csInstance;

    private String businessId;

    private String bizUnitId;

    private String productId;

    private String bizProdId;

    private String cxBusinessUnitName;

    private String cxBusinessUnitNameDisplayValue;

    private String cxBusinessGreenzone;

    private String rulesetMapped;

    private String region;

    private String country;

    private String sector;

    private boolean workflowFlag;

    private String workflowInstance;

    private String wfBusinessUnitName;

    private String wfBusinesUnitNameDisplayValue;

    private String wfBusinessGreenzone;

    private String interfaceDescription;

    private int interfaceAppId;

    private String interfaceApplicationName;

    private String operationEntity;

    private String cbusol;

    private String opsComplianceContacts;

    private String cwVersion;

    private boolean gomCompliant;

    private String cwUatContacts;

    private String sourceTechContacts;

    private String impactToBusiness;

    private String businessEscalationContacts;

    private String timezone;

    private String impactToProductProcessor;

    private String productProcessorEscalationContacts;

    private String productProcessorGroupDl;

    private String productProcessorSnowGroupName;

    private String productProcessorSla;

    private String frequency;

    private String fileScheduledDateAndTime;

    private String file2ndScheduledDateAndTime;

    private String averageVolume;

    private String averageRuntime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCsiId() {
        return csiId;
    }

    public void setCsiId(int csiId) {
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

    public boolean isWorkflowFlag() {
        return workflowFlag;
    }

    public void setWorkflowFlag(boolean workflowFlag) {
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

    public int getInterfaceAppId() {
        return interfaceAppId;
    }

    public void setInterfaceAppId(int interfaceAppId) {
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

    public boolean isGomCompliant() {
        return gomCompliant;
    }

    public void setGomCompliant(boolean gomCompliant) {
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
