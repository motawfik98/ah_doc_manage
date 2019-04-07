package ah_doc_manag.service;

import ah_doc_manag.dao.SentLetterDao;
import ah_doc_manag.model.SentLetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class SentLetterServiceImpl implements SentLetterService {
    @Autowired
    private SentLetterDao sentLetterDao;

    @Override
    public boolean save(SentLetter sentLetter) {
        try {
            sentLetterDao.save(sentLetter);
            return true;
        } catch (DataIntegrityViolationException e) {
            return false;
        }
    }

    @Override
    public Iterable<SentLetter> findAll(Specification<SentLetter> specification) {
        return sentLetterDao.findAll(specification);
    }
}
