package ah_doc_manag.web.restcontroller;

import ah_doc_manag.dao.LetterSpecificationsBuilder;
import ah_doc_manag.model.Letter;
import org.springframework.data.jpa.datatables.mapping.Column;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
class LetterRestController<T extends Letter> {

    void buildSpecifications(@Valid DataTablesInput input, LetterSpecificationsBuilder<T> specificationsBuilder) {
        for (Column column : input.getColumns()) {
            column.setSearchable(false);
            String columnValue = column.getSearch().getValue();
            String columnName = column.getName();
            if (columnName.equals("date")) {
                String[] values = columnValue.split(" ");
                if(values.length < 2)
                    continue;
                if(values[0].length() > 3) {
                    specificationsBuilder.with("startDate", values[0].substring(3));
                }
                if(values[1].length() > 3)
                    specificationsBuilder.with("endDate", values[1].substring(3));
                continue;
            }
            if(columnValue.equals(""))
                continue;
            specificationsBuilder.with(columnName, columnValue);

        }
        specificationsBuilder.build();
    }
}
