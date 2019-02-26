package edu.usf.cse.service;

import edu.usf.cse.model.TransactionRecord;
import edu.usf.cse.model.Record;
import edu.usf.cse.model.SearchParameter;
import edu.usf.cse.model.TransactionRecord;
//import edu.usf.cse.model.TransactionRecordUpdateHistory;
import edu.usf.cse.persistence.TransactionRecordRepository;
//import edu.usf.cse.persistence.TransactionRecordUpdateHistoryRepository;
import edu.usf.cse.specification.RecordSpecification;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

@Component
public class TransactionRecordService implements RecordService {

    private TransactionRecordRepository transactionRecordRepository;
    
    private EntityManager entityManager;

	private static final char historyDelimiter = ';';

    @Autowired
    public TransactionRecordService(TransactionRecordRepository transactionRecordRepository, EntityManager entityManager) {
        this.transactionRecordRepository = transactionRecordRepository;
        this.entityManager = entityManager;
    }

    @Override
    public String createRecord(List<String> fields, String requestor) {
        TransactionRecord transactionRecord = new TransactionRecord();
        Iterator<String> iterator = fields.iterator();
        transactionRecord.setCsiId(iterator.next());
        transactionRecord.setCsInstance(iterator.next());
        transactionRecord.setBusinessUnitId(iterator.next());
        transactionRecord.setBusinessProductId(iterator.next());
        transactionRecord.setCorrectedBuName(iterator.next());
        transactionRecord.setMxBusinessGreenzone(iterator.next());
        transactionRecord.setRulesetMapped(iterator.next());
        transactionRecord.setRegion(iterator.next());
        transactionRecord.setCountry(iterator.next());
        transactionRecord.setSector(iterator.next());
        transactionRecord.setWorkflowFlag(iterator.next());
        transactionRecord.setWorkflowInstance(iterator.next());
        transactionRecord.setWfBusinessUnitName(iterator.next());
        transactionRecord.setWfBusinessUnitNameDisplayValue(iterator.next());
        transactionRecord.setWfBusinessGreenzone(iterator.next());
        transactionRecord.setInterfaceAppId(iterator.next());
        transactionRecord.setInterfaceApplicationName(iterator.next());
        transactionRecord.setOperationEntity(iterator.next());
        transactionRecord.setCsWf(iterator.next());
        transactionRecord.setInterfaceDescription(iterator.next());
        transactionRecord.setWorkflowOperationsContacts(iterator.next());
        transactionRecord.setSourceTechContacts(iterator.next());
        transactionRecord.setImpactToBusiness(iterator.next());
        transactionRecord.setBusinessHotline(iterator.next());
        transactionRecord.setBusinessEscalationContacts(iterator.next());
        transactionRecord.setTimezone(iterator.next());
        transactionRecord.setImpactToProductProcessor(iterator.next());
        transactionRecord.setProductProcessorContacts(iterator.next());
        transactionRecord.setEscalationPath1stLevelContacts(iterator.next());
        transactionRecord.setEscalationPath2ndLevelContacts(iterator.next());
        transactionRecord.setEscalationPath1stLevelApplicationManager(iterator.next());
        transactionRecord.setEscalationPath2ndLevelApplicationManager(iterator.next());
        transactionRecord.setProductProcessorGroupDl(iterator.next());
        transactionRecord.setProductProcessorSnowGroupName(iterator.next());
        transactionRecord.setProductProcessorScreeningResponseCutoffTime(iterator.next());
        transactionRecord.setProductProcessorStandardGreenzones(iterator.next());
        transactionRecord.setInterfaceConnectivityDoc(iterator.next());
        transactionRecord.setRetryMechanism(iterator.next());
        transactionRecord.setDailyOnlineVolumesExpected(iterator.next());
        transactionRecord.setScheduleForRealtimeVolumes(iterator.next());
        transactionRecord.setBatchesOrPeaksForRealtimeVolumes(iterator.next());
        transactionRecord.setInitialScreeningResponseSla(iterator.next());
        transactionRecord.setThresholdSetForTimeouts(iterator.next());
        transactionRecord.setAnyBatchComponent(iterator.next());
        transactionRecord.setWorkflowOperationsWorkSchedule(iterator.next());
        transactionRecord.setUpdateHistory("Record created on " + new Timestamp(System.currentTimeMillis()) + " by " + requestor);
        
        transactionRecordRepository.save(transactionRecord);
        //addCreatedInfoToUpdateHistory(transactionRecord, fields, requestor);
        return "Transaction record created successfully";
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
}