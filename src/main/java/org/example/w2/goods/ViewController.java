package org.example.w2.goods;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@WebServlet(value = "/goods/view1")
@Log4j2
public class ViewController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Cookie[] cookies = req.getCookies();//브라우저에서 보내준 쿠키들이 있음

        if(cookies == null) {
            log.info("no cookie"); //처음방문해서 쿠키가 없다면
            return;
        }

        for (Cookie cookie : cookies) {
            log.info("cookie: " + cookie.getName());
            log.info("cookie Value: " + cookie.getValue());
            log.info("---------------------------------");
        } //노트북으로 할때는 2개가 나오지만, 휴대폰으로 접속하면 1개만 나온다.

    }
}

