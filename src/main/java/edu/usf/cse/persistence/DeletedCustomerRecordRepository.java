package edu.usf.cse.persistence;

import edu.usf.cse.model.DeletedCustomerRecord;
import org.springframework.data.repository.CrudRepository;

public interface DeletedCustomerRecordRepository extends CrudRepository<DeletedCustomerRecord, Integer> {

}
