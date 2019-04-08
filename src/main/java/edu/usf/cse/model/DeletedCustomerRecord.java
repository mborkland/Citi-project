package edu.usf.cse.model;

import javax.persistence.*;

@Entity
@Table(name="DELETED_CX_BU_DETAILS")
public class DeletedCustomerRecord implements Record {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="ID")
    private Integer id;

    @Embedded
    @Column(name="Deleted_Record")
    private CxBuDetails buDetails;

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

    public void setBuDetails(CxBuDetails buDetails) {
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
}
