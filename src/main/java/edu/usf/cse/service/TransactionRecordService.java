package edu.usf.cse.service;

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
    public String createRecord(List<String> fields, String requestor) {
        TxBuDetails txBuDetails = new TxBuDetails();
        Iterator<String> iterator = fields.iterator();
        txBuDetails.setBusinessId(iterator.next());
        txBuDetails.setProductId(iterator.next());
        txBuDetails.setCsiId(iterator.next());
        txBuDetails.setUniqueProductId(iterator.next());
        txBuDetails.setTxScreeningBusinessUnitName(iterator.next());
        txBuDetails.setRulesetMapped(iterator.next());
        txBuDetails.setRegion(iterator.next());
        txBuDetails.setCountry(iterator.next());
        txBuDetails.setSector(iterator.next());
        txBuDetails.setWorkflowFlag(Boolean.parseBoolean(iterator.next()));
        txBuDetails.setWorkflowInstance(iterator.next());
        txBuDetails.setWfBusinessGreenzone(iterator.next());
        txBuDetails.setInterfaceAppId(iterator.next());
        txBuDetails.setInterfaceApplicationName(iterator.next());
        txBuDetails.setOperationEntity(iterator.next());
        txBuDetails.setConnectivityProtocol(iterator.next());
        txBuDetails.setWorkflowOperationsContacts(iterator.next());
        txBuDetails.setSourceTechContacts(iterator.next());
        txBuDetails.setBusinessHotline(iterator.next());
        txBuDetails.setBusinessEscalationPointOfContact(iterator.next());
        txBuDetails.setImpactToProductProcessor(iterator.next());
        txBuDetails.setProductProcessor(iterator.next());
        txBuDetails.setHotlineNumber(iterator.next());
        txBuDetails.setEscalationPath1stLevelSupport(iterator.next());
        txBuDetails.setEscalationPath2ndLevelSupport(iterator.next());
        txBuDetails.setFirstLevelEscalation(iterator.next());
        txBuDetails.setSecondLevelEscalation(iterator.next());
        txBuDetails.setProductProcessorGroupDl(iterator.next());
        txBuDetails.setProductProcessorSnowGroupName(iterator.next());
        txBuDetails.setProductProcessorScreeningResponseCutoffTime(iterator.next());
        txBuDetails.setProductProcessorStandardGreenzones(iterator.next());
        txBuDetails.setInterfaceConnectivityDoc(iterator.next());
        txBuDetails.setRetryMechanism(iterator.next());
        txBuDetails.setDailyOnlineVolumesExpected(iterator.next());
        txBuDetails.setScheduleForRealtimeVolumes(iterator.next());
        txBuDetails.setBatchesOrPeaksForRealtimeVolumes(iterator.next());
        txBuDetails.setInitialScreeningResponseSla(iterator.next());
        txBuDetails.setThresholdSetForTimeouts(iterator.next());
        txBuDetails.setAnyBatchComponent(Boolean.parseBoolean(iterator.next()));
        txBuDetails.setWorkflowOperationsWorkSchedule(iterator.next());
        txBuDetails.setUpdateHistory("Record created on " + new Timestamp(System.currentTimeMillis()) + " by " + requestor);

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
    public List<Record> getRecords(String searchTerms) {
        String[] searchTermsSplit = searchTerms.split(searchDelimiter);
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TransactionRecord> criteriaQuery = builder.createQuery(TransactionRecord.class);
        Root<TransactionRecord> root = criteriaQuery.from(TransactionRecord.class);

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
        return transactionRecordRepository.getRandomRecords(numRandomRecords);
    }

	@Override
	@Transactional
	public String updateRecord(Integer id, String field, String newValue, String requestor) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TransactionRecord> criteriaQuery = builder.createQuery(TransactionRecord.class);
        Root<TransactionRecord> queryRoot = criteriaQuery.from(TransactionRecord.class);
        criteriaQuery.select(queryRoot.get(field));
        criteriaQuery.where(builder.equal(queryRoot.get("id"), id));
        Query query = entityManager.createQuery(criteriaQuery);
        Object oldValue = query.getResultList().get(0);

        CriteriaUpdate<TransactionRecord> criteria = builder.createCriteriaUpdate(TransactionRecord.class);
		Root<TransactionRecord> root = criteria.from(TransactionRecord.class);
		StringBuilder updateHistory = new StringBuilder(transactionRecordRepository.getUpdateHistory(id));
        updateHistory.append(historyDelimiter).append(field).append(" field changed from ").append(oldValue).append(" to ")
                .append(newValue).append(" on ").append(new Timestamp(System.currentTimeMillis())).append(" by ").append(requestor);

		criteria.set(root.get(field), newValue);
		criteria.set(root.get("updateHistory"), updateHistory.toString());
		criteria.where(builder.equal(root.get("id"), id));
		entityManager.createQuery(criteria).executeUpdate();

		return "Transaction record updated successfully";
	}

    @Override
    public String deleteRecord(Integer id) {
        transactionRecordRepository.delete(id);
        return "Transaction record deleted successfully";
    }

    @Override
    public String saveDeletedRecord(BuDetails buDetails, String requestor) {
        DeletedTransactionRecord deletedTransactionRecord = new DeletedTransactionRecord();
        deletedTransactionRecord.setBuDetails((TxBuDetails) buDetails);
        deletedTransactionRecord.setDeletionDetails("Record deleted on " + new Timestamp(System.currentTimeMillis()) +
                " by " + requestor);
        deletedTransactionRecordRepository.save(deletedTransactionRecord);
        return "Deleted record saved successfully";
    }

    @Override
    public String clearDeletedRecords()
    {
        deletedTransactionRecordRepository.deleteAll();
        return "Deleted records cleared successfully";
    }

    @Override
    public String restoreDeletedRecord(Integer id, String requestor)
    {
        DeletedTransactionRecord deletedTransactionRecord = deletedTransactionRecordRepository.findOne(id);
        String deletionDetails = deletedTransactionRecord.getDeletionDetails();
        TxBuDetails txBuDetails = (TxBuDetails) deletedTransactionRecord.getBuDetails();

        TransactionRecord transactionRecord = new TransactionRecord();
        StringBuilder updateHistory = new StringBuilder(txBuDetails.getUpdateHistory());
        updateHistory.append(historyDelimiter).append(deletionDetails).append(historyDelimiter)
                .append("Record restored on ").append(new Timestamp(System.currentTimeMillis())).append(" by ").append(requestor);
        txBuDetails.setUpdateHistory(updateHistory.toString());
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