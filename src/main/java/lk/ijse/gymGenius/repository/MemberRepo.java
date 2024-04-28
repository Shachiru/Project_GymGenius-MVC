package lk.ijse.gymGenius.repository;

import com.mysql.cj.xdevapi.UpdateStatement;
import lk.ijse.gymGenius.db.DbConnection;
import lk.ijse.gymGenius.model.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberRepo {
    public String generateNextId() throws SQLException {
        String sql = "Select ID from Member order by ID desc limit 1";
        Connection connection = DbConnection.getInstance().getConnection();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String id = null;
        if(resultSet.next()){
            id = resultSet.getString(1);
            return splitId(id);
        }
        return splitId(null);
    }

    public static boolean saveMember(Member member) throws SQLException {
        String sql = "INSERT INTO Member VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, member.getId());
        pstm.setObject(2, member.getName());
        pstm.setObject(3, member.getAddress());
        pstm.setObject(4, member.getMobile());
        pstm.setObject(5, member.getDob());
        pstm.setObject(6, member.getGender());

        return pstm.executeUpdate() > 0;
    }

    public static boolean updateMember(Member member) throws SQLException {
        String sql = "Update Member set name=?, address=?, mobile=?, dob=?, gender=? where id=?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, member.getName());
        pstm.setObject(2, member.getAddress());
        pstm.setObject(3, member.getMobile());
        pstm.setObject(4, member.getDob());
        pstm.setObject(5, member.getGender());
        pstm.setObject(6, member.getId());

        return pstm.executeUpdate() > 0;
    }

    public static boolean deleteMember(String id) throws SQLException {
        String sql = "DELETE from Member where id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, id);
        return pstm.executeUpdate() > 0;
    }

    private String splitId(String id) {
        if(id != null){
            String [] split = id.split("M");
            int memberId = Integer.parseInt(split[1]);
            memberId++;
            return "M"+memberId;
        }
        return "M1";
    }

    public static List<Member> getMember() throws SQLException {
        String sql = "SELECT * FROM Member";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Member> memberList = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String mobile = resultSet.getString(4);
            String dob = resultSet.getString(5);
            String gender = resultSet.getString(6);

            Member member = new Member(id, name, address, mobile, dob, gender);
            memberList.add(member);
        }

        return memberList;
    }
}
