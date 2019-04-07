package ah_doc_manag.service;

import ah_doc_manag.dao.DepartmentDao;
import ah_doc_manag.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService{
    @Autowired
    private DepartmentDao departmentDao;

    @Override
    public Iterable<Department> findAll() {
        return departmentDao.findAll();
    }

    @Override
    public Optional<Department> findById(Integer id) {
        return departmentDao.findById(id);
    }
}
