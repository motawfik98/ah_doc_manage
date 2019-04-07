package ah_doc_manag.config;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

public class SimpleAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if(!request.getMethod().equals("POST"))
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());

        UsernamePasswordAuthenticationToken authRequest = getAuthRequest(request);
        setDetails(request, authRequest);
        return this.getAuthenticationManager()
                .authenticate(authRequest);
    }

    private UsernamePasswordAuthenticationToken getAuthRequest(HttpServletRequest request) {
        String username = obtainUsername(request);
        String password = obtainPassword(request);
        String department = obtainDepartment(request);

        if(username == null) username = "";
        if(password == null) password = "";
        if(department == null) department = "";

        String usernameDepartment = String.format("%s%s%s",
                username.trim(),
                String.valueOf(Character.LINE_SEPARATOR),
                department.trim());
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(usernameDepartment, password);

        HashMap<String, String> details = new HashMap<>();
        details.put("departmentID", department);
        token.setDetails(department);
        return token;
    }

    private String obtainDepartment(HttpServletRequest request) {
        return request.getParameter("department");
    }
}
