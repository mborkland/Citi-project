package edu.usf.cse.service;

import edu.usf.cse.dto.CreatedRecord;
import edu.usf.cse.model.*;
import edu.usf.cse.persistence.DeletedTransactionRecordRepository;
import edu.usf.cse.persistence.TransactionRecordRepository;
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
public class TransactionRecordService implements RecordService {

    private TransactionRecordRepository transactionRecordRepository;

    private DeletedTransactionRecordRepository deletedTransactionRecordRepository;
    
    private EntityManager entityManager;

	private static final char historyDelimiter = ';';

    private static final String searchDelimiter = ",";

    private static final int numberOfFields = 40;

    private static final String[] searchableFields = {
            "businessId", "productId", "csiId", "uniqueProductId", "txScreeningBusinessUnitName",
            "rulesetMapped", "region", "country", "sector", "workflowInstance"
    };

    @Autowired
    public TransactionRecordService(TransactionRecordRepository transactionRecordRepository,
                                    DeletedTransactionRecordRepository deletedTransactionRecordRepository, EntityManager entityManager) {
        this.transactionRecordRepository = transactionRecordRepository;
        this.deletedTransactionRecordRepository = deletedTransactionRecordRepository;
        this.entityManager = entityManager;
    }

    @Override
    public String createRecord(CreatedRecord record) {
        TxBuDetails txBuDetails = (TxBuDetails) record.getBuDetails();
        txBuDetails.setHistory("Record created on " + new Timestamp(System.currentTimeMillis()) + " by " + record.getRequestor());
        TransactionRecord transactionRecord = new TransactionRecord();
        transactionRecord.setBuDetails(txBuDetails);
        transactionRecordRepository.save(transactionRecord);
        return "Transaction record created successfully";
    }

    @Override
    public Record getRecordById(Integer id) {
        return transactionRecordRepository.findOne(id);
    }

