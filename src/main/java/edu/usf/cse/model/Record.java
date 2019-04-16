package edu.usf.cse.model;

import java.util.Date;

public interface Record {

    Integer getId();

    void setId(Integer id);

    BuDetails getBuDetails();

    Date getCreationDate();

    void setCreationDate(Date creationDate);

}
