package org.example.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;
import org.example.w2.bmi.Scores;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.*;

@Log4j2
public class DBTests {

    @Test
    public void testRemove() throws Exception {

        @Cleanup Scores scores = new Scores();

        int[] arr = {1, 2, 3};
        log.info(arr[3]);

    }

    @Test
    public void testInsert() throws Exception {

        String sql = "insert into tbl_todo (title, writer)" +
                "              values (?    ,  ?)";

        String url = "jdbc:mariadb://localhost:13306/webdb";
        String username = "webdbuser";
        String password = "webdbuser";

        Class.forName("org.mariadb.jdbc.Driver");


        @Cleanup Connection con = DriverManager.getConnection(url, username, password);
        @Cleanup PreparedStatement ps = con.prepareStatement(sql);


        ps.setString(1, "해야하는일 ");
        ps.setString(2, "writer ");

        int count = ps.executeUpdate(); //1건에 대한 내용이 카운트된다.

        log.info("Count: " + count);

        //테스트의 성공 조건
        Assertions.assertEquals(count, 1);


    }


    @Test
    public void testSelect1() throws Exception {

        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.mariadb.jdbc.Driver");
        config.setJdbcUrl("jdbc:mariadb://localhost:13306/webdb");
        config.setUsername("webdbuser");
        config.setPassword("webdbuser");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        HikariDataSource ds = new HikariDataSource(config);


        @Cleanup Connection con = ds.getConnection();


        String sql = "select * from tbl_todo where tno > 0 order by tno desc limit 0,10";

        long start = System.currentTimeMillis();

        @Cleanup PreparedStatement ps = con.prepareStatement(sql);
        @Cleanup ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            log.info(rs.getInt(1)); //tno
            log.info(rs.getString(2)); //title
            log.info(rs.getString(3)); //writer
//            log.info(rs.getTimestamp(4));
//            log.info(rs.getTimestamp(5));
//            log.info(rs.getBoolean(6));
        }

        long end = System.currentTimeMillis();

        log.info(end - start);

    }



    @Test
    public void testSelectOLD() throws Exception {

        String url = "jdbc:mariadb://49.174.76.109:13306/webdb";
        String username = "webdbuser";
        String password = "webdbuser";

        Class.forName("org.mariadb.jdbc.Driver");

        long start = System.currentTimeMillis();

        for (int i = 0; i < 100; i++) {

            @Cleanup Connection con = DriverManager.getConnection(url, username, password);
            String sql = "select * from tbl_todo where tno > 0 order by tno desc limit 0,10";
            @Cleanup PreparedStatement ps = con.prepareStatement(sql);
            @Cleanup ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // log.info(rs.getInt(1)); //tno
                // log.info(rs.getString(2)); //title
                // log.info(rs.getString(3)); //writer
                // log.info(rs.getTimestamp(4));
                // log.info(rs.getTimestamp(5));
                // log.info(rs.getBoolean(6));
            }//end while
        }

        long end = System.currentTimeMillis();
        log.info("----------------------------------------");
        log.info(end - start);
    }


    @Test
    public void testSelect() throws Exception {

        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.mariadb.jdbc.Driver");
        config.setJdbcUrl("jdbc:mariadb://localhost:13306/webdb");
        config.setUsername("webdbuser");
        config.setPassword("webdbuser");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setConnectionTimeout(1000*19); //해당시간이 지나면 끊고 다른 커넥션 선택
        config.setMaximumPoolSize(100); //톰켓(WAS)의 쓰레드 개수 (최대 동접자)
        config.setMinimumIdle(1); //처음부터 연결을 많이하면 비용문제가 생길수 있다.

        HikariDataSource ds = new HikariDataSource(config);

        long start = System.currentTimeMillis();

        for (int i = 0; i < 100; i++) {

            @Cleanup Connection con = ds.getConnection();
            String sql = "select * from tbl_todo where tno > 0 order by tno desc limit 0,10";
            @Cleanup PreparedStatement ps = con.prepareStatement(sql);
            @Cleanup ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // log.info(rs.getInt(1)); //tno
                // log.info(rs.getString(2)); //title
                // log.info(rs.getString(3)); //writer
                // log.info(rs.getTimestamp(4));
                // log.info(rs.getTimestamp(5));
                // log.info(rs.getBoolean(6));
            }//end while
        }

        long end = System.currentTimeMillis();
        log.info("----------------------------------------");
        log.info(end - start);
    }
}


