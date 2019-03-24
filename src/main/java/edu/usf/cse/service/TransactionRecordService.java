package edu.usf.cse.service;

import edu.usf.cse.model.*;
import edu.usf.cse.persistence.DeletedTransactionRecordRepository;
import edu.usf.cse.persistence.TransactionRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> createRecord(List<String> fields, String requestor) {
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
        txBuDetails.setContactsChecked(false);
        txBuDetails.setUpdateHistory("Record created on " + new Timestamp(System.currentTimeMillis()) + " by " + requestor);

        TransactionRecord transactionRecord = new TransactionRecord();
        transactionRecord.setBuDetails(txBuDetails);
        transactionRecordRepository.save(transactionRecord);
        return new ResponseEntity<String>("{\"result\":\"Transaction record created successfully\"}", HttpStatus.OK);

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
        criteriaQuery.select(root).distinct(true);

        List<Predicate> predicates = new ArrayList<>();
        for (String searchableField : searchableFields) {
            for (String searchTerm : searchTermsSplit) {
                predicates.add(builder.equal(root.get(searchableField), searchTerm));

            }
        }

        criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
        Query query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
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
}