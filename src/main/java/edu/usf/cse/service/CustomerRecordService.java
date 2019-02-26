package edu.usf.cse.service;

import edu.usf.cse.model.CustomerRecord;
import edu.usf.cse.model.Record;
import edu.usf.cse.model.SearchParameter;
//import edu.usf.cse.model.CustomerRecordUpdateHistory;
import edu.usf.cse.persistence.CustomerRecordRepository;
//import edu.usf.cse.persistence.CustomerRecordUpdateHistoryRepository;
import edu.usf.cse.specification.RecordSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;

@Component
public class CustomerRecordService implements RecordService {

    private CustomerRecordRepository customerRecordRepository;

    //private CustomerRecordUpdateHistoryRepository customerRecordUpdateHistoryRepository;

    @Autowired
    public CustomerRecordService(CustomerRecordRepository customerRecordRepository) {
        this.customerRecordRepository = customerRecordRepository;
        //this.customerRecordUpdateHistoryRepository = customerRecordUpdateHistoryRepository;
    }

    @Override
    public String createRecord(List<String> fields, String requestor) {
        CustomerRecord customerRecord = new CustomerRecord();
        Iterator<String> iterator = fields.iterator();
        customerRecord.setCsiId(iterator.next());
        customerRecord.setCsInstance(iterator.next());
        customerRecord.setBusinessId(iterator.next());
        customerRecord.setBizUnitId(iterator.next());
        customerRecord.setProductId(iterator.next());
        customerRecord.setBizProdId(iterator.next());
        customerRecord.setCxBusinessUnitName(iterator.next());
        customerRecord.setCxBusinessUnitNameDisplayValue(iterator.next());
        customerRecord.setCxBusinessGreenzone(iterator.next());
        customerRecord.setRulesetMapped(iterator.next());
        customerRecord.setRegion(iterator.next());
        customerRecord.setCountry(iterator.next());
        customerRecord.setSector(iterator.next());
        customerRecord.setWorkflowFlag(iterator.next());
        customerRecord.setWorkflowInstance(iterator.next());
        customerRecord.setWfBusinessUnitName(iterator.next());
        customerRecord.setWfBusinessUnitNameDisplayValue(iterator.next());
        customerRecord.setWfBusinessGreenzone(iterator.next());
        customerRecord.setInterfaceDescription(iterator.next());
        customerRecord.setInterfaceAppId(iterator.next());
        customerRecord.setInterfaceApplicationName(iterator.next());
        customerRecord.setOperationEntity(iterator.next());
        customerRecord.setCbusol(iterator.next());
        customerRecord.setOpsComplianceContacts(iterator.next());
        customerRecord.setCwVersion(iterator.next());
        customerRecord.setGomCompliant(iterator.next());
        customerRecord.setCwUatContacts(iterator.next());
        customerRecord.setSourceTechContacts(iterator.next());
        customerRecord.setImpactToBusiness(iterator.next());
        customerRecord.setBusinessEscalationContacts(iterator.next());
        customerRecord.setTimezone(iterator.next());
        customerRecord.setImpactToProductProcessor(iterator.next());
        customerRecord.setProductProcessorEscalationContacts(iterator.next());
        customerRecord.setProductProcessorGroupDl(iterator.next());
        customerRecord.setProductProcessorSnowGroupName(iterator.next());
        customerRecord.setProductProcessorSla(iterator.next());
        customerRecord.setFrequency(iterator.next());
        customerRecord.setFileScheduledDateAndTime(iterator.next());
        customerRecord.setAverageVolume(iterator.next());
        customerRecord.setAverageRuntime(iterator.next());
        customerRecord.setFile2ndScheduledDateAndTime(iterator.next());
        customerRecord.setUpdateHistory("Record created on " + new Timestamp(System.currentTimeMillis()) + " by " + requestor);

        customerRecordRepository.save(customerRecord);
    	//addCreatedInfoToUpdateHistory(customerRecord, fields, requestor);
        return "Customer record created successfully";
    }

