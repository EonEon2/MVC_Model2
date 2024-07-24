package org.example.w2;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String msg = "HelloServlet";

        request.setAttribute("msg", msg);


        //
    try {
        request.getRequestDispatcher("/WEB-INF/hello2.jsp")
                .forward(request, response);
    } catch(Exception e){
        e.printStackTrace();
    }


    }

    public void destroy() {
    }
}