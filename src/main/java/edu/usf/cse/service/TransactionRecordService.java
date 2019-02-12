package edu.usf.cse.service;

import edu.usf.cse.model.TransactionRecord;
import edu.usf.cse.persistence.TransactionRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

@Component
public class TransactionRecordService implements RecordService {

    private TransactionRecordRepository transactionRecordRepository;

    @Autowired
    public TransactionRecordService(TransactionRecordRepository transactionRecordRepository) {
        this.transactionRecordRepository = transactionRecordRepository;
    }

    @Override
    public String createRecord(List<String> fields) {
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

        transactionRecordRepository.save(transactionRecord);
        return "Transaction record created successfully";
    }
}