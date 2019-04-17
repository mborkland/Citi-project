package edu.usf.cse.dto;

import edu.usf.cse.model.BuDetails;
import edu.usf.cse.model.CxBuDetails;

public class CreatedCustomerRecord implements CreatedRecord {

    private CxBuDetails buDetails;

    private String soeid;

    @Override
    public BuDetails getBuDetails() {
        return buDetails;
    }

    public void setBuDetails(CxBuDetails buDetails) {
        this.buDetails = buDetails;
    }

    @Override
    public String getSoeid() {
        return soeid;
    }

    @Override
    public void setSoeid(String soeid) {
        this.soeid = soeid;
    }
}
