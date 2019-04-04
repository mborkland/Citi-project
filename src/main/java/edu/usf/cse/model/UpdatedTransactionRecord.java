package edu.usf.cse.model;

public class UpdatedTransactionRecord implements UpdatedRecord {

    private TransactionRecord record;

    private String requestor;

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = (TransactionRecord) record;
    }

    public String getRequestor() {
        return requestor;
    }

    public void setRequestor(String requestor) {
        this.requestor = requestor;
    }

}
