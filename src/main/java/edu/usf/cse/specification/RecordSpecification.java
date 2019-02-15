package edu.usf.cse.specification;

import edu.usf.cse.model.Record;
import edu.usf.cse.model.SearchParameter;
import edu.usf.cse.service.RecordService;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class RecordSpecification implements Specification<Record> {

    private List<SearchParameter> searchParameters;

    public RecordSpecification(List<SearchParameter> searchParameters) {
        this.searchParameters = searchParameters;
    }

    @Override
    public Predicate toPredicate(Root<Record> root, CriteriaQuery<?> query, CriteriaBuilder builder)
    {
        if (searchParameters.isEmpty()) {
            return null;
        }

        List<Predicate> predicates = new ArrayList<>();
        for (SearchParameter searchParameter : searchParameters) {
            predicates.add(builder.equal(root.get(searchParameter.getFieldName()), searchParameter.getFieldValue()));
        }

        return builder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
