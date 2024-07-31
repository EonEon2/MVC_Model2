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

    public List<TodoVO> list (int page) throws Exception {

        int skip = (page - 1) * 10;
        String sql = """
                select * from tbl_todo
                where tno>0 and delflag = false
                order by tno desc
                limit ?,10
                """;

        @Cleanup Connection con = ConnectionUtil.INSTANCE.getDs().getConnection();
        @Cleanup PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, skip);
        @Cleanup ResultSet rs = ps.executeQuery();

        List<TodoVO> todolist = new ArrayList<>();

        while(rs.next()) {
            TodoVO vo = TodoVO.builder()
                    .tno(rs.getInt("tno"))
                    .title(rs.getString("title"))
                    .writer(rs.getString("writer"))
                    .moddate(rs.getTimestamp("moddate"))
                    .regdate(rs.getTimestamp("regdate"))
                    .delflag(rs.getBoolean("delflag"))
                    .build();

            todolist.add(vo);
        }

    return todolist;

    }


    public Integer getTotal() throws Exception {
        String sql = "select count(tno) from tbl_todo where tno > 0 and delflag = false";

        @Cleanup Connection con = ConnectionUtil.INSTANCE.getDs().getConnection();
        @Cleanup PreparedStatement ps = con.prepareStatement(sql);
        @Cleanup ResultSet rs = ps.executeQuery();
        rs.next();

        return rs.getInt(1);
    }


    public Integer register(TodoVO vo) throws Exception {
        String sql = "insert into tbl_todo (title, writer) values (?,?)";

        @Cleanup Connection con = ConnectionUtil.INSTANCE.getDs().getConnection();
        con.setAutoCommit(false);
        @Cleanup PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, vo.getTitle());
        ps.setString(2, vo.getWriter());

        int count = ps.executeUpdate();

        if(count!=1){
            throw new Exception("Abnormal insertion");
        }

        ps.close(); //ps를 다시 사용해야하므로 첫번째 ps를 닫고
        ps = con.prepareStatement("select LAST_INSERT_ID()");

        @Cleanup ResultSet rs = ps.executeQuery();

        rs.next();
        Integer tno = rs.getInt(1);

        con.commit(); //트랜잭션 커밋
        con.setAutoCommit(true); //트랜잭션 오토커밋

        return tno;
    }


    public Optional<TodoVO> get (Integer tno) throws Exception {
        String sql = """
                select tno, title, writer, regdate, moddate, delflag
                from tbl_todo
                where tno = ?
                """;

        @Cleanup Connection con =  ConnectionUtil.INSTANCE.getDs().getConnection();
        @Cleanup PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, tno);
        @Cleanup ResultSet rs = ps.executeQuery();

        if(!rs.next()){
            return Optional.empty();
        }
        TodoVO todovo = TodoVO.builder()
                .tno(rs.getInt("tno"))
                .title(rs.getString("title"))
                .writer(rs.getString("writer"))
                .moddate(rs.getTimestamp("moddate"))
                .regdate(rs.getTimestamp("regdate"))
                .delflag(rs.getBoolean("delflag"))
                .build();

        return Optional.of(todovo);
    }


    public boolean delete (Integer tno) throws Exception {
        String sql = """
                update tbl_todo 
                set moddate = now() , delflag = true
                where tno = ?
                """;
        @Cleanup Connection con =  ConnectionUtil.INSTANCE.getDs().getConnection();
        @Cleanup PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, tno);

        int count = ps.executeUpdate();

        return count == 1;
    }


    public boolean update (TodoVO vo) throws Exception {
        String sql = """
                update tbl_todo
                set
                    title = ? ,writer = ? , moddate=now()
                where tno  = ?
                """;

        @Cleanup Connection con =  ConnectionUtil.INSTANCE.getDs().getConnection();
        @Cleanup PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, vo.getTitle());
        ps.setString(2, vo.getWriter());
        ps.setInt(3, vo.getTno());

        int count = ps.executeUpdate();


        return count == 1;
    }

}
