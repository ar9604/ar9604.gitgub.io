package com.study.filter;

import com.study.pojo.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter({"/pages/manager/*", "/manager/bookServlet"})
public class ManagerFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
        User user = (User) httpServletRequest.getSession().getAttribute("user");
        if (user != null){
            filterChain.doFilter(servletRequest,servletResponse);
        }else {
            httpServletRequest.getRequestDispatcher( "/pages/user/login.jsp").forward(servletRequest,servletResponse);
        }

    }

    @Override
    public void destroy() {

    }
}
