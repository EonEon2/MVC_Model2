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
@WebServlet(value="/todo/remove")
public class TodoRemoveController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("doPost");
        String tnoStr = req.getParameter("tno");
        int tno = StringUtil.getInt(tnoStr,-1);

        boolean result = false;
        try {

             result = TodoDAO.INSTANCE.delete(tno);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        resp.sendRedirect("/todo/list?result="+result);
    }
}
