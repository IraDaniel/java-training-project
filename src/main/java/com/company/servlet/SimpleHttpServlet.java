package com.company.servlet;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SimpleHttpServlet extends HttpServlet {

    private String message;

    public SimpleHttpServlet() {
        super();
        message = "SimpleHttpServlet Hello World";
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        print();
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

    private void print() {
        System.out.println(message);
    }
}
