package ah_doc_manag.dao;

import ah_doc_manag.model.Department;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentDao extends CrudRepository<Department, Integer> {

}