    @Override
    public List<Record> getRecords(String searchTerms, boolean any, boolean exactMatch) {
        String[] searchTermsSplit = searchTerms.split(searchDelimiter);
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TransactionRecord> criteriaQuery = builder.createQuery(TransactionRecord.class);
        Root<TransactionRecord> root = criteriaQuery.from(TransactionRecord.class);
        Query query = entityManager.createQuery(buildQuery(searchTermsSplit, any, exactMatch, builder, criteriaQuery, root));
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

    private CriteriaQuery<? extends Record> buildQuery(String[] searchTermsSplit, boolean any, boolean exactMatch, CriteriaBuilder builder,
                                                       CriteriaQuery<? extends Record> criteriaQuery, Root<? extends Record> root) {
        if (any) {
            List<Predicate> predicates = new ArrayList<>();
            for (String searchTerm : searchTermsSplit) {
                for (String searchableField : searchableFields) {
                    predicates.add(getMatchTypePredicate(exactMatch, searchableField, searchTerm, builder, root));
                }
            }

            criteriaQuery.where(builder.or(predicates.toArray(new Predicate[predicates.size()])));
        } else {
            List<Predicate> outerPredicates = new ArrayList<>();
            for (String searchTerm : searchTermsSplit) {
                List<Predicate> innerPredicates = new ArrayList<>();
                for (String searchableField : searchableFields) {
                    innerPredicates.add(getMatchTypePredicate(exactMatch, searchableField, searchTerm, builder, root));
                }

                outerPredicates.add(builder.or(innerPredicates.toArray(new Predicate[innerPredicates.size()])));
            }

            Predicate finalPredicate = outerPredicates.size() == 1 ? outerPredicates.get(0) :
                    builder.and(outerPredicates.toArray(new Predicate[outerPredicates.size()]));
            criteriaQuery.where(finalPredicate);
        }

        return criteriaQuery;
    }

    public List<Record> getRandomRecords(int numRandomRecords)  {
        return transactionRecordRepository.getRandomRecords(numRandomRecords);
    }

    @Override
    @Transactional
    public String updateRecord(List<? extends UpdatedRecord> records) {
        for (UpdatedRecord record : records) {
            TransactionRecord transactionRecord = (TransactionRecord) record.getRecord();
            String requestor = record.getRequestor();
            String reason = record.getReason();
            String updatedFields = String.join(", ", record.getUpdatedFields());
            StringBuilder history = new StringBuilder(((TxBuDetails) transactionRecord.getBuDetails()).getHistory());
            history.append(historyDelimiter).append(updatedFields).append(" fields updated on ")
                    .append(new Timestamp(System.currentTimeMillis())).append(" by ").append(requestor)
                    .append(" because ").append(reason);
            ((TxBuDetails) transactionRecord.getBuDetails()).setHistory(history.toString());
            transactionRecordRepository.save(transactionRecord);
        }

        return "Customer record(s) updated successfully";
    }

    @Override
    public List<Record> getArchivedRecords(String searchTerms, boolean any, boolean exactMatch) {
        String[] searchTermsSplit = searchTerms.split(searchDelimiter);
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DeletedTransactionRecord> criteriaQuery = builder.createQuery(DeletedTransactionRecord.class);
        Root<DeletedTransactionRecord> root = criteriaQuery.from(DeletedTransactionRecord.class);
        Query query = entityManager.createQuery(buildQuery(searchTermsSplit, any, exactMatch, builder, criteriaQuery, root));
        return query.getResultList();
    }

    @Override
    public String deleteRecord(Integer id) {
        transactionRecordRepository.delete(id);
        return "Transaction record deleted successfully";
    }

    @Override
    public List<Record> getArchive() {
        List<Record> records = new ArrayList<>();
        for (Record record : deletedTransactionRecordRepository.findAll()) {
            records.add(record);
        }
        return records;
    }

    @Override
    public String saveDeletedRecord(BuDetails buDetails, String requestor, String reason) {
        DeletedTransactionRecord deletedTransactionRecord = new DeletedTransactionRecord();
        deletedTransactionRecord.setBuDetails((TxBuDetails) buDetails);
        deletedTransactionRecord.setDeletionDetails("Record deleted on " + new Timestamp(System.currentTimeMillis()) +
                " by " + requestor + ". Reason: " + reason);
        deletedTransactionRecordRepository.save(deletedTransactionRecord);
        return "Deleted record saved successfully";
    }

    @Override
    public String clearDeletedRecords(List<Integer> ids)
    {
        for (Integer id : ids) {
            deletedTransactionRecordRepository.delete(id);
        }

        return "Deleted record(s) cleared successfully";
    }

    @Override
    public String restoreDeletedRecord(Integer id, String requestor)
    {
        DeletedTransactionRecord deletedTransactionRecord = deletedTransactionRecordRepository.findOne(id);
        String deletionDetails = deletedTransactionRecord.getDeletionDetails();
        TxBuDetails txBuDetails = (TxBuDetails) deletedTransactionRecord.getBuDetails();

        TransactionRecord transactionRecord = new TransactionRecord();
        StringBuilder history = new StringBuilder(txBuDetails.getHistory());
        history.append(historyDelimiter).append(deletionDetails).append(historyDelimiter)
                .append("Record restored on ").append(new Timestamp(System.currentTimeMillis())).append(" by ").append(requestor);
        txBuDetails.setHistory(history.toString());
        transactionRecord.setBuDetails(txBuDetails);
        transactionRecordRepository.save(transactionRecord);
        deletedTransactionRecordRepository.delete(id);
        return "Deleted record restored successfully";
    }

    @Override
    public List<Record> findDuplicateRecords(BuDetails detailsToMatch)
    {
        List<Record> duplicateRecords = new ArrayList<>();
        Iterable<TransactionRecord> allRecords = transactionRecordRepository.findAll();
        TxBuDetails recordToMatch = (TxBuDetails) detailsToMatch;

        for (Record record : allRecords) {
            int count = 0;
            TxBuDetails buDetails = (TxBuDetails) record.getBuDetails();

            if (buDetails.getBusinessId().equalsIgnoreCase(recordToMatch.getBusinessId())) {
                count++;
            }
            if (buDetails.getProductId().equalsIgnoreCase(recordToMatch.getProductId())) {
                count++;
            }
            if (buDetails.getCsiId().equalsIgnoreCase(recordToMatch.getCsiId())) {
                count++;
            }
            if (buDetails.getUniqueProductId().equalsIgnoreCase(recordToMatch.getUniqueProductId())) {
                count++;
            }
            if (buDetails.getTxScreeningBusinessUnitName().equalsIgnoreCase(recordToMatch.getTxScreeningBusinessUnitName())) {
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
            if (buDetails.getWfBusinessGreenzone().equalsIgnoreCase(recordToMatch.getWfBusinessGreenzone())) {
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
            if (buDetails.getConnectivityProtocol().equalsIgnoreCase(recordToMatch.getConnectivityProtocol())) {
                count++;
            }
            if (buDetails.getWorkflowOperationsContacts().equalsIgnoreCase(recordToMatch.getWorkflowOperationsContacts())) {
                count++;
            }
            if (buDetails.getSourceTechContacts().equalsIgnoreCase(recordToMatch.getSourceTechContacts())) {
                count++;
            }
            if (buDetails.getBusinessHotline() == recordToMatch.getBusinessHotline()) {
                count++;
            }
            if (buDetails.getBusinessEscalationPointOfContact().equalsIgnoreCase(recordToMatch.getBusinessEscalationPointOfContact())) {
                count++;
            }
            if (buDetails.getImpactToProductProcessor().equalsIgnoreCase(recordToMatch.getImpactToProductProcessor())) {
                count++;
            }
            if (buDetails.getProductProcessor().equalsIgnoreCase(recordToMatch.getProductProcessor())) {
                count++;
            }
            if (buDetails.getHotlineNumber().equalsIgnoreCase(recordToMatch.getHotlineNumber())) {
                count++;
            }
            if (buDetails.getEscalationPath1stLevelSupport().equalsIgnoreCase(recordToMatch.getEscalationPath1stLevelSupport())) {
                count++;
            }
            if (buDetails.getEscalationPath2ndLevelSupport().equalsIgnoreCase(recordToMatch.getEscalationPath2ndLevelSupport())) {
                count++;
            }
            if (buDetails.getFirstLevelEscalation().equalsIgnoreCase(recordToMatch.getFirstLevelEscalation())) {
                count++;
            }
            if (buDetails.getSecondLevelEscalation().equalsIgnoreCase(recordToMatch.getSecondLevelEscalation())) {
                count++;
            }
            if (buDetails.getProductProcessorGroupDl().equalsIgnoreCase(recordToMatch.getProductProcessorGroupDl())) {
                count++;
            }
            if (buDetails.getProductProcessorSnowGroupName().equalsIgnoreCase(recordToMatch.getProductProcessorSnowGroupName())) {
                count++;
            }
            if (buDetails.getProductProcessorScreeningResponseCutoffTime().equalsIgnoreCase(recordToMatch.getProductProcessorScreeningResponseCutoffTime())) {
                count++;
            }
            if (buDetails.getProductProcessorStandardGreenzones().equalsIgnoreCase(recordToMatch.getProductProcessorStandardGreenzones())) {
                count++;
            }
            if (buDetails.getInterfaceConnectivityDoc().equalsIgnoreCase(recordToMatch.getInterfaceConnectivityDoc())) {
                count++;
            }
            if (buDetails.getRetryMechanism().equalsIgnoreCase(recordToMatch.getRetryMechanism())) {
                count++;
            }
            if (buDetails.getDailyOnlineVolumesExpected().equalsIgnoreCase(recordToMatch.getDailyOnlineVolumesExpected())) {
                count++;
            }
            if (buDetails.getScheduleForRealtimeVolumes().equalsIgnoreCase(recordToMatch.getScheduleForRealtimeVolumes())) {
                count++;
            }
            if (buDetails.getBatchesOrPeaksForRealtimeVolumes().equalsIgnoreCase(recordToMatch.getBatchesOrPeaksForRealtimeVolumes())) {
                count++;
            }
            if (buDetails.getInitialScreeningResponseSla().equalsIgnoreCase(recordToMatch.getInitialScreeningResponseSla())) {
                count++;
            }
            if (buDetails.getThresholdSetForTimeouts().equalsIgnoreCase(recordToMatch.getThresholdSetForTimeouts())) {
                count++;
            }
            if (buDetails.getAnyBatchComponent() == recordToMatch.getAnyBatchComponent()) {
                count++;
            }
            if (buDetails.getWorkflowOperationsWorkSchedule().equalsIgnoreCase(recordToMatch.getWorkflowOperationsWorkSchedule())) {
                count++;
            }

            if (count >= numberOfFields / 2) {
                duplicateRecords.add(record);
            }
        }

        return duplicateRecords;
    }

}