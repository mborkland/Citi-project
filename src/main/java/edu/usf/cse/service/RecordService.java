package edu.usf.cse.service;

import edu.usf.cse.model.CustomerRecord;
import edu.usf.cse.model.Record;
import edu.usf.cse.model.SearchParameter;

import java.util.List;

public interface RecordService {

    String createRecord(List<String> fields, String requestor);

    List<Record> getRecords(List<SearchParameter> searchParameters);

    String updateRecord(Integer recordId, String field, String newValue, String requestor);

    String deleteRecord(Record record);
}
