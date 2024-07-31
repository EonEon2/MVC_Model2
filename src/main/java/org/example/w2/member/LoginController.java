package org.example.w2.member;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.Optional;

@WebServlet(value = "/login")
@Log4j2
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);//화면만 보여주는거니까
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String uid = req.getParameter("uid");
        String upw = req.getParameter("upw");

        log.info("--------------");
        log.info(uid);
        log.info(upw);

        //Jsession_id가 세션 저장소에 있으면 그대로 반환, 없으면 새로 생성해준다.
        HttpSession session = req.getSession();

        //db에서 사용자 정보를 가져온다.
        //결과가 Optional로 나올테니까 사용자가 없을떄를 생각해줘야함.
        try {
            Optional<MemberVO> result = MemberDAO.INSTANCE.get(uid,upw);

            // if else를 이용해서 db에서 가져온 결과값이 없을때는 로그인의 에러창,
            // 값이 있을때는 mypage로 가도록 할 수 있는데 람다식으로 가자.
            // 람다식 해석 : 첫번째는 memberVO와 같은 값(정상적인값)이 있을때, 두번째는 값이 없을때.
            result.ifPresentOrElse( memberVO -> {
                session.setAttribute("uid", memberVO);
                try{
                    resp.sendRedirect("/mypage");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }, () -> {
                try {
                    resp.sendRedirect("/login"); //이대로 그냥 사용하면 try-catch사용을 요함.
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } );


        } catch (Exception e) {
            throw new RuntimeException(e);
        }



    }
}
