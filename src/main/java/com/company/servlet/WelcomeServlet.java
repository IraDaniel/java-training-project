package com.company.servlet;


import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;


public class WelcomeServlet implements Servlet {

    private ServletConfig config;

    public void init(ServletConfig config) throws ServletException{
        this.config = config;
    }

    public ServletConfig getServletConfig(){
        return this.config;
    }

    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException{
        String filter = String.valueOf(request.getAttribute("hello"));
        System.out.println("WelcomeServlet:" + filter);
        response.setContentType( "text/html" );
        PrintWriter out = response.getWriter();
        out.println( "<html><head>" );
        out.println( "<title>A Sample Servlet!</title>" );
        out.println( "</head>" );
        out.println( "<body>" );
        out.println( "<h1>Hello, World!</h1>" );
        out.println( "</body></html>" );
        out.close();
    }

    public String getServletInfo(){
        return "Hello World";
    }

    public void destroy(){

    }
}
