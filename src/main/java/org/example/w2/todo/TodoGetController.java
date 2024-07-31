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
import java.util.Optional;

@Log4j2
@WebServlet(value = {"/todo/get" , "/todo/edit"})
public class TodoGetController extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String uri = req.getRequestURI();

        String jspName = uri.substring(uri.lastIndexOf("/")+1);

        String tnoStr = req.getParameter("tno");
        Integer tno = StringUtil.getInt(tnoStr,-1);

        try {
            Optional<TodoVO> result = TodoDAO.INSTANCE.get(tno);

            TodoVO vo = result.orElseThrow();

            req.setAttribute("todo", vo);

            req.getRequestDispatcher("/WEB-INF/todo/"+jspName+".jsp").forward(req, resp);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();

        String job = uri.substring(uri.lastIndexOf("/")+1);

        if(job.equals("get")){
            resp.sendRedirect("/todo/list");
            return;
        }

        String tnoStr = req.getParameter("tno");
        String title = req.getParameter("title");
        String writer = req.getParameter("writer");

        Integer tno = StringUtil.getInt(tnoStr,-1);

        TodoVO vo = TodoVO.builder()
                .tno(tno)
                .title(title)
                .writer(writer)
                .build();

        boolean result = false;

        try {
            result = TodoDAO.INSTANCE.update(vo);
            resp.sendRedirect("/todo/get?tno="+tno);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
