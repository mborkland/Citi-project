package edu.usf.cse.persistence;

import edu.usf.cse.model.CustomerRecord;
import edu.usf.cse.model.Record;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRecordRepository extends CrudRepository<CustomerRecord, Integer> {

        @Query(value = "select Update_History from CX_BU_DETAILS c where c.id = ?1", nativeQuery = true)
        String getUpdateHistory(Integer id);

        @Query(value="select * from CX_BU_DETAILS c order by rand() limit ?1", nativeQuery = true)
        List<Record> getRandomRecords(int numRandomRecords);

}
