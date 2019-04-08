package edu.usf.cse.model;

import javax.persistence.*;

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
}
