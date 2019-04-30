package edu.usf.cse.service;

import edu.usf.cse.dto.CreatedRecord;
import edu.usf.cse.dto.UpdatedField;
import edu.usf.cse.model.*;
import edu.usf.cse.dto.UpdatedRecord;
import edu.usf.cse.persistence.CustomerRecordRepository;
import edu.usf.cse.persistence.TransactionRecordRepository;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class RecordService {

    protected static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    protected static final char historyDelimiter = '#';

    protected static final String searchDelimiter = ",";

    protected static final double duplicateRecordPercentage = 50.0;

    abstract public String createRecord(CreatedRecord record);

    abstract protected Record getRecordById(Integer id);

    abstract public List<Record> getRecords(String searchTerms, boolean or, boolean exactMatch);

    public List<Record> getRecordsImpl(RecordType recordType, boolean isArchive, EntityManager entityManager, String searchTerms,
                                       String[] searchableFields, boolean or, boolean exactMatch) {
        Class<? extends Record> recordClass =
                isArchive ? recordType == RecordType.CUSTOMER ? CustomerRecord.class : TransactionRecord.class
                          : recordType == RecordType.CUSTOMER ? DeletedCustomerRecord.class : DeletedTransactionRecord.class;
        String[] searchTermsSplit = searchTerms.split(searchDelimiter);
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<? extends Record> criteriaQuery = builder.createQuery(recordClass);
        Root<? extends Record> root = criteriaQuery.from(recordClass);
        criteriaQuery.where(getFinalPredicate(searchTermsSplit, searchableFields, or, exactMatch, builder, root));
        Query query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    abstract public List<Record> getRecentRecords(int numRandomRecords);

    abstract public String updateRecords(List<? extends UpdatedRecord> updatedRecords);

    public void updateRecordsImpl(List<? extends UpdatedRecord> updatedRecords, CrudRepository<? extends Record, Integer> repository) {
        for (UpdatedRecord updatedRecord : updatedRecords) {
            Record record = updatedRecord.getRecord();
            String soeid = updatedRecord.getSoeid();
            String reason = updatedRecord.getReason();
            StringBuilder history = new StringBuilder(record.getBuDetails().getHistory());
            LocalDateTime now = LocalDateTime.now();
            history.append(historyDelimiter).append("Updated on ")
                    .append(now.format(formatter)).append(" by ").append(soeid).append(". ");
            for (UpdatedField updatedField : updatedRecord.getUpdatedFields()) {
                history.append(updatedField.getField()).append(" - ").append("New value: ").append(updatedField.getNewValue())
                        .append(", Old value: ").append(updatedField.getOldValue()).append("; ");
            }

            history.append("Reason: ").append(reason);
            record.getBuDetails().setHistory(history.toString());

            if (repository instanceof CustomerRecordRepository) {
                ((CustomerRecordRepository) repository).save((CustomerRecord) record);
            } else {
                ((TransactionRecordRepository) repository).save((TransactionRecord) record);
            }
        }
    }

    abstract public List<Record> getArchivedRecords(String searchTerms, boolean or, boolean exactMatch);

    abstract public String deleteRecords(List<Integer> ids, String soeid, String reason);

    abstract public List<Record> getArchive();

    abstract public String saveDeletedRecord(BuDetails buDetails, Date creationDate, String soeid, String reason);

    abstract public String clearDeletedRecords(List<Integer> ids);

    abstract public String restoreDeletedRecords(List<Integer> ids, String soeid);

    abstract public List<Record> findDuplicateRecords(BuDetails detailsToMatch);

    protected Predicate getFinalPredicate(String[] searchTermsSplit, String[] searchableFields, boolean or, boolean exactMatch,
                                          CriteriaBuilder builder, Root<? extends Record> root) {
        if (or) {
            List<Predicate> predicates = new ArrayList<>();
            for (String searchTerm : searchTermsSplit) {
                for (String searchableField : searchableFields) {
                    predicates.add(getMatchTypePredicate(exactMatch, searchableField, searchTerm, builder, root));
                }
            }

            return builder.or(predicates.toArray(new Predicate[0]));
        } else {
            List<Predicate> outerPredicates = new ArrayList<>();
            for (String searchTerm : searchTermsSplit) {
                List<Predicate> innerPredicates = new ArrayList<>();
                for (String searchableField : searchableFields) {
                    innerPredicates.add(getMatchTypePredicate(exactMatch, searchableField, searchTerm, builder, root));
                }

                outerPredicates.add(builder.or(innerPredicates.toArray(new Predicate[0])));
            }

            return outerPredicates.size() == 1 ? outerPredicates.get(0) : builder.and(outerPredicates.toArray(new Predicate[0]));
        }
    }

    protected Predicate getMatchTypePredicate(boolean exactMatch, String searchableField, String searchTerm,
                                              CriteriaBuilder builder, Root<? extends Record> root) {
        if (exactMatch) {
            return builder.equal(root.get("buDetails").get(searchableField), searchTerm.trim());
        } else {
            Expression<Integer> index = builder.locate(root.get("buDetails").get(searchableField), searchTerm.trim());
            return builder.notEqual(index, 0);
        }
    }

}
