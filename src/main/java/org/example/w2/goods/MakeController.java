package org.example.w2.goods;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@WebServlet("/goods/make")
@Log4j2
public class MakeController extends HttpServlet { //get방식으로 test 해보기

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("doGet");

        Cookie goodsCookie = new Cookie("goods", "123456789"); //키 와 값 형태이므로 생성자를 사용
        //goodsCookie.setPath("/");

        goodsCookie.setMaxAge(60*60); //초 단위 계산이므로 = 1시간

        resp.addCookie(goodsCookie); //개발자가 직접만든 쿠키니까 이렇게 처리 해줘야함.


    }
}
