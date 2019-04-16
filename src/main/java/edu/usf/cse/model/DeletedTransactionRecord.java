package edu.usf.cse.model;

import javax.persistence.*;
import java.util.Date;

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

    @Column(name="Deletion_Details", columnDefinition="MEDIUMTEXT")
    private String deletionDetails;

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

    public void setBuDetails(TxBuDetails buDetails) {
        this.buDetails = buDetails;
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

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

}
