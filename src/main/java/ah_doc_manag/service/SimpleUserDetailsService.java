package ah_doc_manag.service;

import ah_doc_manag.dao.RoleDao;
import ah_doc_manag.dao.UserDao;
import ah_doc_manag.model.ActiveUser;
import ah_doc_manag.model.Role;
import ah_doc_manag.model.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userDetailsService")
public class SimpleUserDetailsService implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String[] usernameAndDepartment = username.split(String.valueOf(Character.LINE_SEPARATOR));
        if(usernameAndDepartment == null || usernameAndDepartment.length != 2) {
            System.out.println("Username and department must be provided");
            throw new UsernameNotFoundException("Username and department must be provided");
        }
        String accessUsername = usernameAndDepartment[0];
        String accessDepartment = usernameAndDepartment[1];
        ActiveUser user = userDao.findUser(accessUsername, Integer.valueOf(accessDepartment));
        if(user == null) {
            System.out.println(String.format("Username=%s not found for department=%s",
                    accessUsername,
                    accessDepartment));

            throw new UsernameNotFoundException(
                    String.format("Username=%s not found for department=%s",
                            accessUsername,
                            accessDepartment
                    ));
        }
        List<Role> roles = roleDao.findByUsernameAndDepartment(accessUsername, Integer.valueOf(accessDepartment));
        return new UserPrincipal(user, roles);
    }
}
