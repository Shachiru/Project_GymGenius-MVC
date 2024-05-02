package lk.ijse.gymGenius.repository;

import lk.ijse.gymGenius.db.DbConnection;
import lk.ijse.gymGenius.model.Supplement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplementRepo {

    public static String generateNextId() throws SQLException {
        String sql = "Select ID from supplements order by ID desc limit 1";
        Connection connection = DbConnection.getInstance().getConnection();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String id = null;
        if (resultSet.next()) {
            id = resultSet.getString(1);
            return splitId(id);
        }
        return splitId(null);
    }

    private static String splitId(String id) {
        if (id != null) {
            String [] split = id.split("Sup ");
            int supId = Integer.parseInt(split[1]);
            supId++;
            return "Sup " + supId;
        }
        return "Sup 1";
    }

    public static List<Supplement> getSupplement() throws SQLException {
        String sql = "SELECT * FROM supplements";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Supplement> supplementList = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String unitPrice = resultSet.getString(3);
            String qty = resultSet.getString(4);

            Supplement supplement = new Supplement(id, name, unitPrice, qty);
            supplementList.add(supplement);
        }
        return supplementList;
    }

    public static boolean saveSupplement(Supplement supplement) throws SQLException {
        String sql = "INSERT INTO supplements VALUES(?,?,?,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, supplement.getId());
        pstm.setObject(2, supplement.getProductName());
        pstm.setObject(3, supplement.getUnitPrice());
        pstm.setObject(4, supplement.getQty());

        return pstm.executeUpdate() > 0;
    }

    public boolean deleteSupplement(String id) throws SQLException {
        String sql = "DELETE from supplements where ID = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public boolean updateSupplement(Supplement supplement) throws SQLException {
        String sql = "UPDATE Supplements SET Description=?, UnitPrice=?, Qty=? WHERE ID=?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, supplement.getProductName());
        pstm.setObject(2, supplement.getUnitPrice());
        pstm.setObject(3, supplement.getQty());
        pstm.setObject(4, supplement.getId());

        return pstm.executeUpdate() > 0;
    }

}
