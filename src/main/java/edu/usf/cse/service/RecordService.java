package edu.usf.cse.service;

import edu.usf.cse.model.BuDetails;
import edu.usf.cse.model.Record;
import edu.usf.cse.model.UpdatedRecord;

import java.util.List;

public interface RecordService {

    Record createRecord(List<String> fields, String requestor);

    String saveRecord(Record record);

    Record getRecordById(Integer id);

    List<Record> getRecords(String searchTerms, boolean exactMatch);

    List<Record> getRandomRecords(int numRandomRecords);

    String updateRecord(List<? extends UpdatedRecord> records);

    List<Record> getArchivedRecords(String searchTerms, boolean exactMatch);

    String deleteRecord(Integer id);

    List<Record> getArchive();

    String saveDeletedRecord(BuDetails buDetails, String requestor, String reason);

    String clearDeletedRecords(List<Integer> ids);

    String restoreDeletedRecord(Integer id, String requestor);

    List<Record> findDuplicateRecords(BuDetails detailsToMatch);
}
