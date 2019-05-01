package edu.usf.cse.service;

import edu.usf.cse.dto.CreatedCustomerRecord;
import edu.usf.cse.dto.CreatedRecord;
import edu.usf.cse.dto.UpdatedField;
import edu.usf.cse.model.*;
import edu.usf.cse.dto.UpdatedRecord;
import edu.usf.cse.persistence.CustomerRecordRepository;
import edu.usf.cse.persistence.DeletedCustomerRecordRepository;
import edu.usf.cse.persistence.DeletedTransactionRecordRepository;
import edu.usf.cse.persistence.TransactionRecordRepository;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.sql.Timestamp;
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

    abstract public String createRecord(CreatedRecord createdRecord);

    public void createRecordImpl(CreatedRecord createdRecord, CrudRepository<? extends Record, Integer> repository) {
        BuDetails buDetails = createdRecord.getBuDetails();
        LocalDateTime now = LocalDateTime.now();
        buDetails.setHistory("Record created on " + now.format(formatter) + " by " + createdRecord.getSoeid());
        Record record;

        if (createdRecord instanceof CreatedCustomerRecord) {
            record = new CustomerRecord();
            ((CustomerRecord) record).setBuDetails((CxBuDetails) buDetails);
        } else {
            record = new TransactionRecord();
            ((TransactionRecord) record).setBuDetails((TxBuDetails) buDetails);
        }
        record.setCreationDate(Timestamp.valueOf(now));

        if (record instanceof CustomerRecord) {
            ((CustomerRecordRepository) repository).save((CustomerRecord) record);
        } else {
            ((TransactionRecordRepository) repository).save((TransactionRecord) record);
        }
    }

    abstract protected Record getRecordById(Integer id);

    abstract public List<Record> getRecords(String searchTerms, boolean or, boolean exactMatch);

    protected List<Record> getRecordsImpl(RecordType recordType, boolean isArchive, EntityManager entityManager, String searchTerms,
                                       String[] searchableFields, boolean or, boolean exactMatch) {
        Class<? extends Record> recordClass =
                isArchive ? (recordType == RecordType.CUSTOMER ? DeletedCustomerRecord.class : DeletedTransactionRecord.class)
                          : (recordType == RecordType.CUSTOMER ? CustomerRecord.class : TransactionRecord.class);
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

    protected void updateRecordsImpl(List<? extends UpdatedRecord> updatedRecords, CrudRepository<? extends Record, Integer> repository) {
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

    protected void deleteRecordsImpl(List<Integer> ids, String soeid, String reason, CrudRepository<? extends Record, Integer> repository,
                                     CrudRepository<? extends DeletedRecord, Integer> deletedRepository) {
        for (Integer id : ids) {
            Record record = getRecordById(id);
            if (deletedRepository instanceof DeletedCustomerRecordRepository) {
                saveDeletedRecord(new DeletedCustomerRecord(), record.getBuDetails(), record.getCreationDate(), soeid, reason,
                        deletedRepository);
            } else {
                saveDeletedRecord(new DeletedTransactionRecord(), record.getBuDetails(), record.getCreationDate(), soeid, reason,
                        deletedRepository);
            }

            repository.delete(id);
        }
    }

    protected void saveDeletedRecord(DeletedRecord deletedRecord, BuDetails buDetails, Date creationDate, String soeid, String reason,
                                     CrudRepository<? extends DeletedRecord, Integer> deletedRepository) {
        if (deletedRecord instanceof CustomerRecord) {
            ((CustomerRecord) deletedRecord).setBuDetails((CxBuDetails) buDetails);
        } else {
            ((TransactionRecord) deletedRecord).setBuDetails((TxBuDetails) buDetails);
        }

        ((Record) deletedRecord).setCreationDate(creationDate);
        LocalDateTime now = LocalDateTime.now();
        deletedRecord.setDeletionDetails("Record deleted on " + now.format(formatter) +
                " by " + soeid + ". Reason: " + reason);

        if (deletedRecord instanceof CustomerRecord) {
            ((DeletedCustomerRecordRepository) deletedRepository).save((DeletedCustomerRecord) deletedRecord);
        } else {
            ((DeletedTransactionRecordRepository) deletedRepository).save((DeletedTransactionRecord) deletedRecord);
        }
    }

    abstract public List<Record> getArchive();

    protected List<Record> getArchiveImpl(CrudRepository<? extends Record, Integer> repository) {
        List<Record> records = new ArrayList<>();
        for (Record record : repository.findAll()) {
            records.add(record);
        }
        return records;
    }

    abstract public String clearDeletedRecords(List<Integer> ids);

    protected void clearDeletedRecordsImpl(List<Integer> ids, CrudRepository<? extends DeletedRecord, Integer> repository) {
        for (Integer id : ids) {
            repository.delete(id);
        }
    }

    abstract public String restoreDeletedRecords(List<Integer> ids, String soeid);

    protected void restoreDeletedRecordsImpl(List<Integer> ids, String soeid, CrudRepository<? extends DeletedRecord, Integer> deletedRepository,
                                             CrudRepository<? extends Record, Integer> repository) {
        for (Integer id : ids) {
            DeletedRecord deletedRecord = deletedRepository.findOne(id);
            String deletionDetails = deletedRecord.getDeletionDetails();
            BuDetails buDetails = ((Record) deletedRecord).getBuDetails();
            Date creationDate = ((Record) deletedRecord).getCreationDate();
            if (deletedRepository instanceof DeletedCustomerRecordRepository) {
                restoreRecord(new CustomerRecord(), buDetails, deletionDetails, creationDate, soeid, repository);
            } else {
                restoreRecord(new TransactionRecord(), buDetails, deletionDetails, creationDate, soeid, repository);
            }
            deletedRepository.delete(id);
        }
    }

    private void restoreRecord(Record record, BuDetails buDetails, String deletionDetails, Date creationDate, String soeid,
                               CrudRepository<? extends Record, Integer> repository) {
        StringBuilder history = new StringBuilder(buDetails.getHistory());
        LocalDateTime now = LocalDateTime.now();
        history.append(historyDelimiter).append(deletionDetails).append(historyDelimiter)
                .append("Record restored on ").append(now.format(formatter)).append(" by ").append(soeid);
        buDetails.setHistory(history.toString());

        if (record instanceof CustomerRecord) {
            ((CustomerRecord) record).setBuDetails((CxBuDetails) buDetails);
        } else {
            ((TransactionRecord) record).setBuDetails((TxBuDetails) buDetails);
        }

        record.setCreationDate(creationDate);
        if (record instanceof CustomerRecord) {
            ((CustomerRecordRepository) repository).save((CustomerRecord) record);
        } else {
            ((TransactionRecordRepository) repository).save((TransactionRecord) record);
        }
    }

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
