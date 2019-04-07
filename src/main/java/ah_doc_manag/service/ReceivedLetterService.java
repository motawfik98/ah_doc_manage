package ah_doc_manag.service;

import ah_doc_manag.model.ReceivedLetter;
import org.springframework.data.jpa.domain.Specification;

import java.util.Map;
import java.util.Optional;

public interface ReceivedLetterService {
    boolean save(ReceivedLetter receivedLetter);
    Iterable<ReceivedLetter> findAll(Specification<ReceivedLetter> specification);
}

