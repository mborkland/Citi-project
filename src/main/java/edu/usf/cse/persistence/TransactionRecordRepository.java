package edu.usf.cse.persistence;

import edu.usf.cse.model.TransactionRecord;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRecordRepository extends CrudRepository<TransactionRecord, Integer> {

}
