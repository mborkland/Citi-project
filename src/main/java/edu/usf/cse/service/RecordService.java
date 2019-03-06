package edu.usf.cse.service;

import edu.usf.cse.model.BuDetails;
import edu.usf.cse.model.CustomerRecord;
import edu.usf.cse.model.Record;
import edu.usf.cse.model.SearchParameter;

import java.util.List;

public interface RecordService {

    String createRecord(List<String> fields, String requestor);

    Record getRecordById(Integer id);

    List<Record> getRecords(List<SearchParameter> searchParameters);

    String updateRecord(Integer id, String field, String newValue, String requestor);

    String deleteRecord(Integer id);

    String saveDeletedRecord(BuDetails buDetails, String requestor);

    String clearDeletedRecords();
}
