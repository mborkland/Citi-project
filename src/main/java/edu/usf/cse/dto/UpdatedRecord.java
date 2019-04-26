package edu.usf.cse.dto;

import edu.usf.cse.model.Record;

import java.util.List;

public interface UpdatedRecord {

    Record getRecord();

    String getSoeid();

    void setSoeid(String soeid);

    void setReason(String newReason);

    String getReason();

    List<UpdatedField> getUpdatedFields();

    void setUpdatedFields(List<UpdatedField> updatedFields);

}
