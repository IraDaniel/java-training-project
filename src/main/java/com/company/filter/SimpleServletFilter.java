package com.company.filter;

import javax.servlet.*;
import java.io.IOException;

public class SimpleServletFilter implements Filter{

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        System.out.println("Entering SimpleServletFilter");
        request.setAttribute("hello","SimpleServletFilter!");
        System.out.println("filter");
        filterChain.doFilter(request, response);
        System.out.println("Exiting SimpleServletFilter");
    }

    public void destroy() {
    }
}
