package org.example.w2.member;

import lombok.Cleanup;
import org.example.w2.common.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

public enum MemberDAO {
    INSTANCE;
            //null 파악해줘야해서 Optional해줘야함.
    public Optional<MemberVO> get(String word, String pw) throws Exception {//id 혹은 email이니까 word라고

        String query = """
                select * from tbl_member
                where
                    (uid = ? or email = ? )
                and
                    upw = ?
                and
                    delflag = false
                """;

        @Cleanup Connection con = ConnectionUtil.INSTANCE.getDs().getConnection();
        @Cleanup PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, word);
        ps.setString(2, word);
        ps.setString(3, pw);

        @Cleanup ResultSet rs = ps.executeQuery();
        //select니까 rs사용해주고.

        if (!rs.next()) {
            return Optional.empty(); //옛날에는 null로 반환했지만 이제는 Optional을 사용.
        }
        //한줄 이동하고
        MemberVO member = MemberVO.builder()
                .mno(rs.getInt("mno")) //sql문에서 *from을 사용해서 문자열로 적는것
                .uid(rs.getString("uid"))
                .upw(rs.getString("upw"))
                .email(rs.getString("email"))
                .delFlag(rs.getBoolean("delflag"))
                .build();

        return Optional.of(member);
    }

    public Integer insert(MemberVO member) throws Exception {

        String sql = "insert into tbl_member (uid,upw,email) values (? , ? , ? )";

        @Cleanup Connection con = ConnectionUtil.INSTANCE.getDs().getConnection();
        @Cleanup PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, member.getUid());
        ps.setString(2, member.getUpw());
        ps.setString(3, member.getEmail());

        Integer count = ps.executeUpdate();

        if(count!=1){
            throw new Exception("Abnormal insertion");
        }

        ps.close(); //ps를 다시 사용해야하므로 첫번째 ps를 닫고
        ps = con.prepareStatement("select LAST_INSERT_ID()");

        @Cleanup ResultSet rs = ps.executeQuery();

        rs.next();
        Integer mno = rs.getInt(1);

        con.commit(); //트랜잭션 커밋
        con.setAutoCommit(true); //트랜잭션 오토커밋

        return mno;
    }
}
