package edu.usf.cse.model;

import org.springframework.beans.factory.annotation.Autowired;

public class SearchParameter {

    private String fieldName;

    private String fieldValue;

    public SearchParameter() { }

    public SearchParameter(String fieldName, String fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }
}