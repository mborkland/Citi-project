package edu.usf.cse.persistence;

import edu.usf.cse.model.TransactionRecordUpdateHistory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRecordUpdateHistoryRepository extends CrudRepository<TransactionRecordUpdateHistory, Integer>, JpaSpecificationExecutor<TransactionRecordUpdateHistory> {

        List<TransactionRecordUpdateHistory> findAll(Specification<TransactionRecordUpdateHistory> spec);
}
