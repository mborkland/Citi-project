package edu.usf.cse.service;

import edu.usf.cse.model.CustomerRecord;
import edu.usf.cse.model.Record;
import edu.usf.cse.model.SearchParameter;
//import edu.usf.cse.model.CustomerRecordUpdateHistory;
import edu.usf.cse.persistence.CustomerRecordRepository;
//import edu.usf.cse.persistence.CustomerRecordUpdateHistoryRepository;
import edu.usf.cse.specification.RecordSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

@Component
public class CustomerRecordService implements RecordService {

    private CustomerRecordRepository customerRecordRepository;

    private EntityManager entityManager;

    private static final char historyDelimiter = ';';

    @Autowired
    public CustomerRecordService(CustomerRecordRepository customerRecordRepository, EntityManager entityManager) {
        this.customerRecordRepository = customerRecordRepository;
        this.entityManager = entityManager;
    }

    @Override
    public String createRecord(List<String> fields, String requestor) {
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
        customerRecord.setWfBusinessUnitNameDisplayValue(iterator.next());
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
        customerRecord.setUpdateHistory("Record created on " + new Timestamp(System.currentTimeMillis()) + " by " + requestor);

        customerRecordRepository.save(customerRecord);
        return "Customer record created successfully";
    }
    
    @Override
    public List<Record> getRecords(List<SearchParameter> searchParameters) {
        RecordSpecification recordSpecification = new RecordSpecification(searchParameters);
        return customerRecordRepository.findAll(recordSpecification);
    }

    @Override
    @Transactional
    public String updateRecord(Integer id, String field, String newValue, String requestor) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<CustomerRecord> criteria = builder.createCriteriaUpdate(CustomerRecord.class);
        Root<CustomerRecord> root = criteria.from(CustomerRecord.class);
        StringBuilder updateHistory = new StringBuilder(customerRecordRepository.getUpdateHistory(id));
        updateHistory.append(historyDelimiter).append("Record updated on ").append(new Timestamp(System.currentTimeMillis()))
                .append(" by ").append(requestor);

        criteria.set(root.get(field), newValue);
        criteria.set(root.get("updateHistory"), updateHistory.toString());
        criteria.where(builder.equal(root.get("id"), id));
        entityManager.createQuery(criteria).executeUpdate();

        return "Customer record updated successfully";
    }

    @Override
    public String deleteRecord(Integer id) {
        customerRecordRepository.delete(id);
        return "Customer record deleted successfully";
    }
}
