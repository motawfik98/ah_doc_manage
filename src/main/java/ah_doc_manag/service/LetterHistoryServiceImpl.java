package ah_doc_manag.service;

import ah_doc_manag.dao.LetterHistoryDao;
import ah_doc_manag.model.IAuthenticationFacade;
import ah_doc_manag.model.Letter;
import org.hibernate.envers.DefaultRevisionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LetterHistoryServiceImpl<T extends Letter> implements LetterHistoryService<T> {
    @Autowired
    private LetterHistoryDao<T> letterHistoryDao;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Override
    public List<T> getLetterHistory(Long id, boolean all) {
        return letterHistoryDao.getLetterHistory(id, all);
    }

    @Override
    public List<DefaultRevisionEntity> getTimeOfHistory(Long id, boolean all) {
        return letterHistoryDao.getTimeOfHistory(id, all);
    }
}
