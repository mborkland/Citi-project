package edu.usf.cse.service;

import edu.usf.cse.model.*;
import edu.usf.cse.model.TransactionRecord;
import edu.usf.cse.persistence.DeletedTransactionRecordRepository;
import edu.usf.cse.persistence.TransactionRecordRepository;
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
public class TransactionRecordService implements RecordService {

    private TransactionRecordRepository transactionRecordRepository;

    private DeletedTransactionRecordRepository deletedTransactionRecordRepository;
    
    private EntityManager entityManager;

	private static final char historyDelimiter = ';';

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
        txBuDetails.setCsiId(iterator.next());
        txBuDetails.setCsInstance(iterator.next());
        txBuDetails.setBusinessUnitId(iterator.next());
        txBuDetails.setBusinessProductId(iterator.next());
        txBuDetails.setCorrectedBuName(iterator.next());
        txBuDetails.setMxBusinessGreenzone(iterator.next());
        txBuDetails.setRulesetMapped(iterator.next());
        txBuDetails.setRegion(iterator.next());
        txBuDetails.setCountry(iterator.next());
        txBuDetails.setSector(iterator.next());
        txBuDetails.setWorkflowFlag(iterator.next());
        txBuDetails.setWorkflowInstance(iterator.next());
        txBuDetails.setWfBusinessUnitName(iterator.next());
        txBuDetails.setWfBusinessUnitNameDisplayValue(iterator.next());
        txBuDetails.setWfBusinessGreenzone(iterator.next());
        txBuDetails.setInterfaceAppId(iterator.next());
        txBuDetails.setInterfaceApplicationName(iterator.next());
        txBuDetails.setOperationEntity(iterator.next());
        txBuDetails.setCsWf(iterator.next());
        txBuDetails.setInterfaceDescription(iterator.next());
        txBuDetails.setWorkflowOperationsContacts(iterator.next());
        txBuDetails.setSourceTechContacts(iterator.next());
        txBuDetails.setImpactToBusiness(iterator.next());
        txBuDetails.setBusinessHotline(iterator.next());
        txBuDetails.setBusinessEscalationContacts(iterator.next());
        txBuDetails.setTimezone(iterator.next());
        txBuDetails.setImpactToProductProcessor(iterator.next());
        txBuDetails.setProductProcessorContacts(iterator.next());
        txBuDetails.setEscalationPath1stLevelContacts(iterator.next());
        txBuDetails.setEscalationPath2ndLevelContacts(iterator.next());
        txBuDetails.setEscalationPath1stLevelApplicationManager(iterator.next());
        txBuDetails.setEscalationPath2ndLevelApplicationManager(iterator.next());
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
        txBuDetails.setAnyBatchComponent(iterator.next());
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
    public List<Record> getRecords(List<SearchParameter> searchParameters) {
        RecordSpecification recordSpecification = new RecordSpecification(searchParameters);
        return transactionRecordRepository.findAll(recordSpecification);
    }

	@Override
	@Transactional
	public String updateRecord(Integer id, String field, String newValue, String requestor) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaUpdate<TransactionRecord> criteria = builder.createCriteriaUpdate(TransactionRecord.class);
		Root<TransactionRecord> root = criteria.from(TransactionRecord.class);
		StringBuilder updateHistory = new StringBuilder(transactionRecordRepository.getUpdateHistory(id));
		updateHistory.append(historyDelimiter).append("Record updated on ").append(new Timestamp(System.currentTimeMillis()))
				.append(" by ").append(requestor);

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
}