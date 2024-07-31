package org.example.w2.member;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.example.w2.todo.dao.TodoDAO;

import java.io.IOException;

@WebServlet(value="/mregister")
@Log4j2
public class RegisterController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("doGet");

        req.getRequestDispatcher("/WEB-INF/mregister.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String uid = req.getParameter("uid");
        String upw = req.getParameter("upw");
        String email = req.getParameter("email");

        MemberVO member = MemberVO.builder()
                .uid(uid)
                .upw(upw)
                .email(email)
                .build();

        try {
            Integer tno = MemberDAO.INSTANCE.insert(member);
            resp.sendRedirect("/login");
        } catch (Exception e) {
            resp.sendRedirect("/login?error=input");
            throw new RuntimeException(e);
        }
    }
}
