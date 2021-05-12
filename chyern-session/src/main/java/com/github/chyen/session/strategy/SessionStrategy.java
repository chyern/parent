package com.github.chyen.session.strategy;

import com.github.chyen.session.processor.SessionManagement;
import org.springframework.session.web.http.HttpSessionStrategy;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2021/5/10
 */
public class SessionStrategy implements HttpSessionStrategy {

    @Override
    public String getRequestedSessionId(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (SessionManagement.SESSION_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return "";
    }

    @Override
    public void onNewSession(org.springframework.session.Session session, HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie(SessionManagement.SESSION_NAME, session.getId());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @Override
    public void onInvalidateSession(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie(SessionManagement.SESSION_NAME, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
