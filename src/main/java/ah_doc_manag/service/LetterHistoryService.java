package ah_doc_manag.service;

import ah_doc_manag.model.Letter;
import org.hibernate.envers.DefaultRevisionEntity;

import java.util.List;

public interface LetterHistoryService<T extends Letter> {
    List<T> getLetterHistory(Long id, boolean all);
    List<DefaultRevisionEntity> getTimeOfHistory(Long id, boolean all);
}
