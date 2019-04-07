package ah_doc_manag.model;

import ah_doc_manag.service.DepartmentService;
import ah_doc_manag.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade implements IAuthenticationFacade {
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private UserService userService;

    @Override
    public UserPrincipal getUserPrincipal() {
        return (UserPrincipal) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }

    @Override
    public String getDepartmentName() {
        return getUserPrincipal().getDepartment().getName();
    }

    @Override
    public Integer getDepartmentID() {
        return getUserPrincipal().getDepartment().getId();
    }

    @Override
    public Department getDepartment() {
        Department department = getUserPrincipal().getDepartment();
        return departmentService.findById(department.getId()).get(); // initialize the department letters and users to be able to add and remove from them
    }

    @Override
    public ActiveUser getLoggedInUser() {
        ActiveUser user = getUserPrincipal().getLoggedInUser();
        return userService.findById(user.getId());
    }
}