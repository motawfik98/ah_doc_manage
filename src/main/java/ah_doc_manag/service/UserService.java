package ah_doc_manag.service;

import ah_doc_manag.model.ActiveUser;

public interface UserService {
    ActiveUser findUser(String username, Integer department);

    ActiveUser findById(long id);
}
