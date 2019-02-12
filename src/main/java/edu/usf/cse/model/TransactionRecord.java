package edu.usf.cse.model;

import javax.persistence.*;

@Entity
@Table(name="TX_BU_DETAILS")
public class TransactionRecord {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="ID")
    private Integer id;

    @Column(name="CSI_ID")
    private String csiId;

    @Column(name="CS_Instance")
    private String csInstance;

    @Column(name="BUSINESS_UNIT_ID")
    private String businessUnitId;

    @Column(name="BUSINESS_PRODUCT_ID")
    private String businessProductId;

    @Column(name="Corrected_BU_Name")
    private String correctedBuName;

    @Column(name="Mx_Business_Greenzone")
    private String mxBusinessGreenzone;

    @Column(name="Ruleset_Mapped")
    private String rulesetMapped;

    @Column(name="REGION")
    private String region;

    @Column(name="COUNTRY")
    private String country;

    @Column(name="SECTOR")
    private String sector;

    @Column(name="Workflow_Flag")
    private String workflowFlag;

    @Column(name="Workflow_Instance")
    private String workflowInstance;

    @Column(name="WF_Business_Unit_Name")
    private String wfBusinessUnitName;

    @Column(name="WF_Business_Unit_Name_Display_Value")
    private String wfBusinessUnitNameDisplayValue;

    @Column(name="WF_Business_Greenzone")
    private String wfBusinessGreenzone;

    @Column(name="Interface_App_ID")
    private String interfaceAppId;

    @Column(name="Interface_Application_Name")
    private String interfaceApplicationName;

    @Column(name="Operation_Entity")
    private String operationEntity;

    @Column(name="CS_WF")
    private String csWf;

    @Column(name="Interface_Description")
    private String interfaceDescription;

    @Column(name="Workflow_Operations_Contacts")
    private String workflowOperationsContacts;

    @Column(name="Source_Tech_Contacts")
    private String sourceTechContacts;

    @Column(name="Impact_to_Business")
    private String impactToBusiness;

    @Column(name="Business_Hotline")
    private String businessHotline;

    @Column(name="Business_Escalation_Contacts")
    private String businessEscalationContacts;

    @Column(name="Timezone")
    private String timezone;

    @Column(name="Impact_to_Product_Processor")
    private String impactToProductProcessor;

    @Column(name="Product_Processor_Contacts")
    private String productProcessorContacts;

    @Column(name="Escalation_Path_1st_Level_Contacts")
    private String escalationPath1stLevelContacts;

    @Column(name="Escalation_Path_2nd_Level_Contacts")
    private String escalationPath2ndLevelContacts;

    @Column(name="Escalation_Path_1st_Level_Application_Manager")
    private String escalationPath1stLevelApplicationManager;

    @Column(name="Escalation_Path_2nd_Level_Application_Manager")
    private String escalationPath2ndLevelApplicationManager;

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

    @Column(name="Inital_Screening_Response_SLA")
    private String initialScreeningResponseSla;

    @Column(name="Threshold_Set_for_Timeouts")
    private String thresholdSetForTimeouts;

    @Column(name="Any_Batch_Component")
    private String anyBatchComponent;

    @Column(name="Workflow_Operations_Work_Schedule")
    private String workflowOperationsWorkSchedule;


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

    public String getBusinessUnitId() {
        return businessUnitId;
    }

    public void setBusinessUnitId(String businessUnitId) {
        this.businessUnitId = businessUnitId;
    }

    public String getBusinessProductId() {
        return businessProductId;
    }

    public void setBusinessProductId(String businessProductId) {
        this.businessProductId = businessProductId;
    }

    public String getCorrectedBuName() {
        return correctedBuName;
    }

    public void setCorrectedBuName(String correctedBuName) {
        this.correctedBuName = correctedBuName;
    }

    public String getMxBusinessGreenzone() {
        return mxBusinessGreenzone;
    }

    public void setMxBusinessGreenzone(String mxBusinessGreenzone) {
        this.mxBusinessGreenzone = mxBusinessGreenzone;
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

    public String getWorkflowFlag() {
        return workflowFlag;
    }

    public void setWorkflowFlag(String workflowFlag) {
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

    public String getCsWf() {
        return csWf;
    }

    public void setCsWf(String csWf) {
        this.csWf = csWf;
    }

    public String getInterfaceDescription() {
        return interfaceDescription;
    }

    public void setInterfaceDescription(String interfaceDescription) {
        this.interfaceDescription = interfaceDescription;
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

    public String getImpactToBusiness() {
        return impactToBusiness;
    }

    public void setImpactToBusiness(String impactToBusiness) {
        this.impactToBusiness = impactToBusiness;
    }

    public String getBusinessHotline() {
        return businessHotline;
    }

    public void setBusinessHotline(String businessHotline) {
        this.businessHotline = businessHotline;
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

    public String getProductProcessorContacts() {
        return productProcessorContacts;
    }

    public void setProductProcessorContacts(String productProcessorContacts) {
        this.productProcessorContacts = productProcessorContacts;
    }

    public String getEscalationPath1stLevelContacts() {
        return escalationPath1stLevelContacts;
    }

    public void setEscalationPath1stLevelContacts(String escalationPath1stLevelContacts) {
        this.escalationPath1stLevelContacts = escalationPath1stLevelContacts;
    }

    public String getEscalationPath2ndLevelContacts() {
        return escalationPath2ndLevelContacts;
    }

    public void setEscalationPath2ndLevelContacts(String escalationPath2ndLevelContacts) {
        this.escalationPath2ndLevelContacts = escalationPath2ndLevelContacts;
    }

    public String getEscalationPath1stLevelApplicationManager() {
        return escalationPath1stLevelApplicationManager;
    }

    public void setEscalationPath1stLevelApplicationManager(String escalationPath1stLevelApplicationManager) {
        this.escalationPath1stLevelApplicationManager = escalationPath1stLevelApplicationManager;
    }

    public String getEscalationPath2ndLevelApplicationManager() {
        return escalationPath2ndLevelApplicationManager;
    }

    public void setEscalationPath2ndLevelApplicationManager(String escalationPath2ndLevelApplicationManager) {
        this.escalationPath2ndLevelApplicationManager = escalationPath2ndLevelApplicationManager;
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

    public String getAnyBatchComponent() {
        return anyBatchComponent;
    }

    public void setAnyBatchComponent(String anyBatchComponent) {
        this.anyBatchComponent = anyBatchComponent;
    }

    public String getWorkflowOperationsWorkSchedule() {
        return workflowOperationsWorkSchedule;
    }

    public void setWorkflowOperationsWorkSchedule(String workflowOperationsWorkSchedule) {
        this.workflowOperationsWorkSchedule = workflowOperationsWorkSchedule;
    }
}
