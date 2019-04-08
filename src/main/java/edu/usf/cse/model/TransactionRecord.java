package edu.usf.cse.model;

import javax.persistence.*;

@Entity
@Table(name="TX_BU_DETAILS")
public class TransactionRecord implements Record {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ID")
    private Integer id;

    @Embedded
    private TxBuDetails buDetails;

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

    public void setBuDetails(TxBuDetails buDetails) {
        this.buDetails = buDetails;
    }

}
