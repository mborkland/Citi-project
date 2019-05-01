package edu.usf.cse.service;

import edu.usf.cse.dto.CreatedRecord;
import edu.usf.cse.dto.UpdatedRecord;
import edu.usf.cse.model.*;
import edu.usf.cse.persistence.DeletedTransactionRecordRepository;
import edu.usf.cse.persistence.TransactionRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

@Component
public class TransactionRecordService extends RecordService {

    private TransactionRecordRepository transactionRecordRepository;

    private DeletedTransactionRecordRepository deletedTransactionRecordRepository;
    
    private EntityManager entityManager;

    private static final int numberOfFields = 42;

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
    public String createRecord(CreatedRecord createdRecord) {
        createRecordImpl(createdRecord, transactionRecordRepository);
        return "Transaction record created successfully";
    }

    @Override
    public Record getRecordById(Integer id) {
        return transactionRecordRepository.findOne(id);
    }

    @Override
    public List<Record> getRecords(String searchTerms, boolean or, boolean exactMatch) {
        return getRecordsImpl(RecordType.TRANSACTION, false, entityManager, searchTerms, searchableFields, or, exactMatch);
    }

    public List<Record> getRecentRecords(int numRecentRecords)  {
        return transactionRecordRepository.getRecentRecords(numRecentRecords);
    }

    @Override
    @Transactional
    public String updateRecords(List<? extends UpdatedRecord> updatedRecords) {
        updateRecordsImpl(updatedRecords, transactionRecordRepository);
        return "Transaction record(s) updated successfully";
    }

    @Override
    public List<Record> getArchivedRecords(String searchTerms, boolean or, boolean exactMatch) {
        return getRecordsImpl(RecordType.TRANSACTION, true, entityManager, searchTerms, searchableFields, or, exactMatch);
    }

    @Override
    public String deleteRecords(List<Integer> ids, String soeid, String reason) {
        deleteRecordsImpl(ids, soeid, reason, transactionRecordRepository, deletedTransactionRecordRepository);
        return "Transaction record(s) deleted and archived successfully";
    }

    @Override
    public List<Record> getArchive() {
        return getArchiveImpl(deletedTransactionRecordRepository);
    }

    @Override
    public String clearDeletedRecords(List<Integer> ids)
    {
        clearDeletedRecordsImpl(ids, deletedTransactionRecordRepository);
        return "Deleted record(s) cleared successfully";
    }

    @Override
    public String restoreDeletedRecords(List<Integer> ids, String soeid)
    {
        restoreDeletedRecordsImpl(ids, soeid, deletedTransactionRecordRepository, transactionRecordRepository);
        return "Deleted record(s) restored successfully";
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

            if (count >= (int) ((duplicateRecordPercentage / 100) * numberOfFields)) {
                duplicateRecords.add(record);
            }
        }

        return duplicateRecords;
    }

}