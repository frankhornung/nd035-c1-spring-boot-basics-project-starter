package com.udacity.jwdnd.course1.cloudstorage.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// https://www.baeldung.com/spring-security-custom-logout-handler
// https://stackoverflow.com/questions/48246874/add-flash-attribute-to-logout-in-spring-security
// https://stackoverflow.com/questions/38479878/spring-security-custom-logout/38480695
// https://stackoverflow.com/questions/58108549/spring-security-logout-pass-parameter-from-logout-to-login

public class logoutService extends SimpleUrlLogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        final FlashMap flashMap = new FlashMap();
        flashMap.put("logoutSuccess", Boolean.TRUE);
        final FlashMapManager flashMapManager = new SessionFlashMapManager();
        flashMapManager.saveOutputFlashMap(flashMap, request, response);
        response.sendRedirect("/login");
        super.handle(request, response, authentication);
    }
}