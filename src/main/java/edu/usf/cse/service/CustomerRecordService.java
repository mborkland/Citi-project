package edu.usf.cse.service;

import edu.usf.cse.model.*;
import edu.usf.cse.persistence.CustomerRecordRepository;
import edu.usf.cse.persistence.DeletedCustomerRecordRepository;
import edu.usf.cse.specification.RecordSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

@Component
public class CustomerRecordService implements RecordService {

    private CustomerRecordRepository customerRecordRepository;

    private DeletedCustomerRecordRepository deletedCustomerRecordRepository;

    private EntityManager entityManager;

    private static final char historyDelimiter = ';';

    @Autowired
    public CustomerRecordService(CustomerRecordRepository customerRecordRepository,
                                 DeletedCustomerRecordRepository deletedCustomerRecordRepository, EntityManager entityManager) {
        this.customerRecordRepository = customerRecordRepository;
        this.deletedCustomerRecordRepository = deletedCustomerRecordRepository;
        this.entityManager = entityManager;
    }

    @Override
    public String createRecord(List<String> fields, String requestor) {
        CxBuDetails cxBuDetails = new CxBuDetails();
        Iterator<String> iterator = fields.iterator();
        cxBuDetails.setCsiId(iterator.next());
        cxBuDetails.setCsInstance(Integer.parseInt(iterator.next()));
        cxBuDetails.setBusinessId(iterator.next());
        cxBuDetails.setBizUnitId(iterator.next());
        cxBuDetails.setProductId(iterator.next());
        cxBuDetails.setBizProdId(iterator.next());
        cxBuDetails.setCxScreeningBusinessUnitName(iterator.next());
        cxBuDetails.setCxBusinessGreenzone(iterator.next());
        cxBuDetails.setRulesetMapped(iterator.next());
        cxBuDetails.setRegion(iterator.next());
        cxBuDetails.setCountry(iterator.next());
        cxBuDetails.setSector(iterator.next());
        cxBuDetails.setWorkflowFlag(Boolean.parseBoolean(iterator.next()));
        cxBuDetails.setWorkflowInstance(iterator.next());
        cxBuDetails.setWfBusinessUnitNameDisplayValue(iterator.next());
        cxBuDetails.setWfBusinessGreenzone(iterator.next());
        cxBuDetails.setConnectivityProtocol(iterator.next());
        cxBuDetails.setInterfaceAppId(Integer.parseInt(iterator.next()));
        cxBuDetails.setInterfaceApplicationName(iterator.next());
        cxBuDetails.setOperationEntity(iterator.next());
        cxBuDetails.setOpsComplianceContacts(iterator.next());
        cxBuDetails.setCwVersion(iterator.next());
        cxBuDetails.setGomCompliant(Boolean.parseBoolean(iterator.next()));
        cxBuDetails.setCwUatContactName(iterator.next());
        cxBuDetails.setSourceTechContact(iterator.next());
        cxBuDetails.setImpactToBusiness(iterator.next());
        cxBuDetails.setBusinessEscalationPointOfContact(iterator.next());
        cxBuDetails.setTimezone(iterator.next());
        cxBuDetails.setContactsChecked(false);
        cxBuDetails.setUpdateHistory("Record created on " + new Timestamp(System.currentTimeMillis()) + " by " + requestor);

        CustomerRecord customerRecord = new CustomerRecord();
        customerRecord.setBuDetails(cxBuDetails);
        customerRecordRepository.save(customerRecord);
        return "Customer record created successfully";
    }

    @Override
    public Record getRecordById(Integer id) {
        return customerRecordRepository.findOne(id);
    }
    
    @Override
    public List<Record> getRecords(List<SearchParameter> searchParameters) {
        RecordSpecification recordSpecification = new RecordSpecification(searchParameters);
        return customerRecordRepository.findAll(recordSpecification);
    }

    @Override
    @Transactional
    public String updateRecord(Integer id, String field, String newValue, String requestor) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<CustomerRecord> criteria = builder.createCriteriaUpdate(CustomerRecord.class);
        Root<CustomerRecord> root = criteria.from(CustomerRecord.class);
        StringBuilder updateHistory = new StringBuilder(customerRecordRepository.getUpdateHistory(id));
        updateHistory.append(historyDelimiter).append("Record updated on ").append(new Timestamp(System.currentTimeMillis()))
                .append(" by ").append(requestor);

        criteria.set(root.get(field), newValue);
        criteria.set(root.get("updateHistory"), updateHistory.toString());
        criteria.where(builder.equal(root.get("id"), id));
        entityManager.createQuery(criteria).executeUpdate();

        return "Customer record updated successfully";
    }

    @Override
    public String deleteRecord(Integer id) {
        customerRecordRepository.delete(id);
        return "Customer record deleted successfully";
    }

    @Override
    public String saveDeletedRecord(BuDetails buDetails, String requestor) {
        DeletedCustomerRecord deletedCustomerRecord = new DeletedCustomerRecord();
        deletedCustomerRecord.setBuDetails((CxBuDetails) buDetails);
        deletedCustomerRecord.setDeletionDetails("Record deleted on " + new Timestamp(System.currentTimeMillis()) +
                " by " + requestor);
        deletedCustomerRecordRepository.save(deletedCustomerRecord);
        return "Deleted record saved successfully";
    }

    @Override
    public String clearDeletedRecords()
    {
        deletedCustomerRecordRepository.deleteAll();
        return "Deleted records cleared successfully";
    }

    @Override
    public String restoreDeletedRecord(Integer id, String requestor)
    {
        DeletedCustomerRecord deletedCustomerRecord = deletedCustomerRecordRepository.findOne(id);
        String deletionDetails = deletedCustomerRecord.getDeletionDetails();
        CxBuDetails cxBuDetails = (CxBuDetails) deletedCustomerRecord.getBuDetails();

        CustomerRecord customerRecord = new CustomerRecord();
        StringBuilder updateHistory = new StringBuilder(cxBuDetails.getUpdateHistory());
        updateHistory.append(historyDelimiter).append(deletionDetails).append(historyDelimiter).append("Record restored on ")
                .append(new Timestamp(System.currentTimeMillis())).append(" by ").append(requestor);
        cxBuDetails.setUpdateHistory(updateHistory.toString());
        customerRecord.setBuDetails(cxBuDetails);
        customerRecordRepository.save(customerRecord);
        deletedCustomerRecordRepository.delete(id);
        return "Deleted record restored successfully";
    }
}
