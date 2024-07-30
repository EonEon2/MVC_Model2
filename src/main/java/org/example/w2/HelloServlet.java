package org.example.w2;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lombok.extern.log4j.Log4j2;

@Log4j2
@WebServlet(name = "helloServlet", value = "/hello")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String msg = request.getParameter("msg");

        log.info("msg: "+msg );

        Integer.parseInt(msg);

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