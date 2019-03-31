package edu.usf.cse.persistence;

import edu.usf.cse.model.Record;
import edu.usf.cse.model.TransactionRecord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRecordRepository extends CrudRepository<TransactionRecord, Integer> {

        @Query(value = "select Update_History from TX_BU_DETAILS t where t.id = ?1", nativeQuery = true)
        String getUpdateHistory(Integer id);

        @Query(value="select * from TX_BU_DETAILS t order by rand() limit ?1", nativeQuery = true)
        List<Record> getRandomRecords(int numRandomRecords);

}
