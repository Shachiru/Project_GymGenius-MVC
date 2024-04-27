package lk.ijse.gymGenius.repository;

import lk.ijse.gymGenius.db.DbConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberRepo {
    public static String generateNextId() throws SQLException {
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

    private static String splitId(String id) {
        if(id != null){
            String [] split = id.split("M");
            int memberId = Integer.parseInt(split[1]);
            memberId++;
            return "M"+memberId;
        }
        return "M1";
    }
}
