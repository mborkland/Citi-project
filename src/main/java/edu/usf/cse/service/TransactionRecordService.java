package edu.usf.cse.service;

import edu.usf.cse.model.Record;
import edu.usf.cse.model.SearchParameter;
import edu.usf.cse.model.TransactionRecord;
//import edu.usf.cse.model.TransactionRecordUpdateHistory;
import edu.usf.cse.persistence.TransactionRecordRepository;
//import edu.usf.cse.persistence.TransactionRecordUpdateHistoryRepository;
import edu.usf.cse.specification.RecordSpecification;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;

@Component
public class TransactionRecordService implements RecordService {

    private TransactionRecordRepository transactionRecordRepository;
    //private TransactionRecordUpdateHistoryRepository transactionRecordUpdateHistoryRepository;

    @Autowired
    public TransactionRecordService(TransactionRecordRepository transactionRecordRepository) {
        this.transactionRecordRepository = transactionRecordRepository;
        //this.transactionRecordUpdateHistoryRepository = transactionRecordUpdateHistoryRepository;
    }

    @Override
    public String createRecord(List<String> fields, String requestor) {
        TransactionRecord transactionRecord = new TransactionRecord();
        Iterator<String> iterator = fields.iterator();
        transactionRecord.setCsiId(iterator.next());
        transactionRecord.setCsInstance(iterator.next());
        transactionRecord.setBusinessUnitId(iterator.next());
        transactionRecord.setBusinessProductId(iterator.next());
        transactionRecord.setCorrectedBuName(iterator.next());
        transactionRecord.setMxBusinessGreenzone(iterator.next());
        transactionRecord.setRulesetMapped(iterator.next());
        transactionRecord.setRegion(iterator.next());
        transactionRecord.setCountry(iterator.next());
        transactionRecord.setSector(iterator.next());
        transactionRecord.setWorkflowFlag(iterator.next());
        transactionRecord.setWorkflowInstance(iterator.next());
        transactionRecord.setWfBusinessUnitName(iterator.next());
        transactionRecord.setWfBusinessUnitNameDisplayValue(iterator.next());
        transactionRecord.setWfBusinessGreenzone(iterator.next());
        transactionRecord.setInterfaceAppId(iterator.next());
        transactionRecord.setInterfaceApplicationName(iterator.next());
        transactionRecord.setOperationEntity(iterator.next());
        transactionRecord.setCsWf(iterator.next());
        transactionRecord.setInterfaceDescription(iterator.next());
        transactionRecord.setWorkflowOperationsContacts(iterator.next());
        transactionRecord.setSourceTechContacts(iterator.next());
        transactionRecord.setImpactToBusiness(iterator.next());
        transactionRecord.setBusinessHotline(iterator.next());
        transactionRecord.setBusinessEscalationContacts(iterator.next());
        transactionRecord.setTimezone(iterator.next());
        transactionRecord.setImpactToProductProcessor(iterator.next());
        transactionRecord.setProductProcessorContacts(iterator.next());
        transactionRecord.setEscalationPath1stLevelContacts(iterator.next());
        transactionRecord.setEscalationPath2ndLevelContacts(iterator.next());
        transactionRecord.setEscalationPath1stLevelApplicationManager(iterator.next());
        transactionRecord.setEscalationPath2ndLevelApplicationManager(iterator.next());
        transactionRecord.setProductProcessorGroupDl(iterator.next());
        transactionRecord.setProductProcessorSnowGroupName(iterator.next());
        transactionRecord.setProductProcessorScreeningResponseCutoffTime(iterator.next());
        transactionRecord.setProductProcessorStandardGreenzones(iterator.next());
        transactionRecord.setInterfaceConnectivityDoc(iterator.next());
        transactionRecord.setRetryMechanism(iterator.next());
        transactionRecord.setDailyOnlineVolumesExpected(iterator.next());
        transactionRecord.setScheduleForRealtimeVolumes(iterator.next());
        transactionRecord.setBatchesOrPeaksForRealtimeVolumes(iterator.next());
        transactionRecord.setInitialScreeningResponseSla(iterator.next());
        transactionRecord.setThresholdSetForTimeouts(iterator.next());
        transactionRecord.setAnyBatchComponent(iterator.next());
        transactionRecord.setWorkflowOperationsWorkSchedule(iterator.next());
        transactionRecord.setUpdateHistory("Record created on " + new Timestamp(System.currentTimeMillis()) + " by " + requestor);
        
        transactionRecordRepository.save(transactionRecord);
        //addCreatedInfoToUpdateHistory(transactionRecord, fields, requestor);
        return "Transaction record created successfully";
    }

