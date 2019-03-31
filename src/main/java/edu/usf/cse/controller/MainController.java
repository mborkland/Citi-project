package edu.usf.cse.controller;

import edu.usf.cse.model.*;
import edu.usf.cse.service.CustomerRecordService;
import edu.usf.cse.service.RecordService;
import edu.usf.cse.service.TransactionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestParam RecordType recordType, @RequestParam List<String> fields, @RequestParam String requestor) {
        return new ResponseEntity<String>("{\"result\":\"" + getRecordService(recordType).createRecord(fields, requestor) + "\"}", HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/read")
    public ResponseEntity<List<Record>> read(@RequestParam RecordType recordType, String searchTerms) {
        return new ResponseEntity<List<Record>>(getRecordService(recordType).getRecords(searchTerms), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/read-random")
    public ResponseEntity<List<Record>> readRandom(@RequestParam RecordType recordType, @RequestParam int numRandomRecords) {
        return new ResponseEntity<List<Record>>(getRecordService(recordType).getRandomRecords(numRandomRecords), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/update", method = RequestMethod.PATCH)
    public String update(@RequestParam RecordType recordType, @RequestParam Integer recordId, @RequestParam String field,
                         @RequestParam String newValue, @RequestParam String requestor) {
        return getRecordService(recordType).updateRecord(recordId, field, newValue, requestor);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public String delete(@RequestParam RecordType recordType, @RequestParam Integer id, @RequestParam String requestor) {
        RecordService recordService = getRecordService(recordType);
        BuDetails buDetails = recordService.getRecordById(id).getBuDetails();
        String deleteSuccess = recordService.deleteRecord(id);
        String saveSuccess = recordService.saveDeletedRecord(buDetails, requestor);
        return deleteSuccess + ". " + saveSuccess;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/clear", method = RequestMethod.DELETE)
    public String clear(@RequestParam RecordType recordType) {
        return getRecordService(recordType).clearDeletedRecords();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
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