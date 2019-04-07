package ah_doc_manag.service;

import ah_doc_manag.dao.UserDao;
import ah_doc_manag.model.ActiveUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public ActiveUser findUser(String username, Integer department) {
        return userDao.findUser(username, department);
    }

    @Override
    public ActiveUser findById(long id) {
        return userDao.findById(id);
    }
}
