package edu.usf.cse.controller;

import edu.usf.cse.model.CustomerRecord;
import edu.usf.cse.model.RecordType;
import edu.usf.cse.model.TransactionRecord;
import edu.usf.cse.persistence.CustomerRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Iterator;
import java.util.List;

@RestController
public class MainController {

    @Autowired
    private CustomerRecordRepository customerRecordRepository;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@RequestParam String recordType, @RequestParam List<String> fields) {
        if (RecordType.valueOf(recordType) == RecordType.CUSTOMER) {
            CustomerRecord customerRecord = new CustomerRecord();
            Iterator<String> iterator = fields.iterator();
            customerRecord.setCsiId(iterator.next());
            customerRecord.setCsInstance(iterator.next());
            customerRecord.setBusinessId(iterator.next());
            customerRecord.setBizUnitId(iterator.next());
            customerRecord.setProductId(iterator.next());
            customerRecord.setBizProdId(iterator.next());
            customerRecord.setCxBusinessUnitName(iterator.next());
            customerRecord.setCxBusinessUnitNameDisplayValue(iterator.next());
            customerRecord.setCxBusinessGreenzone(iterator.next());
            customerRecord.setRulesetMapped(iterator.next());
            customerRecord.setRegion(iterator.next());
            customerRecord.setCountry(iterator.next());
            customerRecord.setSector(iterator.next());
            customerRecord.setWorkflowFlag(iterator.next());
            customerRecord.setWorkflowInstance(iterator.next());
            customerRecord.setWfBusinessUnitName(iterator.next());
            customerRecord.setWfBusinesUnitNameDisplayValue(iterator.next());
            customerRecord.setWfBusinessGreenzone(iterator.next());
            customerRecord.setInterfaceDescription(iterator.next());
            customerRecord.setInterfaceAppId(iterator.next());
            customerRecord.setInterfaceApplicationName(iterator.next());
            customerRecord.setOperationEntity(iterator.next());
            customerRecord.setCbusol(iterator.next());
            customerRecord.setOpsComplianceContacts(iterator.next());
            customerRecord.setCwVersion(iterator.next());
            customerRecord.setGomCompliant(iterator.next());
            customerRecord.setCwUatContacts(iterator.next());
            customerRecord.setSourceTechContacts(iterator.next());
            customerRecord.setImpactToBusiness(iterator.next());
            customerRecord.setBusinessEscalationContacts(iterator.next());
            customerRecord.setTimezone(iterator.next());
            customerRecord.setImpactToProductProcessor(iterator.next());
            customerRecord.setProductProcessorEscalationContacts(iterator.next());
            customerRecord.setProductProcessorGroupDl(iterator.next());
            customerRecord.setProductProcessorSnowGroupName(iterator.next());
            customerRecord.setProductProcessorSla(iterator.next());
            customerRecord.setFrequency(iterator.next());
            customerRecord.setFileScheduledDateAndTime(iterator.next());
            customerRecord.setAverageVolume(iterator.next());
            customerRecord.setAverageRuntime(iterator.next());
            customerRecord.setFile2ndScheduledDateAndTime(iterator.next());

            customerRecordRepository.save(customerRecord);
            return "Customer record created successfully";
        } else {
            TransactionRecord transactionRecord = new TransactionRecord();
            // transaction record field values here

            // add record to database here
            return "Transaction record created successfully";
        }
    }


}