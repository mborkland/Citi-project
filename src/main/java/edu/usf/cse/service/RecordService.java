package edu.usf.cse.service;

import edu.usf.cse.model.Record;
import edu.usf.cse.model.SearchParameter;

import java.util.List;

public interface RecordService {

    String createRecord(List<String> fields);

    Record getRecord(List<SearchParameter> searchParameters);

    String updateRecord(Record record, String field, String newValue);

    String deleteRecord(Record record);
}
