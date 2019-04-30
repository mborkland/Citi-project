package edu.usf.cse.service;

import edu.usf.cse.dto.CreatedRecord;
import edu.usf.cse.dto.UpdatedField;
import edu.usf.cse.dto.UpdatedRecord;
import edu.usf.cse.model.*;
import edu.usf.cse.persistence.CustomerRecordRepository;
import edu.usf.cse.persistence.DeletedCustomerRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;

@Component
public class CustomerRecordService extends RecordService {

    private CustomerRecordRepository customerRecordRepository;

    private DeletedCustomerRecordRepository deletedCustomerRecordRepository;

    private EntityManager entityManager;
    
    private static final int numberOfFields = 28;

    private static final String[] searchableFields = {
            "csiId", "csInstance", "businessId", "bizUnitId", "productId", "bizProdId",
            "cxScreeningBusinessUnitName", "rulesetMapped", "region", "country", "sector",
            "workflowInstance", "wfBusinessUnitNameDisplayValue"
    };

    @Autowired
    public CustomerRecordService(CustomerRecordRepository customerRecordRepository,
                                 DeletedCustomerRecordRepository deletedCustomerRecordRepository, EntityManager entityManager) {
        this.customerRecordRepository = customerRecordRepository;
        this.deletedCustomerRecordRepository = deletedCustomerRecordRepository;
        this.entityManager = entityManager;
    }

    @Override
    public String createRecord(CreatedRecord record) {
        CxBuDetails cxBuDetails = (CxBuDetails) record.getBuDetails();
        LocalDateTime now = LocalDateTime.now();
        cxBuDetails.setHistory("Record created on " + now.format(formatter) + " by " + record.getSoeid());
        CustomerRecord customerRecord = new CustomerRecord();
        customerRecord.setBuDetails(cxBuDetails);
        customerRecord.setCreationDate(Timestamp.valueOf(now));
        customerRecordRepository.save(customerRecord);
        return "Customer record created successfully";
    }

    @Override
    public Record getRecordById(Integer id) {
        return customerRecordRepository.findOne(id);
    }
    
    @Override
    public List<Record> getRecords(String searchTerms, boolean or, boolean exactMatch) {
        return getRecordsImpl(RecordType.CUSTOMER, false, entityManager, searchTerms, searchableFields, or, exactMatch);
    }

    public List<Record> getRecentRecords(int numRecentRecords)  {
        return customerRecordRepository.getRecentRecords(numRecentRecords);
    }

    @Override
    public List<Record> getArchivedRecords(String searchTerms, boolean or, boolean exactMatch) {
        return getRecordsImpl(RecordType.CUSTOMER, true, entityManager, searchTerms, searchableFields, or, exactMatch);
    }

    @Override
    @Transactional
    public String updateRecords(List<? extends UpdatedRecord> updatedRecords) {
        updateRecordsImpl(updatedRecords, customerRecordRepository);
        return "Customer record(s) updated successfully";
    }

    @Override
    public String deleteRecords(List<Integer> ids, String soeid, String reason) {
        for (Integer id : ids) {
            Record record = getRecordById(id);
            saveDeletedRecord(record.getBuDetails(), record.getCreationDate(), soeid, reason);
            customerRecordRepository.delete(id);
        }

        return "Customer record(s) deleted and archived successfully";
    }

    @Override
    public List<Record> getArchive() {
        List<Record> records = new ArrayList<>();
        for (Record record : deletedCustomerRecordRepository.findAll()) {
            records.add(record);
        }
        return records;
    }

    @Override
    public String saveDeletedRecord(BuDetails buDetails, Date creationDate, String soeid, String reason) {
        DeletedCustomerRecord deletedCustomerRecord = new DeletedCustomerRecord();
        deletedCustomerRecord.setBuDetails((CxBuDetails) buDetails);
        deletedCustomerRecord.setCreationDate(creationDate);
        LocalDateTime now = LocalDateTime.now();
        deletedCustomerRecord.setDeletionDetails("Record deleted on " + now.format(formatter) +
                " by " + soeid + ". Reason: " + reason);
        deletedCustomerRecordRepository.save(deletedCustomerRecord);
        return "Deleted record saved successfully";
    }

    @Override
    public String clearDeletedRecords(List<Integer> ids)
    {
        for (Integer id : ids) {
            deletedCustomerRecordRepository.delete(id);
        }

        return "Deleted record(s) cleared successfully";
    }

    @Override
    public String restoreDeletedRecords(List<Integer> ids, String soeid)
    {
        for (Integer id : ids) {
            DeletedCustomerRecord deletedCustomerRecord = deletedCustomerRecordRepository.findOne(id);
            String deletionDetails = deletedCustomerRecord.getDeletionDetails();
            CxBuDetails cxBuDetails = (CxBuDetails) deletedCustomerRecord.getBuDetails();
            Date creationDate = deletedCustomerRecord.getCreationDate();

            CustomerRecord customerRecord = new CustomerRecord();
            StringBuilder history = new StringBuilder(cxBuDetails.getHistory());
            LocalDateTime now = LocalDateTime.now();
            history.append(historyDelimiter).append(deletionDetails).append(historyDelimiter).append("Record restored on ")
                    .append(now.format(formatter)).append(" by ").append(soeid);
            cxBuDetails.setHistory(history.toString());
            customerRecord.setBuDetails(cxBuDetails);
            customerRecord.setCreationDate(creationDate);
            customerRecordRepository.save(customerRecord);
            deletedCustomerRecordRepository.delete(id);
        }

        return "Deleted record(s) restored successfully";
    }

