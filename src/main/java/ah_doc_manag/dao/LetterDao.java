package ah_doc_manag.dao;

import ah_doc_manag.model.Letter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LetterDao<T extends Letter> extends CrudRepository<T, Long> {
    Iterable<T> findAll(Specification<T> specification);
}
