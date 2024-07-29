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

@WebServlet(value= "/todo/remove")
@Log4j2
public class TodoRemoveController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        log.info("doPost");

        String tnoStr = req.getParameter("tno");

        Integer tno = StringUtil.getInt(tnoStr,-1);

        //삭제가 되면 true or false가 된다는 가정
        boolean result = false;

        try {
            result = TodoDAO.INSTANCE.delete(tno);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //삭제가 되면 리스트 화면
        //redirect할때는 get방식밖에 못쓰니까 쿼리스트링 써줘야함
        resp.sendRedirect("/todo/list?result="+result);






    }
}
