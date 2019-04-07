package ah_doc_manag.dao;

import ah_doc_manag.model.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageDao extends CrudRepository<Image, Long> {
    List<Image> findByLetterId(long letterID);

    Image findByPath(String path);


    Image findByPathAndDepartmentNumberAndLetterType(String path, Long departmentNumber, String letterType);
}
