package edu.usf.cse.dto;

import edu.usf.cse.model.BuDetails;

public interface CreatedRecord {

    BuDetails getBuDetails();

    String getSoeid();

    void setSoeid(String soeid);

}
