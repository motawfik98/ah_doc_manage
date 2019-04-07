package ah_doc_manag.dao.datatables;

import ah_doc_manag.model.SentLetter;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.repository.cdi.Eager;

public interface SentLettersDataTableRepository extends DataTablesRepository<SentLetter, Long> {

}
