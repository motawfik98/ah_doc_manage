package ah_doc_manag.service;

import ah_doc_manag.dao.LetterDao;
import ah_doc_manag.model.Letter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LetterServiceImpl<T extends Letter> implements LetterService<T> {
    @Autowired
    private LetterDao<T> letterDao;

    @Override
    public Iterable<T> findAll() {
        return letterDao.findAll();
    }

    @Override
    public Iterable<T> findAll(Specification<T> specification) {
        return letterDao.findAll(specification);
    }

    @Override
    public Optional<T> findById(Long id) {
        return letterDao.findById(id);
    }

    @Override
    public void delete(Long id) {
        letterDao.deleteById(id);
    }

    @Override
    public boolean save(T letter) {
        try {
            letterDao.save(letter);
            return true;
        } catch (DataIntegrityViolationException e) {
            return false;
        }
    }
}
