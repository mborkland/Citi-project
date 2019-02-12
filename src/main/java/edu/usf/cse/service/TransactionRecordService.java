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

    public String createRecord(List<String> fields) {
        TransactionRecord transactionRecord = new TransactionRecord();
        Iterator<String> iterator = fields.iterator();
        // TODO: set field values

        transactionRecordRepository.save(transactionRecord);
        return "Transaction record created successfully";
    }
}
