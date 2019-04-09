package ah_doc_manag.service;

import ah_doc_manag.dao.ImageDao;
import ah_doc_manag.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageDao imageDao;

//    @PersistenceContext
//    private EntityManager entityManager;

    @Override
    public Optional<Image> findById(long id) {
        return imageDao.findById(id);
    }

    @Override
    public List<Image> findByLetterId(long letterID) {
        return imageDao.findByLetterId(letterID);
    }

    @Override
    public void save(Image image) {
        imageDao.save(image);
//        entityManager.clear();
    }

    @Override
    public boolean save(Image image, MultipartFile imageBytes) {
        try {
            image.setBytes(imageBytes.getBytes());
            imageDao.save(image);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Image findByPath(String name) {
        return imageDao.findByPath(name);
    }

    @Override
    public Image findByPathAndDepartmentNumberAndLetterType(String path, Long departmentNumber, String letterType) {
        return imageDao.findByPathAndDepartmentNumberAndLetterType(path, departmentNumber, letterType);
    }
}
