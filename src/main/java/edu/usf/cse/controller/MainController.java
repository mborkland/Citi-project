package edu.usf.cse.controller;

import edu.usf.cse.model.*;
import edu.usf.cse.service.CustomerRecordService;
import edu.usf.cse.service.RecordService;
import edu.usf.cse.service.TransactionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@RestController
public class MainController {

    private CustomerRecordService customerRecordService;

    private TransactionRecordService transactionRecordService;

    @Autowired
    public MainController(CustomerRecordService customerRecordService, TransactionRecordService transactionRecordService) {
        this.customerRecordService = customerRecordService;
        this.transactionRecordService = transactionRecordService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@RequestParam RecordType recordType, @RequestParam List<String> fields,
                         @RequestParam String author) {
        String success = getRecordServiceType(recordType).createRecord(fields);
        // TODO: add author to last modified table
        return success;
    }

    @RequestMapping(value = "/read", method = RequestMethod.GET)
    public Record read(@RequestParam RecordType recordType, @RequestParam List<SearchParameter> searchParameters)
    {
        return getRecordServiceType(recordType).getRecord(searchParameters);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PATCH)
    public String update(@RequestParam RecordType recordType, @RequestParam Record record, @RequestParam String field,
                         @RequestParam String newValue, @RequestParam String author)
    {

        String success = getRecordServiceType(recordType).updateRecord(record, field, newValue);
        // TODO: add author to last modified table
        return success;
    }

    private RecordService getRecordServiceType(RecordType recordType) {
        switch (recordType) {
            case CUSTOMER:
                return customerRecordService;
            case TRANSACTION:
                return transactionRecordService;
            default:
                throw new IllegalArgumentException("Invalid record type.");
        }
    }
}