    /*private void addCreatedInfoToUpdateHistory(TransactionRecord record, List<String> fields, String requestor) {
    	Iterator<String> iterator = fields.iterator();
    	
    	addInfoToUpdateHistory(record, "CSI_ID", iterator.next(), requestor); 
    	addInfoToUpdateHistory(record, "CS_Instance", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "BUSINESS_UNIT_ID", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "BUSINESS_PRODUCT_ID", iterator.next(), requestor);
	    addInfoToUpdateHistory(record, "Corrected_BU_Name", iterator.next(), requestor);  
	    addInfoToUpdateHistory(record, "Mx_Business_Greenzone", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "Ruleset_Mapped", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "REGION", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "COUNTRY", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "SECTOR", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "Workflow_Flag", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "Workflow_Instance", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "WF_Business_Unit_Name", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "WF_Business_Unit_Name_Display_Value", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "WF_Business_Greenzone", iterator.next(), requestor);  
	    addInfoToUpdateHistory(record, "Interface_App_ID", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "Interface_Application_Name", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "Operation_Entity", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "CS_WF", iterator.next(), requestor);
	    addInfoToUpdateHistory(record, "Interface_Description", iterator.next(), requestor);
	    addInfoToUpdateHistory(record, "Workflow_Operations_Contacts", iterator.next(), requestor);
	    addInfoToUpdateHistory(record, "Source_Tech_Contacts", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "Impact_to_Business", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "Business_Hotline", iterator.next(), requestor);
	    addInfoToUpdateHistory(record, "Business_Escalation_Contacts", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "Timezone", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "Impact_to_Product_Processor", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "Product_Processor_Contacts", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "Escalation_Path_1st_Level_Contacts", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "Escalation_Path_2nd_Level_Contacts", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "Escalation_Path_1st_Level_Application_Manager", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "Escalation_Path_2nd_Level_Application_Manager", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "Product_Processor_Group_DL", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "Product_Processor_SNOW_Group_Name", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "Product_Processor_Screening_Response_Cutoff_Time", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "Product_Processor_Standard_Greenzones", iterator.next(), requestor); 	
	    addInfoToUpdateHistory(record, "Interface_Connectivity_Doc", iterator.next(), requestor); 	
	    addInfoToUpdateHistory(record, "Retry_Mechanism", iterator.next(), requestor); 	
	    addInfoToUpdateHistory(record, "Daily_Online_Volumes_Expected", iterator.next(), requestor); 	
	    addInfoToUpdateHistory(record, "Schedule_for_Realtime_Volumes", iterator.next(), requestor); 	
	    addInfoToUpdateHistory(record, "Batches_or_Peaks_for_Realtime_Volumes", iterator.next(), requestor); 	
	    addInfoToUpdateHistory(record, "Initial_Screening_Response_SLA", iterator.next(), requestor); 	
	    addInfoToUpdateHistory(record, "Threshold_Set_for_Timeouts", iterator.next(), requestor); 	
	    addInfoToUpdateHistory(record, "Any_Batch_Component", iterator.next(), requestor); 	
	    addInfoToUpdateHistory(record, "Workflow_Operations_Work_Schedule", iterator.next(), requestor); 	
    }
    
    private void addInfoToUpdateHistory(TransactionRecord record, String fieldName, String newValue, String requestor) {
    	TransactionRecordUpdateHistory updateInfo = new TransactionRecordUpdateHistory();
        updateInfo.setRecordID(record.getId());
        updateInfo.setCsiId(record.getCsiId());
        updateInfo.setCsInstance(record.getCsInstance());
        updateInfo.setFieldName(fieldName);
        updateInfo.setNewValue(newValue);
        updateInfo.setRequestor(requestor);
        updateInfo.setTimeOfChange(new Timestamp(System.currentTimeMillis()));
        
        transactionRecordUpdateHistoryRepository.save(updateInfo);
    }*/
    
    @Override
    public List<Record> getRecords(List<SearchParameter> searchParameters) {
        RecordSpecification recordSpecification = new RecordSpecification(searchParameters);
        return transactionRecordRepository.findAll(recordSpecification);
    }

