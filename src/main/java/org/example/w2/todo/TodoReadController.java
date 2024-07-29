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

@WebServlet(value = {"/todo/get","/todo/edit"})
@Log4j2
public class TodoReadController extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("doGet");

        String uri = req.getRequestURI(); //현재 브라우저의 uri창이 어떤건지 알아보는 방법

        // /todo/get 일때 마지막 슬래쉬를 끊어내면 get 혹은 edit 을 가져온다.
        String jspName = uri.substring(uri.lastIndexOf('/') + 1);

        String tnoStr = req.getParameter("tno"); //정보가져오는거 문자열로 가져오니까 밑에서 숫자로 바꿔줘야함

        Integer tno = StringUtil.getInt(tnoStr,-1); //오류나면 -1로 반환하기

        log.info("tno: "+tno);

        try {
            Optional<TodoVO> result = TodoDAO.INSTANCE.get(tno);

            TodoVO vo = result.orElseThrow(); //없으면 예외가 처리는것, 없으면 아래로 빠지게됨 내부에 예외기능이 내포되어있는 메소드

            req.setAttribute("todo", vo); //vo의 값을 jsp에서 todo라는 이름으로 사용할거야.

            req.getRequestDispatcher("/WEB-INF/todo/"+jspName+".jsp").forward(req, resp);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }



    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI(); //현재 브라우저의 uri창이 어떤건지 알아보는 방법

        String job = uri.substring(uri.lastIndexOf('/') + 1);

        if(job.equals("get")){ //get으로 들어오게 된다면 list로 보내버리기
            resp.sendRedirect("/todo/list");
            return;
        }

        String tnoStr = req.getParameter("tno");

        Integer tno = StringUtil.getInt(tnoStr,-1);

        String title = req.getParameter("title");
        String writer = req.getParameter("writer");

        TodoVO vo = TodoVO.builder()
                .tno(tno)
                .title(title)
                .writer(writer)
                .build();

        try {
            boolean result = TodoDAO.INSTANCE.update(vo);
            resp.sendRedirect("/todo/get?tno="+tno);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
