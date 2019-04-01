package edu.usf.cse.service;

import edu.usf.cse.model.BuDetails;
import edu.usf.cse.model.Record;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RecordService {

    String createRecord(List<String> fields, String requestor);

    Record getRecordById(Integer id);

    List<Record> getRecords(String searchTerms);

    List<Record> getRandomRecords(int numRandomRecords);

    String updateRecord(Integer id, String field, String newValue, String requestor);

    String deleteRecord(Integer id);

    String saveDeletedRecord(BuDetails buDetails, String requestor);

    String clearDeletedRecords();

    String restoreDeletedRecord(Integer id, String requestor);

    List<BuDetails> findDuplicateRecords(BuDetails detailsToMatch);
}
