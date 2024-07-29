package org.example.w2.todo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.example.w2.bmi.Scores;
import org.example.w2.common.Pageinfo;
import org.example.w2.common.StringUtil;
import org.example.w2.todo.dao.TodoDAO;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@WebServlet(value = "/todo/list") //목록페이지
@Log4j2
public class TodoListController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pageStr = req.getParameter("page"); //url주소창에 example.com/todo/list?page=21 에 해당하는 21의 값을 가져옴

        log.info("pageStr: " + pageStr);

        int page = StringUtil.getInt(pageStr, 1);
        //페이지 이상하면 1페이로 넘어가기

        try {
            int total = TodoDAO.INSTANCE.getTotal();
            //예외처리를 해줘야하는데 던지면 오버라이딩 상속에 문제가 생김 그래서 일단 try-catch

            Pageinfo pageInfo = new Pageinfo(page, 10, total);
            //현재 / 게시물개수 / 데이터개수

            List<TodoVO> todoList = TodoDAO.INSTANCE.list(pageInfo.getPage());
            //해당페이지를 주면 todoList의 값을 준다.

            req.setAttribute("todolist", todoList);

            req.setAttribute("pageInfo", pageInfo);
            //더미데이터를 jsp로 옮기기

            req.getRequestDispatcher("/WEB-INF/todo/list.jsp").forward(req, resp); //view
            //list.jsp로 출발

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
