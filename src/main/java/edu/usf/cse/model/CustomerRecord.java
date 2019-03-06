package edu.usf.cse.model;

import javax.persistence.*;

@Entity
@Table(name="CX_BU_DETAILS")
@Embeddable
public class CustomerRecord implements Record {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="ID")
    private Integer id;

    @Embedded
    private CxBuDetails buDetails;

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

    @Override
    public void setBuDetails(BuDetails buDetails) {
	    this.buDetails = (CxBuDetails) buDetails;
    }
}
