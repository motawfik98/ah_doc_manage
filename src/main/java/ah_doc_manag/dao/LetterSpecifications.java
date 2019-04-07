package ah_doc_manag.dao;

import ah_doc_manag.model.Department;
import ah_doc_manag.model.Letter;
import ah_doc_manag.model.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LetterSpecifications<T> implements Specification<T> {
    private String columnName, columnValue; // column name and value

    public LetterSpecifications(SearchCriteria searchCriteria) {
        columnName = searchCriteria.getColumnName();
        columnValue = searchCriteria.getColumnValue();
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        DateTimeFormatter formatter;
        switch (columnName) { // know the name of the selected column to search by
            case "startDate":
                formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                return criteriaBuilder.greaterThanOrEqualTo(root.get("date"), LocalDate.parse(columnValue, formatter));
            case "endDate":
                formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                return criteriaBuilder.lessThanOrEqualTo(root.get("date"), LocalDate.parse(columnValue, formatter));
            case "subject":
            case "message":
            case "sentTo":
            case "departmentSentFrom":
            case "senderName":
                Expression<String> databaseValue = criteriaBuilder.lower(root.get(columnName));
                String userValue = "%" + columnValue.toLowerCase() + "%";
                return criteriaBuilder.like(databaseValue, userValue);
            case "departmentID":
                Join<T, Department> departments = root.join("department");
                Predicate p = criteriaBuilder.equal(departments.get("id"), Integer.parseInt(columnValue));
                return p;
            default:
                return criteriaBuilder.equal(root.get(columnName), columnValue);
        }
    }
}
