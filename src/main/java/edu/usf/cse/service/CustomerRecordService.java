package edu.usf.cse.service;

import edu.usf.cse.model.CustomerRecord;
import edu.usf.cse.model.Record;
import edu.usf.cse.model.SearchParameter;
import edu.usf.cse.model.CustomerRecordUpdateHistory;
import edu.usf.cse.persistence.CustomerRecordRepository;
import edu.usf.cse.persistence.CustomerRecordUpdateHistoryRepository;
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
    private CustomerRecordUpdateHistoryRepository customerRecordUpdateHistoryRepository;

    @Autowired
    public CustomerRecordService(CustomerRecordRepository customerRecordRepository, CustomerRecordUpdateHistoryRepository customerRecordUpdateHistoryRepository) {
        this.customerRecordRepository = customerRecordRepository;
        this.customerRecordUpdateHistoryRepository = customerRecordUpdateHistoryRepository;
    }

    @Override
    public String createRecord(List<String> fields) {
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

        customerRecordRepository.save(customerRecord);
        return "Customer record created successfully";
    }

    @Override
    public List<Record> getRecords(List<SearchParameter> searchParameters) {
        RecordSpecification recordSpecification = new RecordSpecification(searchParameters);
        return customerRecordRepository.findAll(recordSpecification);
    }

    @Override
    public String updateRecord(Record record, String field, String newValue, String requestor) {
        // TODO: add logic to update the given field of the given record with new value
        CustomerRecord recordToUpdate = customerRecordRepository.findOne(record.getId());
        
    	CustomerRecordUpdateHistory updateInfo = new CustomerRecordUpdateHistory();
        updateInfo.setId(record.getId());
        updateInfo.setCsiId(record.getCsiId());
        updateInfo.setCsInstance(record.getCsInstance());
        updateInfo.setFieldName(field);
        updateInfo.setNewValue(newValue);
        updateInfo.setRequestor(requestor);
        updateInfo.setTimeOfChange(new Timestamp(System.currentTimeMillis()));
    	
    	switch(field) {
        case "CSI_ID": 
        	recordToUpdate.setCsiId(newValue);
        	break;
        case "CS_Instance":
        	recordToUpdate.setCsInstance(newValue);
        	break;
        case "BUSINESS_ID":
        	recordToUpdate.setBusinessId(newValue);
        	break;
        case "BIZ_UNIT_ID":
        	recordToUpdate.setBizUnitId(newValue);
        	break;
        case "PRODUCT_ID":
        	recordToUpdate.setProductId(newValue);
        	break;
        case "BIZ_PROD_ID":
        	recordToUpdate.setBizProdId(newValue);
        	break;
        case "Cx_Business_Unit_Name":
        	recordToUpdate.setCxBusinessUnitName(newValue);
        	break;
        case "Cx_Business_Unit_Name_Display_Value":
        	recordToUpdate.setCxBusinessUnitNameDisplayValue(newValue);
        	break;
        case "Cx_Business_Greenzone":
        	recordToUpdate.setCxBusinessGreenzone(newValue);
        	break;
        case "Ruleset_Mapped":
        	recordToUpdate.setRulesetMapped(newValue);
        	break;
        case "REGION":
        	recordToUpdate.setRegion(newValue);
        	break;
        case "COUNTRY":
        	recordToUpdate.setCountry(newValue);
        	break;
        case "SECTOR":
        	recordToUpdate.setSector(newValue);
        	break;
        case "Workflow_Flag":
        	recordToUpdate.setWorkflowFlag(newValue);
        	break;
        case "Workflow_Instance":
        	recordToUpdate.setWorkflowInstance(newValue);
        	break;
        case "WF_Business_Unit_Name":
        	recordToUpdate.setWfBusinessUnitName(newValue);
        	break;
        case "WF_Business_Unit_Name_Display_Value":
        	recordToUpdate.setWfBusinessUnitNameDisplayValue(newValue);
        	break;
        case "WF_Business_Greenzone":
        	recordToUpdate.setWfBusinessGreenzone(newValue);
        	break;
        case "Interface_Description":
        	recordToUpdate.setInterfaceDescription(newValue);
        	break;
        case "Interface_App_ID":
        	recordToUpdate.setInterfaceAppId(newValue);
        	break;
        case "Interface_Application_Name":
        	recordToUpdate.setInterfaceApplicationName(newValue);
        	break;
        case "Operation_Entity":
        	recordToUpdate.setOperationEntity(newValue);
        	break;
        case "CBUSOL":
        	recordToUpdate.setCbusol(newValue);
        	break;
        case "Ops_Compliance_Contacts":
        	recordToUpdate.setOpsComplianceContacts(newValue);
        	break;
        case "Cw_V1_V2":
        	recordToUpdate.setCwVersion(newValue);
        	break;
        case "GOM_Compliant":
        	recordToUpdate.setGomCompliant(newValue);
        	break;
        case "Cw_UAT_Contacts":
        	recordToUpdate.setCwUatContacts(newValue);
        	break;
        case "Source_Tech_Contacts":
        	recordToUpdate.setSourceTechContacts(newValue);
        	break;
        case "Impact_to_Business":
        	recordToUpdate.setImpactToBusiness(newValue);
        	break;
        case "Business_Escalation_Contacts":
        	recordToUpdate.setBusinessEscalationContacts(newValue);
        	break;
        case "Timezone":
        	recordToUpdate.setTimezone(newValue);
        	break;
        case "Impact_to_Product_Processor":
        	recordToUpdate.setImpactToProductProcessor(newValue);
        	break;
        case "Product_Processor_Escalation_Contacts":
        	recordToUpdate.setProductProcessorEscalationContacts(newValue);
        	break;
        case "Product_Processor_Group_DL":
        	recordToUpdate.setProductProcessorGroupDl(newValue);
        	break;
        case "Product_Processor_SNOW_Group_Name":
        	recordToUpdate.setProductProcessorSnowGroupName(newValue);
        	break;
        case "Product_Processor_SLA":
        	recordToUpdate.setProductProcessorSla(newValue);
        	break;
        case "Frequency":
        	recordToUpdate.setFrequency(newValue);
        	break;
        case "File_Scheduled_Date_and_Time":
        	recordToUpdate.setFileScheduledDateAndTime(newValue);
        	break;
        case "Avg_Volume":
        	recordToUpdate.setAverageVolume(newValue);
        	break;
        case "Avg_Runtime":
        	recordToUpdate.setAverageRuntime(newValue);
        	break;
        case "File_2nd_Scheduled_Date_and_Time":
        	recordToUpdate.setFile2ndScheduledDateAndTime(newValue);
        	break;
        }
        
        customerRecordRepository.save(recordToUpdate);
        customerRecordUpdateHistoryRepository.save(updateInfo);
        
    	return "Customer record updated successfully";
    }

    @Override
    public String deleteRecord(Record record) {
        // TODO: add logic to delete the given record
        return "Customer record deleted successfully";
    }
}