    /*private void addCreatedInfoToUpdateHistory(CustomerRecord record, List<String> fields, String requestor) {
    	Iterator<String> iterator = fields.iterator();
    	
    	addInfoToUpdateHistory(record, "CSI_ID", iterator.next(), requestor); 
    	addInfoToUpdateHistory(record, "CS_Instance", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "BUSINESS_ID", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "BIZ_UNIT_ID", iterator.next(), requestor);
	    addInfoToUpdateHistory(record, "PRODUCT_ID", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "BIZ_PROD_ID", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "Cx_Business_Unit_Name", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "Cx_Business_Unit_Name_Display_Value", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "Cx_Business_Greenzone", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "Ruleset_Mapped", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "REGION", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "COUNTRY", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "SECTOR", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "Workflow_Flag", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "Workflow_Instance", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "WF_Business_Unit_Name", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "WF_Business_Unit_Name_Display_Value", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "WF_Business_Greenzone", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "Interface_Description", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "Interface_App_ID", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "Interface_Application_Name", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "Operation_Entity", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "CBUSOL", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "Ops_Compliance_Contacts", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "Cw_V1_V2", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "GOM_Compliant", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "Cw_UAT_Contacts", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "Source_Tech_Contacts", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "Impact_to_Business", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "Business_Escalation_Contacts", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "Timezone", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "Impact_to_Product_Processor", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "Product_Processor_Escalation_Contacts", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "Product_Processor_Group_DL", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "Product_Processor_SNOW_Group_Name", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "Product_Processor_SLA", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "Frequency", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "File_Scheduled_Date_and_Time", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "Avg_Volume", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "Avg_Runtime", iterator.next(), requestor); 
	    addInfoToUpdateHistory(record, "File_2nd_Scheduled_Date_and_Time", iterator.next(), requestor); 	
    }
    
    private void addInfoToUpdateHistory(CustomerRecord record, String fieldName, String newValue, String requestor) {
    	CustomerRecordUpdateHistory updateInfo = new CustomerRecordUpdateHistory();
        updateInfo.setRecordId(record.getId());
        updateInfo.setCsiId(record.getCsiId());
        updateInfo.setCsInstance(record.getCsInstance());
        updateInfo.setFieldName(fieldName);
        updateInfo.setNewValue(newValue);
        updateInfo.setRequestor(requestor);
        updateInfo.setTimeOfChange(new Timestamp(System.currentTimeMillis()));
        
        customerRecordUpdateHistoryRepository.save(updateInfo);
    }*/
    
    @Override
    public List<Record> getRecords(List<SearchParameter> searchParameters) {
        RecordSpecification recordSpecification = new RecordSpecification(searchParameters);
        return customerRecordRepository.findAll(recordSpecification);
    }