    @Override
    public List<Record> findDuplicateRecords(BuDetails detailsToMatch)
    {
        List<Record> duplicateRecords = new ArrayList<>();
        Iterable<CustomerRecord> allRecords = customerRecordRepository.findAll();
        CxBuDetails recordToMatch = (CxBuDetails) detailsToMatch;

        for(Record record : allRecords) {
            int count = 0;
            CxBuDetails buDetails = (CxBuDetails) record.getBuDetails();

            if (buDetails.getCsiId().equalsIgnoreCase(recordToMatch.getCsiId())) {
                count++;
            }
            if (buDetails.getCsInstance().equalsIgnoreCase(recordToMatch.getCsInstance())) {
                count++;
            }
            if (buDetails.getBusinessId().equalsIgnoreCase(recordToMatch.getBusinessId())) {
                count++;
            }
            if (buDetails.getBizUnitId().equalsIgnoreCase(recordToMatch.getBizUnitId())) {
                count++;
            }
            if (buDetails.getProductId().equalsIgnoreCase(recordToMatch.getProductId())) {
                count++;
            }
            if (buDetails.getBizProdId().equalsIgnoreCase(recordToMatch.getBizProdId())) {
                count++;
            }
            if (buDetails.getCxScreeningBusinessUnitName().equalsIgnoreCase(recordToMatch.getCxScreeningBusinessUnitName())) {
                count++;
            }
            if (buDetails.getCxBusinessGreenzone().equalsIgnoreCase(recordToMatch.getCxBusinessGreenzone())) {
                count++;
            }
            if (buDetails.getRulesetMapped().equalsIgnoreCase(recordToMatch.getRulesetMapped())) {
                count++;
            }
            if (buDetails.getRegion().equalsIgnoreCase(recordToMatch.getRegion())) {
                count++;
            }
            if (buDetails.getCountry().equalsIgnoreCase(recordToMatch.getCountry())) {
                count++;
            }
            if (buDetails.getSector().equalsIgnoreCase(recordToMatch.getSector())) {
                count++;
            }
            if (buDetails.getWorkflowFlag() == recordToMatch.getWorkflowFlag()) {
                count++;
            }
            if (buDetails.getWorkflowInstance().equalsIgnoreCase(recordToMatch.getWorkflowInstance())) {
                count++;
            }
            if (buDetails.getWfBusinessUnitNameDisplayValue().equalsIgnoreCase(recordToMatch.getWfBusinessUnitNameDisplayValue())) {
                count++;
            }
            if (buDetails.getWfBusinessGreenzone().equalsIgnoreCase(recordToMatch.getWfBusinessGreenzone())) {
                count++;
            }
            if (buDetails.getConnectivityProtocol().equalsIgnoreCase(recordToMatch.getConnectivityProtocol())) {
                count++;
            }
            if (buDetails.getInterfaceAppId().equalsIgnoreCase(recordToMatch.getInterfaceAppId())) {
                count++;
            }
            if (buDetails.getInterfaceApplicationName().equalsIgnoreCase(recordToMatch.getInterfaceApplicationName())) {
                count++;
            }
            if (buDetails.getOperationEntity().equalsIgnoreCase(recordToMatch.getOperationEntity())) {
                count++;
            }
            if (buDetails.getOpsComplianceContacts().equalsIgnoreCase(recordToMatch.getOpsComplianceContacts())) {
                count++;
            }
            if (buDetails.getCwVersion().equalsIgnoreCase(recordToMatch.getCwVersion())) {
                count++;
            }
            if (buDetails.getGomCompliant() == recordToMatch.getGomCompliant()) {
                count++;
            }
            if (buDetails.getCwUatContactName().equalsIgnoreCase(recordToMatch.getCwUatContactName())) {
                count++;
            }
            if (buDetails.getSourceTechContact().equalsIgnoreCase(recordToMatch.getSourceTechContact())) {
                count++;
            }
            if (buDetails.getImpactToBusiness().equalsIgnoreCase(recordToMatch.getImpactToBusiness())) {
                count++;
            }
            if (buDetails.getBusinessEscalationPointOfContact().equalsIgnoreCase(recordToMatch.getBusinessEscalationPointOfContact())) {
                count++;
            }
            if (buDetails.getTimezone().equalsIgnoreCase(recordToMatch.getTimezone())) {
                count++;
            }

            if (count >= (int) ((duplicateRecordPercentage / 100) * numberOfFields)) {
                duplicateRecords.add(record);
            }
        }

        return duplicateRecords;
    }
}