    @Override
    public String updateRecord(Integer id, String field, String newValue, String requestor) {
        // TODO: add logic to update the given field of the given record with new value
    	TransactionRecord recordToUpdate = transactionRecordRepository.findOne(id);
        String oldValue = "";
    	
    	switch(field) {
        case "CSI_ID": 
        	oldValue = recordToUpdate.getCsiId();
        	recordToUpdate.setCsiId(newValue);
        	break;
        case "CS_Instance":
        	oldValue = recordToUpdate.getCsInstance();
        	recordToUpdate.setCsInstance(newValue);
        	break;
        case "BUSINESS_UNIT_ID":
        	oldValue = recordToUpdate.getBusinessUnitId();
        	recordToUpdate.setBusinessUnitId(newValue);
        	break;
        case "BUSINESS_PRODUCT_ID":
        	oldValue = recordToUpdate.getBusinessProductId();
        	recordToUpdate.setBusinessProductId(newValue);
        	break;
        case "Corrected_BU_Name":
        	oldValue = recordToUpdate.getCorrectedBuName();
        	recordToUpdate.setCorrectedBuName(newValue);
        	break;
        case "Mx_Business_Greenzone":
        	oldValue = recordToUpdate.getMxBusinessGreenzone();
        	recordToUpdate.setMxBusinessGreenzone(newValue);
        	break;
        case "Ruleset_Mapped":
        	oldValue = recordToUpdate.getRulesetMapped();
        	recordToUpdate.setRulesetMapped(newValue);
        	break;
        case "REGION":
        	oldValue = recordToUpdate.getRegion();
        	recordToUpdate.setRegion(newValue);
        	break;
        case "COUNTRY":
        	oldValue = recordToUpdate.getCountry();
        	recordToUpdate.setCountry(newValue);
        	break;
        case "SECTOR":
        	oldValue = recordToUpdate.getSector();
        	recordToUpdate.setSector(newValue);
        	break;
        case "Workflow_Flag":
        	oldValue = recordToUpdate.getWorkflowFlag();
        	recordToUpdate.setWorkflowFlag(newValue);
        	break;
        case "Workflow_Instance":
        	oldValue = recordToUpdate.getWorkflowInstance();
        	recordToUpdate.setWorkflowInstance(newValue);
        	break;
        case "WF_Business_Unit_Name":
        	oldValue = recordToUpdate.getWfBusinessUnitName();
        	recordToUpdate.setWfBusinessUnitName(newValue);
        	break;
        case "WF_Business_Unit_Name_Display_Value":
        	oldValue = recordToUpdate.getWfBusinessUnitNameDisplayValue();
        	recordToUpdate.setWfBusinessUnitNameDisplayValue(newValue);
        	break;
        case "WF_Business_Greenzone":
        	oldValue = recordToUpdate.getWfBusinessGreenzone();
        	recordToUpdate.setWfBusinessGreenzone(newValue);
        	break;
        case "Interface_App_ID":
        	oldValue = recordToUpdate.getInterfaceAppId();
        	recordToUpdate.setInterfaceAppId(newValue);
        	break;
        case "Interface_Application_Name":
        	oldValue = recordToUpdate.getInterfaceApplicationName();
        	recordToUpdate.setInterfaceApplicationName(newValue);
        	break;
        case "Operation_Entity":
        	oldValue = recordToUpdate.getOperationEntity();
        	recordToUpdate.setOperationEntity(newValue);
        	break;
        case "CS_WF":
        	oldValue = recordToUpdate.getCsWf();
        	recordToUpdate.setCsWf(newValue);
        	break;
        case "Interface_Description":
        	oldValue = recordToUpdate.getInterfaceDescription();
        	recordToUpdate.setInterfaceDescription(newValue);
        	break;
        case "Workflow_Operations_Contacts":
        	oldValue = recordToUpdate.getWorkflowOperationsContacts();
        	recordToUpdate.setWorkflowOperationsContacts(newValue);
        	break;
        case "Source_Tech_Contacts":
        	oldValue = recordToUpdate.getSourceTechContacts();
        	recordToUpdate.setSourceTechContacts(newValue);
        	break;
        case "Impact_to_Business":
        	oldValue = recordToUpdate.getImpactToBusiness();
        	recordToUpdate.setImpactToBusiness(newValue);
        	break;
        case "Business_Hotline":
        	oldValue = recordToUpdate.getBusinessHotline();
        	recordToUpdate.setImpactToBusiness(newValue);
        	break;
        case "Business_Escalation_Contacts":
        	oldValue = recordToUpdate.getBusinessEscalationContacts();
        	recordToUpdate.setBusinessEscalationContacts(newValue);
        	break;
        case "Timezone":
        	oldValue = recordToUpdate.getTimezone();
        	recordToUpdate.setTimezone(newValue);
        	break;
        case "Impact_to_Product_Processor":
        	oldValue = recordToUpdate.getImpactToProductProcessor();
        	recordToUpdate.setImpactToProductProcessor(newValue);
        	break;
        case "Product_Processor_Contacts":
        	oldValue = recordToUpdate.getProductProcessorContacts();
        	recordToUpdate.setProductProcessorContacts(newValue);
        	break;
        case "Escalation_Path_1st_Level_Contacts":
        	oldValue = recordToUpdate.getEscalationPath1stLevelContacts();
            recordToUpdate.setEscalationPath1stLevelContacts(newValue);
            break;
        case "Escalation_Path_2nd_Level_Contacts":
        	oldValue = recordToUpdate.getEscalationPath2ndLevelContacts();
            recordToUpdate.setEscalationPath2ndLevelContacts(newValue);
            break;
        case "Escalation_Path_1st_Level_Application_Manager":
        	oldValue = recordToUpdate.getEscalationPath1stLevelApplicationManager();
        	recordToUpdate.setEscalationPath1stLevelApplicationManager(newValue);
            break;
        case "Escalation_Path_2nd_Level_Application_Manager":
        	oldValue = recordToUpdate.getEscalationPath2ndLevelApplicationManager();
            recordToUpdate.setEscalationPath2ndLevelApplicationManager(newValue);
            break;
        case "Product_Processor_Group_DL":
        	oldValue = recordToUpdate.getProductProcessorGroupDl();
        	recordToUpdate.setProductProcessorGroupDl(newValue);
        	break;
        case "Product_Processor_SNOW_Group_Name":
        	oldValue = recordToUpdate.getProductProcessorSnowGroupName();
        	recordToUpdate.setProductProcessorSnowGroupName(newValue);
        	break;
        case "Product_Processor_Screening_Response_Cutoff_Time":
        	oldValue = recordToUpdate.getProductProcessorScreeningResponseCutoffTime();
        	recordToUpdate.setProductProcessorScreeningResponseCutoffTime(newValue);
        	break;
        case "Product_Processor_Standard_Greenzones":
        	oldValue = recordToUpdate.getProductProcessorStandardGreenzones();
        	recordToUpdate.setProductProcessorStandardGreenzones(newValue);
        	break;
        case "Interface_Connectivity_Doc":
        	oldValue = recordToUpdate.getInterfaceConnectivityDoc();
        	recordToUpdate.setInterfaceConnectivityDoc(newValue);
        	break;
        case "Retry_Mechanism":
        	oldValue = recordToUpdate.getRetryMechanism();
        	recordToUpdate.setRetryMechanism(newValue);
        	break;
        case "Daily_Online_Volumes_Expected":
        	oldValue = recordToUpdate.getDailyOnlineVolumesExpected();
        	recordToUpdate.setDailyOnlineVolumesExpected(newValue);
        	break;
        case "Schedule_for_Realtime_Volumes":
        	oldValue = recordToUpdate.getScheduleForRealtimeVolumes();
        	recordToUpdate.setScheduleForRealtimeVolumes(newValue);
        	break;
        case "Batches_or_Peaks_for_Realtime_Volumes":
        	oldValue = recordToUpdate.getBatchesOrPeaksForRealtimeVolumes();
        	recordToUpdate.setBatchesOrPeaksForRealtimeVolumes(newValue);
        	break;
        case "Initial_Screening_Response_SLA":
        	oldValue = recordToUpdate.getInitialScreeningResponseSla();
        	recordToUpdate.setInitialScreeningResponseSla(newValue);
        	break;
        case "Threshold_Set_for_Timeouts":
        	oldValue = recordToUpdate.getThresholdSetForTimeouts();
        	recordToUpdate.setThresholdSetForTimeouts(newValue);
        	break;
        case "Any_Batch_Component":
        	oldValue = recordToUpdate.getAnyBatchComponent();
           	recordToUpdate.setAnyBatchComponent(newValue);
        	break;
        case "Workflow_Operations_Work_Schedule":
        	oldValue = recordToUpdate.getWorkflowOperationsWorkSchedule();
        	recordToUpdate.setWorkflowOperationsWorkSchedule(newValue);
        	break;
        }
        
    	if(oldValue.equalsIgnoreCase(newValue))
    			return "Transaction record not updated. The updated value is the old value.";
    	
    	String updateHistory = recordToUpdate.getUpdateHistory();
    	updateHistory = updateHistory + ", Record updated on " + new Timestamp(System.currentTimeMillis()) + " by " + requestor;
    	recordToUpdate.setUpdateHistory(updateHistory);
    	
        transactionRecordRepository.save(recordToUpdate);
        //addInfoToUpdateHistory(recordToUpdate, field, newValue, requestor);
        
    	return "Transaction record updated successfully";
    }

    @Override
    public String deleteRecord(Integer id) {
        transactionRecordRepository.delete(id);
        return "Transaction record deleted successfully";
    }
}