package org.example.w2.member;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@WebServlet(value="/logout")
@Log4j2
public class LogoutController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //세션이 기존에 없었으면 이 페이지로 들어왔을때 자동으로 만들지 않기
        HttpSession session = req.getSession(false);

        if(session == null) {
            resp.sendRedirect("/");
            return;
        }

        //removeAttrivute를 사용하지 않고 invalidate만 사용해도 되는데
        //invalidate는 바로 지워지는게 아니고, 시간이 지나야 없어진다.
        //바로 없애버리기 위해선 removeAttribute를 사용
        session.removeAttribute("uid");
        session.invalidate(); //무효화를 사용하자
        resp.sendRedirect("/");

    }
}
