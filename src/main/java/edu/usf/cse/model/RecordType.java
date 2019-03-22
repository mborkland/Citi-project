package edu.usf.cse.model;

public enum RecordType {
    CUSTOMER("CUSTOMER"),
    TRANSACTION("TRANSACTION");

    private String text;

    RecordType(String text) {
        this.text = text;
    }

    public static RecordType fromString(String text) {
        if (text.equals("CUSTOMER")) {
            return CUSTOMER;
        } else {
            return TRANSACTION;
        }
    }
}
