package edu.usf.cse.service;

import edu.usf.cse.dto.CreatedRecord;
import edu.usf.cse.model.BuDetails;
import edu.usf.cse.model.Record;
import edu.usf.cse.dto.UpdatedRecord;

import java.util.Date;
import java.util.List;

public interface RecordService {

    String createRecord(CreatedRecord record);

    Record getRecordById(Integer id);

    List<Record> getRecords(String searchTerms, boolean or, boolean exactMatch);

    List<Record> getRecentRecords(int numRandomRecords);

    String updateRecord(List<? extends UpdatedRecord> records);

    List<Record> getArchivedRecords(String searchTerms, boolean or, boolean exactMatch);

    String deleteRecords(List<Integer> ids, String soeid, String reason);

    List<Record> getArchive();

    String saveDeletedRecord(BuDetails buDetails, Date creationDate, String soeid, String reason);

    String clearDeletedRecords(List<Integer> ids);

    String restoreDeletedRecords(List<Integer> ids, String soeid);

    List<Record> findDuplicateRecords(BuDetails detailsToMatch);
}
