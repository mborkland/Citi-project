package edu.usf.cse.controller;

import edu.usf.cse.dto.CreatedCustomerRecord;
import edu.usf.cse.dto.CreatedTransactionRecord;
import edu.usf.cse.dto.UpdatedCustomerRecord;
import edu.usf.cse.dto.UpdatedTransactionRecord;
import edu.usf.cse.model.*;
import edu.usf.cse.service.CustomerRecordService;
import edu.usf.cse.service.RecordService;
import edu.usf.cse.service.TransactionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create-cx")
    public ResponseEntity<String> createCx(@RequestBody CreatedCustomerRecord record) {
        return new ResponseEntity<String>("{\"result\":\"" + customerRecordService.createRecord(record) + "\"}", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create-tx")
    public ResponseEntity<String> createTx(@RequestBody CreatedTransactionRecord record) {
        return new ResponseEntity<String>("{\"result\":\"" + transactionRecordService.createRecord(record) + "\"}", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/duplicate-cx")
    public ResponseEntity<List<Record>> getDuplicateCx(@RequestBody CxBuDetails buDetails) {
        return new ResponseEntity<List<Record>>(customerRecordService.findDuplicateRecords(buDetails), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/duplicate-tx")
    public ResponseEntity<List<Record>> getDuplicateTx(@RequestBody TxBuDetails buDetails) {
        return new ResponseEntity<List<Record>>(transactionRecordService.findDuplicateRecords(buDetails), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/read")
    public ResponseEntity<List<Record>> read(@RequestParam RecordType recordType, @RequestParam String searchTerms,
                                             @RequestParam boolean any, @RequestParam boolean exactMatch) {
        return new ResponseEntity<List<Record>>(getRecordService(recordType).getRecords(searchTerms, any, exactMatch), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/read-recent")
    public ResponseEntity<List<Record>> readRecent(@RequestParam RecordType recordType, @RequestParam int numRecentRecords) {
        return new ResponseEntity<List<Record>>(getRecordService(recordType).getRecentRecords(numRecentRecords), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/read-archive")
    public ResponseEntity<List<Record>> readArchive(@RequestParam RecordType recordType, @RequestParam String searchTerms,
                                                    @RequestParam boolean any, @RequestParam boolean exactMatch) {
        return new ResponseEntity<List<Record>>(getRecordService(recordType).getArchivedRecords(searchTerms, any, exactMatch), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/update-customer", method = RequestMethod.PATCH)
    public ResponseEntity<String> updateCustomerRecord(@RequestBody List<UpdatedCustomerRecord> records) {
        return new ResponseEntity<String>("{\"result\":\"" + customerRecordService.updateRecord(records) + "\"}", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/update-transaction", method = RequestMethod.PATCH)
    public ResponseEntity<String> updateTransactionRecord(@RequestBody List<UpdatedTransactionRecord> records) {
        return new ResponseEntity<String>("{\"result\":\"" + transactionRecordService.updateRecord(records) + "\"}", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@RequestParam RecordType recordType, @RequestParam List<Integer> ids, @RequestParam String soeid, @RequestParam String reason) {
        RecordService recordService = getRecordService(recordType);
        for (Integer id : ids) {
            Record record = recordService.getRecordById(id);
            BuDetails buDetails = record.getBuDetails();
            Date creationDate = record.getCreationDate();
            recordService.deleteRecord(id);
            recordService.saveDeletedRecord(buDetails, creationDate, soeid, reason);
        }

        return new ResponseEntity<String>("{\"result\":\"Record(s) deleted and archived successfully.\"}", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/archive", method = RequestMethod.GET)
    public ResponseEntity<List<Record>> getArchive(@RequestParam RecordType recordType) {
        return new ResponseEntity<List<Record>>(getRecordService(recordType).getArchive(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/clear", method = RequestMethod.DELETE)
    public ResponseEntity<String> clear(@RequestParam RecordType recordType, @RequestParam List<Integer> ids) {
        return new ResponseEntity<String>("{\"result\":\"" + getRecordService(recordType).clearDeletedRecords(ids) + "\"}", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/restore", method = RequestMethod.PATCH)
    public ResponseEntity<String> restore(@RequestParam RecordType recordType, @RequestParam List<Integer> ids, @RequestParam String soeid) {
        RecordService recordService = getRecordService(recordType);
        for (Integer id : ids) {
            recordService.restoreDeletedRecord(id, soeid);
        }

        return new ResponseEntity<String>("{\"result\":\"Record(s) restored successfully.\"}", HttpStatus.OK);
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