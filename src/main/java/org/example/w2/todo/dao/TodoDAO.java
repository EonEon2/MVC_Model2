
package org.example.w2.todo.dao;

import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;
import org.example.w2.common.ConnectionUtil;
import org.example.w2.todo.TodoVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
public enum TodoDAO {
    INSTANCE;

    public Integer insert(TodoVO todoVO) throws Exception {
        //insert update delete는 대게 integer 혹은 boolean을 사용한다.
        //return 값으로 대부분 tno와 같은 접수를 전달함.

        String sql = "insert into tbl_todo (title, writer) values (?,?)";

        @Cleanup Connection con = ConnectionUtil.INSTANCE.getDs().getConnection();

        @Cleanup PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, todoVO.getTitle());
        ps.setString(2, todoVO.getWriter());

        int count = ps.executeUpdate(); //db에 1건 추가되어야하니까.

        if(count != 1){ //count값이 1이 아니면 잘못된것이니까.
            throw new Exception("Abnormal insertion");
        }

        ps.close(); //실험
        ps = con.prepareStatement("select LAST_INSERT_ID()"); //실험

        @Cleanup ResultSet rs = ps.executeQuery(); //실험

        rs.next(); //실험
        Integer tno = rs.getInt(1); // 실험

        log.info("TNO:..." +tno);

        //con.commit(); //실험
        //con.setAutoCommit(true); //실험

        return tno;
    }

    //페이지의 total count값을 가져와야해서 db와 연결해줘야함
    public List<TodoVO> list(int page) throws Exception{ //arraylist는 클래스 list 더 추상적이니까 사용함

        int skip = (page -1) *10; //db에서의 limit

        String query = """
            select * from tbl_todo
                             where
                                 tno > 0
                               and
                                 delflag = false
                             order by tno desc
                    limit ?,10
        """;
        //limit 의 첫번째를 변수처리하기위해서 db안에서는 limit값에 값밖에 안들어가니까.
        //1페이지는 1번부터 10번까지 있으므로 limit 0 10
        //2페이지는 11번부터 20번까지 있으므로 limit 10 10
        //그러면 limit (page-1)*10 10

        @Cleanup Connection con = ConnectionUtil.INSTANCE.getDs().getConnection(); //connection pool을 사용하기 위함.
        @Cleanup PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, skip); //첫번째 물음표의 값은 skip이야.
        @Cleanup ResultSet rs = ps.executeQuery();

        List<TodoVO> list = new ArrayList<>(); //데이터타입이 List<TodoVO> 인 ArrayList 생성

        while (rs.next()) { //re.next() 레코드 한줄씩 넘기기

            TodoVO vo = TodoVO.builder()
                    .tno(rs.getInt("tno")) //tno에다가 tno 컬럼값을 읽어서 넣자
                    .title(rs.getString("title"))
                    .writer(rs.getString("writer"))
                    .regDate(rs.getTimestamp("regdate"))
                    .modDate(rs.getTimestamp("moddate"))
                    .delFlag(rs.getBoolean("delflag"))
                    .build();

            list.add(vo); //list에 한줄씩 추가하기

        }


        return list;
    }

    public int getTotal() throws Exception {
        log.info("GetTotal");

        String query = "select count(tno) from tbl_todo where tno > 0 and delflag = false";

        @Cleanup Connection con = ConnectionUtil.INSTANCE.getDs().getConnection();
        @Cleanup PreparedStatement ps = con.prepareStatement(query);
        @Cleanup ResultSet rs = ps.executeQuery();

        rs.next(); //앞에 쓸대없는 정보는 읽지않고 한개 넘기기 tno count 85016 읽기위해서
            //rs의 count라는 한개의 행이 필요한데, 첫번째는 버리고 두번째에 그 카운트의 개수가 적혀있음.
        int total = rs.getInt(1);
            //getInt는 ResultSet에서 제공하는 메소드로 해당되는 인덱스의 값을 정수로 가져온다.


        return total;
    }

    public Optional<TodoVO> get(Integer tno) throws Exception {

        final String query = """
                 select tno, title, writer, regdate, moddate, delflag
                 from 
                     tbl_todo
                 where tno = ?
                 """;
        @Cleanup Connection con = ConnectionUtil.INSTANCE.getDs().getConnection();
        @Cleanup PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, tno);
        @Cleanup ResultSet rs = ps.executeQuery();

        if(!rs.next()){ //원래 첫번째는 next로 넘기는데, 그 첫번째가 없다면.
            return Optional.empty();
        }

        TodoVO vo = TodoVO.builder()
                .tno(rs.getInt("tno")) //tno에다가 tno 컬럼값을 읽어서 넣자
                .title(rs.getString("title"))
                .writer(rs.getString("writer"))
                .regDate(rs.getTimestamp("regdate"))
                .modDate(rs.getTimestamp("moddate"))
                .delFlag(rs.getBoolean("delflag"))
                .build();

        return Optional.of(vo);
    }

    public boolean delete(Integer tno) throws Exception {
        String sql = "update tbl_todo set moddate = now() , delflag = true where tno = ?";


        @Cleanup Connection con = ConnectionUtil.INSTANCE.getDs().getConnection();
        @Cleanup PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, tno);

        int count = ps.executeUpdate(); //dml은 몇건이 변경되었는지 int로 출력됨

        return count == 1; //count가 1이면 true
    }

    public boolean update(TodoVO todoVO) throws Exception {
        String sql = """
                update tbl_todo
                    set 
                        title = ?,
                        writer = ?,
                        moddate = now()
                where tno = ?
                """;
        @Cleanup Connection con = ConnectionUtil.INSTANCE.getDs().getConnection();
        @Cleanup PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, todoVO.getTitle());
        ps.setString(2, todoVO.getWriter());
        ps.setInt(3, todoVO.getTno());

        int count = ps.executeUpdate();

        return count == 1 ? true : false;

    }
}
