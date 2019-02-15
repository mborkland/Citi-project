package edu.usf.cse.persistence;

import edu.usf.cse.model.CustomerRecord;
import edu.usf.cse.model.Record;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRecordRepository extends CrudRepository<CustomerRecord, Integer>, JpaSpecificationExecutor<Record> {

        List<Record> findAll(Specification<Record> spec);
}
