package edu.usf.cse.dto;

import edu.usf.cse.model.BuDetails;

public interface CreatedRecord {

    BuDetails getBuDetails();

    String getRequestor();

    void setRequestor(String requestor);

}
