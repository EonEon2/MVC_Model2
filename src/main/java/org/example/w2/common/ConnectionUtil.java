package org.example.w2.common;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public enum ConnectionUtil {

    INSTANCE;

    //결과적으로 만들어내야하는것
    private HikariDataSource ds;

    //Connection pull 하기위함.
    private ConnectionUtil() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.mariadb.jdbc.Driver");
        config.setJdbcUrl("jdbc:mariadb://localhost:13306/webdb");
        config.setUsername("webdbuser");
        config.setPassword("webdbuser");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setConnectionTimeout(1000*19); //해당시간이 지나면 끊고 다른 커넥션 선택
        config.setMaximumPoolSize(20); //톰켓(WAS)의 쓰레드 개수 (최대 동접자)
        config.setMinimumIdle(1); //처음부터 연결을 많이하면 비용문제가 생길수 있다.

        ds = new HikariDataSource(config);
    }


    //필요할때마다 가져갈수있도록
    public HikariDataSource getDs(){
        return ds;
    }


}