    @Override
    public String updateRecord(Integer id, String field, String newValue, String requestor) {
        CustomerRecord recordToUpdate = customerRecordRepository.findOne(id);
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
        case "BUSINESS_ID":
        	oldValue = recordToUpdate.getBusinessId();
        	recordToUpdate.setBusinessId(newValue);
        	break;
        case "BIZ_UNIT_ID":
        	oldValue = recordToUpdate.getBizUnitId();
        	recordToUpdate.setBizUnitId(newValue);
        	break;
        case "PRODUCT_ID":
        	oldValue = recordToUpdate.getProductId();
        	recordToUpdate.setProductId(newValue);
        	break;
        case "BIZ_PROD_ID":
        	oldValue = recordToUpdate.getBizProdId();
        	recordToUpdate.setBizProdId(newValue);
        	break;
        case "Cx_Business_Unit_Name":
        	oldValue = recordToUpdate.getCxBusinessUnitName();
        	recordToUpdate.setCxBusinessUnitName(newValue);
        	break;
        case "Cx_Business_Unit_Name_Display_Value":
        	oldValue = recordToUpdate.getCxBusinessUnitNameDisplayValue();
        	recordToUpdate.setCxBusinessUnitNameDisplayValue(newValue);
        	break;
        case "Cx_Business_Greenzone":
        	oldValue = recordToUpdate.getCxBusinessGreenzone();
        	recordToUpdate.setCxBusinessGreenzone(newValue);
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
        case "Interface_Description":
        	oldValue = recordToUpdate.getInterfaceDescription();
        	recordToUpdate.setInterfaceDescription(newValue);
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
        case "CBUSOL":
        	oldValue = recordToUpdate.getCbusol();
        	recordToUpdate.setCbusol(newValue);
        	break;
        case "Ops_Compliance_Contacts":
        	oldValue = recordToUpdate.getOpsComplianceContacts();
        	recordToUpdate.setOpsComplianceContacts(newValue);
        	break;
        case "Cw_V1_V2":
        	oldValue = recordToUpdate.getCwVersion();
        	recordToUpdate.setCwVersion(newValue);
        	break;
        case "GOM_Compliant":
        	oldValue = recordToUpdate.getGomCompliant();
        	recordToUpdate.setGomCompliant(newValue);
        	break;
        case "Cw_UAT_Contacts":
        	oldValue = recordToUpdate.getCwUatContacts();
        	recordToUpdate.setCwUatContacts(newValue);
        	break;
        case "Source_Tech_Contacts":
        	oldValue = recordToUpdate.getSourceTechContacts();
        	recordToUpdate.setSourceTechContacts(newValue);
        	break;
        case "Impact_to_Business":
        	oldValue = recordToUpdate.getImpactToBusiness();
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
        case "Product_Processor_Escalation_Contacts":
        	oldValue = recordToUpdate.getProductProcessorEscalationContacts();
        	recordToUpdate.setProductProcessorEscalationContacts(newValue);
        	break;
        case "Product_Processor_Group_DL":
        	oldValue = recordToUpdate.getProductProcessorGroupDl();
        	recordToUpdate.setProductProcessorGroupDl(newValue);
        	break;
        case "Product_Processor_SNOW_Group_Name":
        	oldValue = recordToUpdate.getProductProcessorSnowGroupName();
        	recordToUpdate.setProductProcessorSnowGroupName(newValue);
        	break;
        case "Product_Processor_SLA":
        	oldValue = recordToUpdate.getProductProcessorSla();
        	recordToUpdate.setProductProcessorSla(newValue);
        	break;
        case "Frequency":
        	oldValue = recordToUpdate.getFrequency();
        	recordToUpdate.setFrequency(newValue);
        	break;
        case "File_Scheduled_Date_and_Time":
        	oldValue = recordToUpdate.getFileScheduledDateAndTime();
        	recordToUpdate.setFileScheduledDateAndTime(newValue);
        	break;
        case "Avg_Volume":
        	oldValue = recordToUpdate.getAverageVolume();
        	recordToUpdate.setAverageVolume(newValue);
        	break;
        case "Avg_Runtime":
        	oldValue = recordToUpdate.getAverageRuntime();
        	recordToUpdate.setAverageRuntime(newValue);
        	break;
        case "File_2nd_Scheduled_Date_and_Time":
        	oldValue = recordToUpdate.getFile2ndScheduledDateAndTime();
        	recordToUpdate.setFile2ndScheduledDateAndTime(newValue);
        	break;
        }
        
    	if (oldValue.equalsIgnoreCase(newValue)) {
			return "Customer record not updated. The updated value is the old value.";
		}

    	String updateHistory = recordToUpdate.getUpdateHistory();
    	updateHistory = updateHistory + ", Record updated on " + new Timestamp(System.currentTimeMillis()) + " by " + requestor;
    	recordToUpdate.setUpdateHistory(updateHistory);
    	
        customerRecordRepository.save(recordToUpdate);
        //addInfoToUpdateHistory(recordToUpdate, field, newValue, requestor);
        
    	return "Customer record updated successfully";
    }

    @Override
    public String deleteRecord(Integer id) {
        customerRecordRepository.delete(id);
        return "Customer record deleted successfully";
    }
}
