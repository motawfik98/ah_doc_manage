package ah_doc_manag.service;

import ah_doc_manag.dao.ReceivedLetterDao;
import ah_doc_manag.model.ReceivedLetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ReceivedLetterServiceImpl implements ReceivedLetterService {
    @Autowired
    private ReceivedLetterDao receivedLetterDao;

    @Override
    public boolean save(ReceivedLetter receivedLetter) {
        try {
            receivedLetterDao.save(receivedLetter);
            return true;
        } catch (DataIntegrityViolationException e) {
            return false;
        }
    }

    @Override
    public Iterable<ReceivedLetter> findAll(Specification<ReceivedLetter> specification) {
        return receivedLetterDao.findAll(specification);
    }
}
