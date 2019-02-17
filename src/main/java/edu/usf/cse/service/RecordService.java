package edu.usf.cse.service;

import edu.usf.cse.model.Record;
import edu.usf.cse.model.SearchParameter;

import java.util.List;

public interface RecordService {

    String createRecord(List<String> fields);

    List<Record> getRecords(List<SearchParameter> searchParameters);

    String updateRecord(Record record, String field, String newValue);

    String deleteRecord(Integer record);
}
