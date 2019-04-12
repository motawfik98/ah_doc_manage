package ah_doc_manag.web.restcontroller;

import ah_doc_manag.dao.LetterSpecificationsBuilder;
import ah_doc_manag.model.Image;
import ah_doc_manag.model.Letter;
import ah_doc_manag.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.Column;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
class LetterRestController<T extends Letter> {
    @Autowired
    private ImageService imageService;

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

    @PreAuthorize("hasRole('ROLE_READ')")
    @RequestMapping("/{type}/{letterID}/images")
    public ResponseEntity<Object> showImages(@PathVariable String type, @PathVariable long letterID) {
        List<Image> images = imageService.findByLetterId(letterID);
        return new ResponseEntity<>(images, HttpStatus.OK);
    }

}
