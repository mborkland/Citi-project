package edu.usf.cse.model;

import java.util.List;

public interface UpdatedRecord {

    Record getRecord();

    String getRequestor();

    void setRequestor(String requestor);

    List<String> getUpdatedFields();

    void setUpdatedFields(List<String> updatedFields);

}
