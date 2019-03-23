package edu.usf.cse.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TxBuDetails implements BuDetails {

    @Column(name="BUSINESS_ID")
    private String businessId;

    @Column(name="PRODUCT_ID")
    private String productId;

    @Column(name="CSI_ID")
    private Integer csiId;

    @Column(name="Unique_Product_ID")
    private String uniqueProductId;

    @Column(name="Tx_Screening_Business_Unit_Name")
    private String txScreeningBusinessUnitName;

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

    @Column(name="WF_Business_Greenzone")
    private String wfBusinessGreenzone;

    @Column(name="Interface_App_ID")
    private Integer interfaceAppId;

    @Column(name="Interface_Application_Name")
    private String interfaceApplicationName;

    @Column(name="Operation_Entity")
    private String operationEntity;

    @Column(name="Connectivity_Protocol")
    private String connectivityProtocol;

    @Column(name="Workflow_Operations_Contacts")
    private String workflowOperationsContacts;

    @Column(name="Source_Tech_Contacts")
    private String sourceTechContacts;

    @Column(name="Business_Hotline")
    private String businessHotline;

    @Column(name="Business_Escalation_Point_of_Contact")
    private String businessEscalationPointOfContact;

    @Column(name="Impact_to_Product_Processor")
    private String impactToProductProcessor;

    @Column(name="Product_Processor")
    private String productProcessor;

    @Column(name="Hotline_Number")
    private String hotlineNumber;

    @Column(name="Escalation_Path_1st_Level_Support")
    private String escalationPath1stLevelSupport;

    @Column(name="Escalation_Path_2nd_Level_Support")
    private String escalationPath2ndLevelSupport;

    @Column(name="1st_Level_Escalation")
    private String firstLevelEscalation;

    @Column(name="2nd_Level_Escalation")
    private String secondLevelEscalation;

    @Column(name="Product_Processor_Group_DL")
    private String productProcessorGroupDl;

    @Column(name="Product_Processor_SNOW_Group_Name")
    private String productProcessorSnowGroupName;

    @Column(name="Product_Processor_Screening_Response_Cutoff_Time")
    private String productProcessorScreeningResponseCutoffTime;

    @Column(name="Product_Processor_Standard_Greenzones")
    private String productProcessorStandardGreenzones;

    @Column(name="Interface_Connectivity_Doc")
    private String interfaceConnectivityDoc;

    @Column(name="Retry_Mechanism")
    private String retryMechanism;

    @Column(name="Daily_Online_Volumes_Expected")
    private String dailyOnlineVolumesExpected;

    @Column(name="Schedule_for_Realtime_Volumes")
    private String scheduleForRealtimeVolumes;

    @Column(name="Batches_or_Peaks_for_Realtime_Volumes")
    private String batchesOrPeaksForRealtimeVolumes;

    @Column(name="Initial_Screening_Response_SLA")
    private String initialScreeningResponseSla;

    @Column(name="Threshold_Set_for_Timeouts")
    private String thresholdSetForTimeouts;

    @Column(name="Any_Batch_Component")
    private Boolean anyBatchComponent;

    @Column(name="Workflow_Operations_Work_Schedule")
    private String workflowOperationsWorkSchedule;

    @Column(name="Contacts_Checked")
    private Boolean contactsChecked;

    @Column(name="Update_History")
    private String updateHistory;

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Override
    public Integer getCsiId() {
        return csiId;
    }

    @Override
    public void setCsiId(Integer csiId) {
        this.csiId = csiId;
    }

    public String getUniqueProductId() {
        return uniqueProductId;
    }

    public void setUniqueProductId(String uniqueProductId) {
        this.uniqueProductId = uniqueProductId;
    }

    public String getTxScreeningBusinessUnitName() {
        return txScreeningBusinessUnitName;
    }

    public void setTxScreeningBusinessUnitName(String txScreeningBusinessUnitName) {
        this.txScreeningBusinessUnitName = txScreeningBusinessUnitName;
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

    public String getWfBusinessGreenzone() {
        return wfBusinessGreenzone;
    }

    public void setWfBusinessGreenzone(String wfBusinessGreenzone) {
        this.wfBusinessGreenzone = wfBusinessGreenzone;
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

    public String getConnectivityProtocol() {
        return connectivityProtocol;
    }

    public void setConnectivityProtocol(String connectivityProtocol) {
        this.connectivityProtocol = connectivityProtocol;
    }

    public String getWorkflowOperationsContacts() {
        return workflowOperationsContacts;
    }

    public void setWorkflowOperationsContacts(String workflowOperationsContacts) {
        this.workflowOperationsContacts = workflowOperationsContacts;
    }

    public String getSourceTechContacts() {
        return sourceTechContacts;
    }

    public void setSourceTechContacts(String sourceTechContacts) {
        this.sourceTechContacts = sourceTechContacts;
    }

    public String getBusinessHotline() {
        return businessHotline;
    }

    public void setBusinessHotline(String businessHotline) {
        this.businessHotline = businessHotline;
    }

    public String getBusinessEscalationPointOfContact() {
        return businessEscalationPointOfContact;
    }

    public void setBusinessEscalationPointOfContact(String businessEscalationPointOfContact) {
        this.businessEscalationPointOfContact = businessEscalationPointOfContact;
    }

    public String getImpactToProductProcessor() {
        return impactToProductProcessor;
    }

    public void setImpactToProductProcessor(String impactToProductProcessor) {
        this.impactToProductProcessor = impactToProductProcessor;
    }

    public String getProductProcessor() {
        return productProcessor;
    }

    public void setProductProcessor(String productProcessor) {
        this.productProcessor = productProcessor;
    }

    public String getHotlineNumber() {
        return hotlineNumber;
    }

    public void setHotlineNumber(String hotlineNumber) {
        this.hotlineNumber = hotlineNumber;
    }

    public String getEscalationPath1stLevelSupport() {
        return escalationPath1stLevelSupport;
    }

    public void setEscalationPath1stLevelSupport(String escalationPath1stLevelSupport) {
        this.escalationPath1stLevelSupport = escalationPath1stLevelSupport;
    }

    public String getEscalationPath2ndLevelSupport() {
        return escalationPath2ndLevelSupport;
    }

    public void setEscalationPath2ndLevelSupport(String escalationPath2ndLevelSupport) {
        this.escalationPath2ndLevelSupport = escalationPath2ndLevelSupport;
    }

    public String getFirstLevelEscalation() {
        return firstLevelEscalation;
    }

    public void setFirstLevelEscalation(String firstLevelEscalation) {
        this.firstLevelEscalation = firstLevelEscalation;
    }

    public String getSecondLevelEscalation() {
        return secondLevelEscalation;
    }

    public void setSecondLevelEscalation(String secondLevelEscalation) {
        this.secondLevelEscalation = secondLevelEscalation;
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

    public String getProductProcessorScreeningResponseCutoffTime() {
        return productProcessorScreeningResponseCutoffTime;
    }

    public void setProductProcessorScreeningResponseCutoffTime(String productProcessorScreeningResponseCutoffTime) {
        this.productProcessorScreeningResponseCutoffTime = productProcessorScreeningResponseCutoffTime;
    }

    public String getProductProcessorStandardGreenzones() {
        return productProcessorStandardGreenzones;
    }

    public void setProductProcessorStandardGreenzones(String productProcessorStandardGreenzones) {
        this.productProcessorStandardGreenzones = productProcessorStandardGreenzones;
    }

    public String getInterfaceConnectivityDoc() {
        return interfaceConnectivityDoc;
    }

    public void setInterfaceConnectivityDoc(String interfaceConnectivityDoc) {
        this.interfaceConnectivityDoc = interfaceConnectivityDoc;
    }

    public String getRetryMechanism() {
        return retryMechanism;
    }

    public void setRetryMechanism(String retryMechanism) {
        this.retryMechanism = retryMechanism;
    }

    public String getDailyOnlineVolumesExpected() {
        return dailyOnlineVolumesExpected;
    }

    public void setDailyOnlineVolumesExpected(String dailyOnlineVolumesExpected) {
        this.dailyOnlineVolumesExpected = dailyOnlineVolumesExpected;
    }

    public String getScheduleForRealtimeVolumes() {
        return scheduleForRealtimeVolumes;
    }

    public void setScheduleForRealtimeVolumes(String scheduleForRealtimeVolumes) {
        this.scheduleForRealtimeVolumes = scheduleForRealtimeVolumes;
    }

    public String getBatchesOrPeaksForRealtimeVolumes() {
        return batchesOrPeaksForRealtimeVolumes;
    }

    public void setBatchesOrPeaksForRealtimeVolumes(String batchesOrPeaksForRealtimeVolumes) {
        this.batchesOrPeaksForRealtimeVolumes = batchesOrPeaksForRealtimeVolumes;
    }

    public String getInitialScreeningResponseSla() {
        return initialScreeningResponseSla;
    }

    public void setInitialScreeningResponseSla(String initialScreeningResponseSla) {
        this.initialScreeningResponseSla = initialScreeningResponseSla;
    }

    public String getThresholdSetForTimeouts() {
        return thresholdSetForTimeouts;
    }

    public void setThresholdSetForTimeouts(String thresholdSetForTimeouts) {
        this.thresholdSetForTimeouts = thresholdSetForTimeouts;
    }

    public Boolean getAnyBatchComponent() {
        return anyBatchComponent;
    }

    public void setAnyBatchComponent(Boolean anyBatchComponent) {
        this.anyBatchComponent = anyBatchComponent;
    }

    public String getWorkflowOperationsWorkSchedule() {
        return workflowOperationsWorkSchedule;
    }

    public void setWorkflowOperationsWorkSchedule(String workflowOperationsWorkSchedule) {
        this.workflowOperationsWorkSchedule = workflowOperationsWorkSchedule;
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
