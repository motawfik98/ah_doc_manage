package ah_doc_manag.model;


public interface IAuthenticationFacade {
    UserPrincipal getUserPrincipal();
    String getDepartmentName();
    Integer getDepartmentID();
    Department getDepartment();
    ActiveUser getLoggedInUser();
}


