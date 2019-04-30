package edu.usf.cse.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import edu.usf.cse.utility.CreationDateDeserializer;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="CX_BU_DETAILS")
@Embeddable
public class CustomerRecord implements Record {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ID")
    private Integer id;

    @Embedded
    private CxBuDetails buDetails;

    @JsonDeserialize(using = CreationDateDeserializer.class)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="Creation_Date")
    private Date creationDate;

    @Override
	public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public BuDetails getBuDetails() {
	    return buDetails;
    }

    public void setBuDetails(CxBuDetails buDetails) {
	    this.buDetails = buDetails;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

}
