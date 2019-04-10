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
        cxBuDetails.setHistory("Record created on " + new Timestamp(System.currentTimeMillis()) + " by " + requestor);

        if(findDuplicateRecords((BuDetails) cxBuDetails).size() > 0)
            return "Duplicate customer records found";

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
    public List<Record> getRecords(String searchTerms, boolean any, boolean exactMatch) {
        String[] searchTermsSplit = searchTerms.split(searchDelimiter);
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CustomerRecord> criteriaQuery = builder.createQuery(CustomerRecord.class);
        Root<CustomerRecord> root = criteriaQuery.from(CustomerRecord.class);
        criteriaQuery.where(getFinalPredicate(searchTermsSplit, any, exactMatch, builder, root));
        Query query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    private Predicate getMatchTypePredicate(boolean exactMatch, String searchableField, String searchTerm,
                                            CriteriaBuilder builder, Root<? extends Record> root) {
        if (exactMatch) {
            return builder.equal(root.get("buDetails").get(searchableField), searchTerm.trim());
        } else {
            Expression<Integer> index = builder.locate(root.get("buDetails").get(searchableField), searchTerm.trim());
            return builder.notEqual(index, 0);
        }
    }

    private Predicate getFinalPredicate(String[] searchTermsSplit, boolean any, boolean exactMatch, CriteriaBuilder builder,
                                     Root<? extends Record> root) {
        if (any) {
            List<Predicate> predicates = new ArrayList<>();
            for (String searchTerm : searchTermsSplit) {
                for (String searchableField : searchableFields) {
                    predicates.add(getMatchTypePredicate(exactMatch, searchableField, searchTerm, builder, root));
                }
            }

            return builder.or(predicates.toArray(new Predicate[0]));
        } else {
            List<Predicate> outerPredicates = new ArrayList<>();
            for (String searchTerm : searchTermsSplit) {
                List<Predicate> innerPredicates = new ArrayList<>();
                for (String searchableField : searchableFields) {
                    innerPredicates.add(getMatchTypePredicate(exactMatch, searchableField, searchTerm, builder, root));
                }

                outerPredicates.add(builder.or(innerPredicates.toArray(new Predicate[0])));
            }

            return outerPredicates.size() == 1 ? outerPredicates.get(0) : builder.and(outerPredicates.toArray(new Predicate[0]));
        }
    }

    public List<Record> getRandomRecords(int numRandomRecords)  {
        return customerRecordRepository.getRandomRecords(numRandomRecords);
    }

    @Override
    public List<Record> getArchivedRecords(String searchTerms, boolean any, boolean exactMatch) {
        String[] searchTermsSplit = searchTerms.split(searchDelimiter);
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DeletedCustomerRecord> criteriaQuery = builder.createQuery(DeletedCustomerRecord.class);
        Root<DeletedCustomerRecord> root = criteriaQuery.from(DeletedCustomerRecord.class);
        criteriaQuery.where(getFinalPredicate(searchTermsSplit, any, exactMatch, builder, root));
        Query query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    @Transactional
    public String updateRecord(List<? extends UpdatedRecord> records) {
        for (UpdatedRecord record : records) {
            CustomerRecord customerRecord = (CustomerRecord) record.getRecord();
            String requestor = record.getRequestor();
            String reason = record.getReason();
            String updatedFields = String.join(", ", record.getUpdatedFields());
            StringBuilder history = new StringBuilder(((CxBuDetails) customerRecord.getBuDetails()).getHistory());
            history.append(historyDelimiter).append(updatedFields).append(" fields updated on ")
                    .append(new Timestamp(System.currentTimeMillis())).append(" by ").append(requestor)
                    .append(" because ").append(reason);
            ((CxBuDetails) customerRecord.getBuDetails()).setHistory(history.toString());
            customerRecordRepository.save(customerRecord);
        }

        return "Customer record(s) updated successfully";
    }

    @Override
    public String deleteRecord(Integer id) {
        customerRecordRepository.delete(id);
        return "Customer record deleted successfully";
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
    public String saveDeletedRecord(BuDetails buDetails, String requestor, String reason) {
        DeletedCustomerRecord deletedCustomerRecord = new DeletedCustomerRecord();
        deletedCustomerRecord.setBuDetails((CxBuDetails) buDetails);
        deletedCustomerRecord.setDeletionDetails("Record deleted on " + new Timestamp(System.currentTimeMillis()) +
                " by " + requestor + ". Reason: " + reason);
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
    public String restoreDeletedRecord(Integer id, String requestor)
    {
        DeletedCustomerRecord deletedCustomerRecord = deletedCustomerRecordRepository.findOne(id);
        String deletionDetails = deletedCustomerRecord.getDeletionDetails();
        CxBuDetails cxBuDetails = (CxBuDetails) deletedCustomerRecord.getBuDetails();

        CustomerRecord customerRecord = new CustomerRecord();
        StringBuilder history = new StringBuilder(cxBuDetails.getHistory());
        history.append(historyDelimiter).append(deletionDetails).append(historyDelimiter).append("Record restored on ")
                .append(new Timestamp(System.currentTimeMillis())).append(" by ").append(requestor);
        cxBuDetails.setHistory(history.toString());
        customerRecord.setBuDetails(cxBuDetails);
        customerRecordRepository.save(customerRecord);
        deletedCustomerRecordRepository.delete(id);
        return "Deleted record restored successfully";
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

            if (count >= numberOfFields / 2) {
                duplicateRecords.add(record);
            }
        }

        return duplicateRecords;
    }
}
