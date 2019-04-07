package ah_doc_manag.dao;

import ah_doc_manag.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleDao extends CrudRepository<Role, Long> {
    List<Role> findByUsernameAndDepartment(String username, Integer department);
}
