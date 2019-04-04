package edu.usf.cse.model;

public interface UpdatedRecord {

    Record getRecord();

    void setRecord(Record record);

    String getRequestor();

    void setRequestor(String requestor);

}
