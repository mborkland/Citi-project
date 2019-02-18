package edu.usf.cse.model;

import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name="TX_BU_UPDATE_HISTORY")
public class TransactionRecordUpdateHistory {

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ID")
    private Integer id;

	@Column(name="Record_ID")
	private Integer recordID;
	
    @Column(name="CSI_ID")
    private String csiId;

    @Column(name="CS_Instance")
    private String csInstance;
    
	@Column(name="Field_Name")
	private String fieldName;
	
	@Column(name="New_Value")
	private String newValue;
	
	@Column(name="Requestor")
	private String requestor;
	
	@Column(name="Time_Of_Change")
	private Timestamp timeOfChange;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRecordID() {
		return recordID;
	}

	public void setRecordID(Integer recordID) {
		this.recordID = recordID;
	}

	public String getCsiId() {
		return csiId;
	}

	public void setCsiId(String csiId) {
		this.csiId = csiId;
	}

	public String getCsInstance() {
		return csInstance;
	}

	public void setCsInstance(String csInstance) {
		this.csInstance = csInstance;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	public String getRequestor() {
		return requestor;
	}

	public void setRequestor(String requestor) {
		this.requestor = requestor;
	}

	public Timestamp getTimeOfChange() {
		return timeOfChange;
	}

	public void setTimeOfChange(Timestamp timeOfChange) {
		this.timeOfChange = timeOfChange;
	}
}