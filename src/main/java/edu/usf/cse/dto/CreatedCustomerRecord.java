package edu.usf.cse.dto;

import edu.usf.cse.model.BuDetails;
import edu.usf.cse.model.CxBuDetails;

public class CreatedCustomerRecord implements CreatedRecord {

    private CxBuDetails buDetails;

    private String requestor;

    @Override
    public BuDetails getBuDetails() {
        return buDetails;
    }

    public void setBuDetails(CxBuDetails buDetails) {
        this.buDetails = buDetails;
    }

    @Override
    public String getRequestor() {
        return requestor;
    }

    @Override
    public void setRequestor(String requestor) {
        this.requestor = requestor;
    }
}
