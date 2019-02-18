package edu.usf.cse.persistence;

import edu.usf.cse.model.CustomerRecordUpdateHistory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRecordUpdateHistoryRepository extends CrudRepository<CustomerRecordUpdateHistory, Integer>, JpaSpecificationExecutor<CustomerRecordUpdateHistory> {

        List<CustomerRecordUpdateHistory> findAll(Specification<CustomerRecordUpdateHistory> spec);
}
