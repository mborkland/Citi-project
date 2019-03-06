package edu.usf.cse.controller;

import edu.usf.cse.model.*;
import edu.usf.cse.service.CustomerRecordService;
import edu.usf.cse.service.RecordService;
import edu.usf.cse.service.TransactionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public String create(@RequestParam RecordType recordType, @RequestParam List<String> fields, @RequestParam String requestor) {
        return getRecordService(recordType).createRecord(fields, requestor);
    }

    @RequestMapping(value = "/read", method = RequestMethod.GET)
    public List<Record> read(@RequestParam RecordType recordType, SearchParameterWrapper searchParameterWrapper) {
        return getRecordService(recordType).getRecords(searchParameterWrapper.getSearchParameters());
    }

    @RequestMapping(value = "/update", method = RequestMethod.PATCH)
    public String update(@RequestParam RecordType recordType, @RequestParam Integer recordId, @RequestParam String field,
                         @RequestParam String newValue, @RequestParam String requestor) {
        return getRecordService(recordType).updateRecord(recordId, field, newValue, requestor);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public String delete(@RequestParam RecordType recordType, @RequestParam Integer id, @RequestParam String requestor) {
        RecordService recordService = getRecordService(recordType);
        BuDetails buDetails = recordService.getRecordById(id).getBuDetails();
        String deleteSuccess = recordService.deleteRecord(id);
        String saveSuccess = recordService.saveDeletedRecord(buDetails, requestor);
        return deleteSuccess + ". " + saveSuccess;
    }

    @RequestMapping(value = "/clear", method = RequestMethod.DELETE)
    public String clear(@RequestParam RecordType recordType) {
        return getRecordService(recordType).clearDeletedRecords();
    }

    @RequestMapping(value = "/restore", method = RequestMethod.PATCH)
    public String restore(@RequestParam RecordType recordType, @RequestParam Integer id, @RequestParam String requestor) {
        return getRecordService(recordType).restoreDeletedRecord(id, requestor);
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