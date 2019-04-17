package edu.usf.cse.model;

import java.util.List;

public class UpdatedCustomerRecord implements UpdatedRecord {

    private CustomerRecord record;

    private String soeid;

    private String reason;

    private List<String> updatedFields;

    @Override
    public Record getRecord() {
        return record;
    }

    public void setRecord(CustomerRecord record) {
        this.record = record;
    }

    @Override
    public String getSoeid() {
        return soeid;
    }

    @Override
    public void setSoeid(String soeid) {
        this.soeid = soeid;
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
