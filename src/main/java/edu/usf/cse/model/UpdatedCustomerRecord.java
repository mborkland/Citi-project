package edu.usf.cse.model;

public class UpdatedCustomerRecord implements UpdatedRecord {

    private CustomerRecord record;

    private String requestor;

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = (CustomerRecord) record;
    }

    public String getRequestor() {
        return requestor;
    }

    public void setRequestor(String requestor) {
        this.requestor = requestor;
    }

}
