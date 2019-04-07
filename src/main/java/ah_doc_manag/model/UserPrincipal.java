package ah_doc_manag.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class UserPrincipal implements UserDetails {
    private ActiveUser activeUser;
    private List<Role> roles;

    public UserPrincipal(ActiveUser activeUser, List<Role> roles) {
        super();
        this.activeUser = activeUser;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(roles == null)
            return Collections.emptySet();

        Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();
        roles.forEach(role ->
                grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleValue()))
        );
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return activeUser.getPassword();
    }

    @Override
    public String getUsername() {
        return activeUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public Department getDepartment() {
        return activeUser.getDepartment();
    }

    public ActiveUser getLoggedInUser() {
        return activeUser;
    }
}
