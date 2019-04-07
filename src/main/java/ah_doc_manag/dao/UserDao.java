package ah_doc_manag.dao;

import ah_doc_manag.model.ActiveUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserDao extends CrudRepository<ActiveUser, Long> {
    @Query("SELECT user FROM ActiveUser user WHERE user.username = :username AND user.department.id = :department")
    ActiveUser findUser(@Param("username") String username,@Param("department") Integer department);

    ActiveUser findById(long id);
}
