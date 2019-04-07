package ah_doc_manag.dao;

import ah_doc_manag.model.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LetterSpecificationsBuilder<T> {
    private List<SearchCriteria> parameters; // columns selected to be searched with
    private Specification<T> result; // the result of the last search


    public LetterSpecificationsBuilder(String departmentID) {
        parameters = new ArrayList<>();
        parameters.add(new SearchCriteria("departmentID", departmentID));
    }

    public void with(String columnName, String columnValue) {
        parameters.add(new SearchCriteria(columnName, columnValue)); // adds a new column with its value
    }

    public void build() {
        int size = parameters.size();
        if(size == 0) { // no selected columns
            result = null; // make result null to return the whole data set
            return;
        }
        List<Specification<T>> specs = parameters.stream() // convert the list of columns to list of Specifications
                .map(LetterSpecifications<T>::new)
                .collect(Collectors.toList());

        result = specs.get(0); // adds the first constraint to the final result
        for(int i = 1; i < size; i++) {
            result = Specification.where(result)
                    .and(specs.get(i)); // adds the whole list
        }
    }


    public Specification<T> getResult() {
        return result;
    }
}
