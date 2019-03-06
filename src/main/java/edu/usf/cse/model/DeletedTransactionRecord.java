package edu.usf.cse.model;

import javax.persistence.*;

@Entity
@Table(name="DELETED_TX_BU_DETAILS")
public class DeletedTransactionRecord implements Record {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="ID")
    private Integer id;

    @Embedded
    @Column(name="Deleted_Record")
    private TxBuDetails buDetails;

    @Column(name="Deletion_Details")
    private String deletionDetails;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public void setBuDetails(BuDetails buDetails) {
        this.buDetails = (TxBuDetails) buDetails;
    }

    @Override
    public BuDetails getBuDetails() {
        return buDetails;
    }

    public void setDeletionDetails(String deletionDetails) {
        this.deletionDetails = deletionDetails;
    }

    public String getDeletionDetails() {
        return deletionDetails;
    }
}
