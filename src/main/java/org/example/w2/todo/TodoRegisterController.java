package org.example.w2.todo;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.example.w2.todo.dao.TodoDAO;

import java.io.IOException;

@WebServlet(value = "/todo/register")
@Log4j2
public class TodoRegisterController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("todo register doGet");
        req.getRequestDispatcher("/WEB-INF/todo/register.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //step1 - 브라우저에 전달하는 데이터 수집
        log.info("todo register doPost");

        String title = req.getParameter("title");
        String writer = req.getParameter("writer");

        log.info("title :" + title);
        log.info("writer :" + writer);

        //step2 - 데이터 가공해서 VO, DTO 객체를 생성
        TodoVO vo = TodoVO.builder()
                .title(title)
                .writer(writer)
                .build();


        log.info("todoVO: " + vo);


        //step3 - service, DAO에게 처리를 부탁
        try {
            Integer tno = TodoDAO.INSTANCE.insert(vo);
            //step4 - 결과 전송 Redirect
            resp.sendRedirect("/todo/list?tno=" + tno);
            // 잘 처리하면 list화면으로 redirect

        } catch (Exception e) {
            //step4 - 결과 전송 insert 예외 발생시 Redirect
            resp.sendRedirect("/todo/register?error=input");
            //잘못 처리하면
        }




    }
}
