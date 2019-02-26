package edu.usf.cse.persistence;

import edu.usf.cse.model.CustomerRecord;
import edu.usf.cse.model.Record;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRecordRepository extends CrudRepository<CustomerRecord, Integer>, JpaSpecificationExecutor<Record> {

        List<Record> findAll(Specification<Record> spec);

        @Query(value = "select Update_History from CX_BU_DETAILS c where c.id = ?1", nativeQuery = true)
        String getUpdateHistory(Integer id);
}
