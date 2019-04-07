package ah_doc_manag.dao.datatables;

import ah_doc_manag.model.ReceivedLetter;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.repository.cdi.Eager;

public interface ReceivedLettersDataTablesRepository extends DataTablesRepository<ReceivedLetter, Long> {
}
