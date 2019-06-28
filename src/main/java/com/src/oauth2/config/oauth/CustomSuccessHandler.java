package com.src.oauth2.config.oauth;

import com.src.oauth2.dao.interfaces.RegisteredUserDao;
import com.src.oauth2.domain.RegisteredUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Priya on 12/4/17.
 */
@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    RegisteredUserDao registeredUserDao;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public CustomSuccessHandler() {
        super();
        setUseReferer(true);
    }

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        String targetUrl = determineTargetUrl(authentication);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        RegisteredUser registeredUser = registeredUserDao.getByUsername(user.getUsername());
        if (response.isCommitted()) {
            System.out.println("Can't redirect");
            return;
        }
        request.getSession().setAttribute("firstName", registeredUser.getFirstName());
        request.getSession().setAttribute("isLoggedIn", true);
        request.getSession().setAttribute("imageKey", registeredUser.getPhotoUrl()); // todo very bad way ... all functions mixed up now in services controllers ... dao is untouched
        Set<GrantedAuthority> grantedAuthorities = (Set<GrantedAuthority>)
                ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAuthorities();
        List<String> roles = grantedAuthorities.stream().map
                (grantedAuthority -> grantedAuthority.getAuthority()).collect(Collectors.toList());
        request.getSession().setAttribute("userRole", roles.get(0)); // todo ... multiple roles should also be there

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    /*
     * This method extracts the roles of currently logged-in user and returns
     * appropriate URL according to his/her role.
     */
    protected String determineTargetUrl(Authentication authentication) {
        String url = "";

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        List<String> roles = new ArrayList<String>();

        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }

        if (isModerator(roles)) {
            url = "/moderator/dashboard";
        } else if (isAdmin(roles)) {
            url = "/admin/dashboard";
        } else if (isUser(roles)) {
            url = "/user/dashboard";
        } else if (isVendor(roles)) {
            url = "/vendor/dashboard";
        } else {
            url = "/accessDenied";
        }

        return url;
    }

    private boolean isUser(List<String> roles) {
        if (roles.contains("ROLE_USER")) {
            return true;
        }
        return false;
    }

    private boolean isAdmin(List<String> roles) {
        if (roles.contains("ROLE_ADMIN")) {
            return true;
        }
        return false;
    }

    private boolean isModerator(List<String> roles) {
        if (roles.contains("ROLE_MODERATOR")) {
            return true;
        }
        return false;
    }

    private boolean isVendor(List<String> roles) {
        if (roles.contains("ROLE_VENDOR")) {
            return true;
        }
        return false;
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

}
