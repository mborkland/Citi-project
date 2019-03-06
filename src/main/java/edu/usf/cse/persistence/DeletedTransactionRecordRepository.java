package edu.usf.cse.persistence;

import edu.usf.cse.model.DeletedTransactionRecord;
import org.springframework.data.repository.CrudRepository;

public interface DeletedTransactionRecordRepository extends CrudRepository<DeletedTransactionRecord, Integer> {

}
