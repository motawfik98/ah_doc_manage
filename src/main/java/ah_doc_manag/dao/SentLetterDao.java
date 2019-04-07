package ah_doc_manag.dao;

import ah_doc_manag.model.SentLetter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SentLetterDao extends CrudRepository<SentLetter, Long> {
    List<SentLetter> findAll(Specification<SentLetter> specification);

}
