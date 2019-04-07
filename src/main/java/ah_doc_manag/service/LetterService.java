package ah_doc_manag.service;

import ah_doc_manag.model.Letter;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public interface LetterService<T extends Letter> {
    Iterable<T> findAll();
    Iterable<T> findAll(Specification<T> specification);
    Optional<T> findById(Long id);
    void delete(Long id);
    boolean save(T letter);
}
