package edu.usf.cse.model;

import java.util.List;

public class UpdatedTransactionRecord implements UpdatedRecord {

    private TransactionRecord record;

    private String requestor;

    private String reason;

    private List<String> updatedFields;

    @Override
    public Record getRecord() {
        return record;
    }

    public void setRecord(TransactionRecord record) {
        this.record = record;
    }

    @Override
    public String getRequestor() {
        return requestor;
    }

    @Override
    public void setRequestor(String requestor) {
        this.requestor = requestor;
    }

    @Override
    public List<String> getUpdatedFields() {
        return updatedFields;
    }

    @Override
    public void setUpdatedFields(List<String> updatedFields) {
        this.updatedFields = updatedFields;
    }

    @Override
    public void setReason(String newReason)
    {
        this.reason = newReason;
    }

    @Override
    public String getReason()
    {
        return this.reason;
    }

}
