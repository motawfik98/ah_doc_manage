package ah_doc_manag.dao;

import ah_doc_manag.model.ReceivedLetter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceivedLetterDao extends CrudRepository<ReceivedLetter, Long> {
    List<ReceivedLetter> findAll(Specification<ReceivedLetter> specification);

}
