package org.example.dao;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@Log4j2
public class DBTests {


    @Test
    public void testInsert() throws Exception {

        String sql = "insert into tbl_todo (title, writer)" +
                     "              values (?    ,  ?)";

        String url = "jdbc:mariadb://localhost:13306/webdb";
        String username ="webdbuser";
        String password ="webdbuser";

        Class.forName("org.mariadb.jdbc.Driver");

        try(
                Connection con = DriverManager.getConnection(url, username, password);
                PreparedStatement ps = con.prepareStatement(sql);
                ){

                ps.setString(1, "해야하는일 ");
                ps.setString(2, "writer ");

                int count = ps.executeUpdate(); //1건에 대한 내용이 카운트된다.

                log.info("Count: "+count);

                //테스트의 성공 조건
            Assertions.assertEquals(count,1);

        }catch(Exception e){
            log.error(e);
        }

    }
}
