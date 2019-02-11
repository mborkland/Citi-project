package edu.usf.cse.persistence;

import edu.usf.cse.model.CustomerRecord;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRecordRepository extends CrudRepository<CustomerRecord, Integer> {

}
