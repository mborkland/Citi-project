package edu.usf.cse.service;

import edu.usf.cse.model.*;
import edu.usf.cse.persistence.CustomerRecordRepository;
import edu.usf.cse.persistence.DeletedCustomerRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;

@Component
public class CustomerRecordService implements RecordService {

    private CustomerRecordRepository customerRecordRepository;

    private DeletedCustomerRecordRepository deletedCustomerRecordRepository;

    private EntityManager entityManager;

    private static final char historyDelimiter = ';';

    private static final String searchDelimiter = ",";

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
    public String createRecord(List<String> fields, String requestor) {
        CxBuDetails cxBuDetails = new CxBuDetails();
        Iterator<String> iterator = fields.iterator();
        cxBuDetails.setCsiId(iterator.next());
        cxBuDetails.setCsInstance(iterator.next());
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
        cxBuDetails.setInterfaceAppId(iterator.next());
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
    public List<Record> getRecords(String searchTerms) {
        String[] searchTermsSplit = searchTerms.split(searchDelimiter);
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CustomerRecord> criteriaQuery = builder.createQuery(CustomerRecord.class);
        Root<CustomerRecord> root = criteriaQuery.from(CustomerRecord.class);

        List<Predicate> predicates = new ArrayList<>();
        for (String searchableField : searchableFields) {
            for (String searchTerm : searchTermsSplit) {
                predicates.add(builder.equal(root.get("buDetails").get(searchableField), searchTerm.trim()));

            }
        }

        Predicate orPredicate = builder.disjunction();
        orPredicate = builder.or(predicates.toArray(new Predicate[predicates.size()]));
        criteriaQuery.where(orPredicate);
        Query query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    public List<Record> getRandomRecords(int numRandomRecords)  {
        return customerRecordRepository.getRandomRecords(numRandomRecords);
    }

    @Override
    @Transactional
    public String updateRecord(Integer id, String field, String newValue, String requestor) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CustomerRecord> criteriaQuery = builder.createQuery(CustomerRecord.class);
        Root<CustomerRecord> queryRoot = criteriaQuery.from(CustomerRecord.class);
        criteriaQuery.select(queryRoot.get(field));
        criteriaQuery.where(builder.equal(queryRoot.get("id"), id));
        Query query = entityManager.createQuery(criteriaQuery);
        Object oldValue = query.getResultList().get(0);

        CriteriaUpdate<CustomerRecord> criteriaUpdate = builder.createCriteriaUpdate(CustomerRecord.class);
        Root<CustomerRecord> updateRoot = criteriaUpdate.from(CustomerRecord.class);
        StringBuilder updateHistory = new StringBuilder(customerRecordRepository.getUpdateHistory(id));
        updateHistory.append(historyDelimiter).append(field).append(" field changed from ").append(oldValue).append(" to ")
                .append(newValue).append(" on ").append(new Timestamp(System.currentTimeMillis())).append(" by ").append(requestor);

        criteriaUpdate.set(updateRoot.get(field), newValue);
        criteriaUpdate.set(updateRoot.get("updateHistory"), updateHistory.toString());
        criteriaUpdate.where(builder.equal(updateRoot.get("id"), id));
        entityManager.createQuery(criteriaUpdate).executeUpdate();

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
