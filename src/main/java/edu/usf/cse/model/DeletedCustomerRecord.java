package edu.usf.cse.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="DELETED_CX_BU_DETAILS")
public class DeletedCustomerRecord implements Record, DeletedRecord {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="ID")
    private Integer id;

    @Embedded
    @Column(name="Deleted_Record")
    private CxBuDetails buDetails;

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

    public void setBuDetails(CxBuDetails buDetails) {
        this.buDetails = buDetails;
    }

    @Override
    public BuDetails getBuDetails() {
        return buDetails;
    }

    @Override
    public void setDeletionDetails(String deletionDetails) {
        this.deletionDetails = deletionDetails;
    }

    @Override
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
