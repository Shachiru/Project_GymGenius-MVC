package lk.ijse.gymGenius.repository;

import lk.ijse.gymGenius.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShopRepo {

    public static String generateNextId() throws SQLException {
        String sql = "Select o_id from shop order by o_id desc limit 1";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        String currentOrderId = null;
        if (resultSet.next()) {
            currentOrderId = resultSet.getString(1);
            return nextOrderId(currentOrderId);
        }
        return nextOrderId(null);
    }

    private static String nextOrderId(String currentOrderId) {
        if (currentOrderId != null) {
            String[] split = currentOrderId.split("O ");
            int memberId = Integer.parseInt(split[1]);
            memberId++;
            return "O " + memberId;
        }
        return "O 1";
    }

    public static List<String> getMemId() throws SQLException {
        String sql = "select ID from member";

        Connection connection = DbConnection.getInstance().getConnection();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<String> memberIdList = new ArrayList<>();

        while (resultSet.next()) {
            memberIdList.add(resultSet.getString(1));
        }
        return memberIdList;
    }

    public static List<String> getSupId() throws SQLException {
        String sql = "select ID from supplements";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        List<String> supplementIdList = new ArrayList<>();

        while (resultSet.next()) {
            supplementIdList.add(resultSet.getString(1));
        }
        return supplementIdList;
    }
}
