package org.example.w2.member;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;


@WebServlet(value = "/mypage")
@Log4j2
public class MyPageController extends HttpServlet { //redirect로 불러온 애는 get방식으로 받기 때문에

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        log.info("get");

        //로그인한적이 없이 url로 들어오더라도 세션을 다시 만들지않는다.
        //mypage의 url로 바로 들어오는것을 막기위해
        HttpSession session = req.getSession(false);

        //로그인했던 세션이 없다면 로그인창으로 보내버려야함.
        //로그인했는지 확인하려고 getAttribute로 가져왔는데 없다면 로그인창으로 보내버려야함.
        if(session == null || session.getAttribute("uid") == null) {
            resp.sendRedirect("/login");
            return;
        }

        req.getRequestDispatcher("/WEB-INF/mypage.jsp").forward(req, resp);
    }
}
