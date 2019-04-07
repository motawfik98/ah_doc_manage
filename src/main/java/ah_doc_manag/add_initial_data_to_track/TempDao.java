//package ah_doc_manag.add_initial_data_to_track;
//
//import ah_doc_manag.model.ActiveUser;
//import ah_doc_manag.model.Department;
//import ah_doc_manag.web.controller.UniqueViolationException;
//import org.springframework.stereotype.Repository;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.PersistenceException;
//import javax.persistence.PersistenceUnit;
//
//@Repository
//public class TempDao {
//    @PersistenceUnit
//    private EntityManagerFactory entityManagerFactory;
//
//    public void addDepartment(Integer id, String name) {
//        Department department = new Department();
//        department.setId(id);
//        department.setName(name);
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        entityManager.getTransaction().begin();
//        entityManager.persist(department);
//        entityManager.flush();
//        entityManager.getTransaction().commit();
//        entityManager.close();
//    }
//
//    private Department getDepartment(Integer id) {
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        Department department = entityManager.find(Department.class, id);
//        entityManager.close();
//        return department;
//    }
//
//    public void addUser(String username, String password, Integer departmentId) {
//        ActiveUser user = new ActiveUser();
//        user.setUsername(username);
//        user.setPassword(password);
//        user.setDepartment(getDepartment(departmentId));
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        entityManager.getTransaction().begin();
//        entityManager.persist(user);
//        entityManager.flush();
//        entityManager.getTransaction().commit();
//        entityManager.close();
//    }
//}
