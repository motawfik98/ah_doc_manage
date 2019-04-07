package ah_doc_manag.dao;

import ah_doc_manag.model.Letter;
import ah_doc_manag.model.SentLetter;
import org.hibernate.Hibernate;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LetterHistoryDaoImpl<T extends Letter> implements LetterHistoryDao<T> {
    @PersistenceContext
    private EntityManager entityManager;

    private List<Object> getHistoryObjects(long id, boolean all) {

        AuditReader reader = AuditReaderFactory.get(entityManager);
        AuditQuery query = reader.createQuery().forRevisionsOfEntity(Letter.class, false, false);
        if(all) {
            query.add(AuditEntity.property("id").eq(id));
            query.addOrder(AuditEntity.revisionNumber().asc());
        } else {
            query.add(AuditEntity.revisionNumber().eq((int)id));
        }
        return query.getResultList();

    }

    @Override
    public List<T> getLetterHistory(Long id, boolean all) {
        List<Object> objects = getHistoryObjects(id, all);
        List<T> letters = new ArrayList<>(objects.size());
        for (Object object : objects) {
            Object[] objArr = (Object[]) object;
            T letter =(T) objArr[0];
            letters.add(letter);
            Hibernate.initialize(letter.getUser());
            Hibernate.initialize(letter.getDepartment());
        }
        return letters;
    }

    @Override
    public List<DefaultRevisionEntity> getTimeOfHistory(Long id, boolean all) {
        List<Object> objects = getHistoryObjects(id, all);
        List<DefaultRevisionEntity> history = new ArrayList<>(objects.size());
        for (Object object : objects) {
            Object[] objArray = (Object[]) object;
            DefaultRevisionEntity dre = (DefaultRevisionEntity) objArray[1];
            history.add(dre);
        }
        return history;
    }

}
