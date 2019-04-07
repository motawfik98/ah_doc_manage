package ah_doc_manag.service;

import ah_doc_manag.model.Department;

import java.util.Optional;


public interface DepartmentService {
    Iterable<Department> findAll();

    Optional<Department> findById(Integer id);
}
