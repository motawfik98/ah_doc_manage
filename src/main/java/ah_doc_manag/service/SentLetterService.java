package ah_doc_manag.service;

import ah_doc_manag.model.SentLetter;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SentLetterService {
    boolean save(SentLetter sentLetter);
    Iterable<SentLetter> findAll(Specification<SentLetter> specification);
}
