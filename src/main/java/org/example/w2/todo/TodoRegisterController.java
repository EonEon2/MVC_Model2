package org.example.w2.todo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.example.w2.common.StringUtil;
import org.example.w2.todo.dao.TodoDAO;

import java.io.IOException;

@Log4j2
@WebServlet(value="/todo/register")
public class TodoRegisterController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("doGet");

        req.getRequestDispatcher("/WEB-INF/todo/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("doPost");

        String title = req.getParameter("title");
        String writer = req.getParameter("writer");

        TodoVO vo = TodoVO.builder()
                .title(title)
                .writer(writer)
                .build();

        try {
            Integer tno = TodoDAO.INSTANCE.register(vo);
            resp.sendRedirect("/todo/list?tno="+tno);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("/todo/register?error=input");
        }

    }
}
