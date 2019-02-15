package edu.usf.cse.persistence;

import edu.usf.cse.model.Record;
import edu.usf.cse.model.TransactionRecord;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRecordRepository extends CrudRepository<TransactionRecord, Integer>, JpaSpecificationExecutor<Record> {

        List<Record> findAll(Specification<Record> spec);
}
