package ah_doc_manag.service;

import ah_doc_manag.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ImageService {
    Optional<Image> findById(long id);
    List<Image> findByLetterId(long letterID);
    void save(Image image);

    boolean save(Image image, MultipartFile imageBytes);

    Image findByPath(String name);
    Image findByPathAndDepartmentNumberAndLetterType(String path, Long departmentNumber, String letterType);

}

