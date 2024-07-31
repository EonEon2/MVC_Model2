package org.example.w2.todo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.example.w2.common.Pageinfo;
import org.example.w2.common.StringUtil;
import org.example.w2.todo.dao.TodoDAO;

import java.io.IOException;
import java.util.List;

@Log4j2
@WebServlet(value= "/todo/list")
public class TodoListController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("doGet");

        String pageStr = req.getParameter("page");
        log.info("pageStr:" + pageStr);

        int page = StringUtil.getInt(pageStr,1);

        try {
            int total = TodoDAO.INSTANCE.getTotal();

            Pageinfo pageinfo = new Pageinfo(page,10,total);

            List<TodoVO> todoList = TodoDAO.INSTANCE.list(pageinfo.getPage());

            log.info(total);


            req.setAttribute("pageInfo",pageinfo);

            req.setAttribute("todoList",todoList);


            req.getRequestDispatcher("/WEB-INF/todo/list.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
