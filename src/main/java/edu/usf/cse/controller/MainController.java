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
                         @RequestParam String requestor) {
        String success = getRecordService(recordType).createRecord(fields, requestor);
        // TODO: add requestor to last modified table
        return success;
    }

    @RequestMapping(value = "/read", method = RequestMethod.GET)
    public List<Record> read(@RequestParam RecordType recordType, @RequestParam List<SearchParameter> searchParameters)
    {
        return getRecordService(recordType).getRecords(searchParameters);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PATCH)
    public String update(@RequestParam RecordType recordType, @RequestParam Integer recordId, @RequestParam String field,
                         @RequestParam String newValue, @RequestParam String requestor)
    {
        String success = getRecordService(recordType).updateRecord(recordId, field, newValue, requestor);
        //addUpdateInfo(record, field, newValue, requestor);
        // TODO: add requestor to last modified table
        return success;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public String delete(@RequestParam RecordType recordType, @RequestParam Integer id, @RequestParam String requestor)
    {
        String success = getRecordService(recordType).deleteRecord(id);
        // TODO: add record and requestor to deleted record table
        return success;
    }

    private RecordService getRecordService(RecordType recordType) {
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