package edu.usf.cse.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.usf.cse.model.Record;
import edu.usf.cse.model.TransactionRecord;

import java.util.List;

public class UpdatedTransactionRecord implements UpdatedRecord {

    private TransactionRecord record;

    private String soeid;

    private String reason;

    @JsonProperty("updatedFields")
    private List<UpdatedField> updatedFields;

    @Override
    public Record getRecord() {
        return record;
    }

    public void setRecord(TransactionRecord record) {
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
    public List<UpdatedField> getUpdatedFields() {
        return updatedFields;
    }

    @Override
    public void setUpdatedFields(List<UpdatedField> updatedFields) {
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
