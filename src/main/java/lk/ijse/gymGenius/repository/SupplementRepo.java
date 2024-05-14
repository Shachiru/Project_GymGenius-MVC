package lk.ijse.gymGenius.repository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import lk.ijse.gymGenius.db.DbConnection;
import lk.ijse.gymGenius.model.OrderDetail;
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

    public static Supplement searchSupplement(String supplementId) throws SQLException {
        String sql = "select * from supplements where ID = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, supplementId);
        ResultSet resultSet = pstm.executeQuery();
        Supplement supplement = null;

        if (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String unitPrice = resultSet.getString(3);
            String qty = resultSet.getString(4);

            supplement = new Supplement(id, name, unitPrice, qty);
        }
        return supplement;
    }

    public static boolean updateSupplementQty(List<OrderDetail> orderDetail) throws SQLException {
        for (OrderDetail od : orderDetail){
            if(!updateSupplementQty(od)){
                return false;
            }
        }
        return true;
    }

    private static boolean updateSupplementQty(OrderDetail od) throws SQLException {
        String sql = "update supplements set Qty = Qty - ? where ID = ? ";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setInt(1,od.getQty());
        pstm.setString(2,od.getSupplement_id());

        return pstm.executeUpdate() > 0;
    }

    public static ObservableList<XYChart.Series<String, Integer>> getDataToBarChart() throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();
        String sql="SELECT Description,Qty FROM supplements ";

        ObservableList<XYChart.Series<String, Integer>> datalist = FXCollections.observableArrayList();

        PreparedStatement pstm= connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        // Creating a new series object
        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        while(resultSet.next()){
            String itemName = resultSet.getString("Description");
            int itemQty = resultSet.getInt("Qty");
            series.getData().add(new XYChart.Data<>(itemName, itemQty));
        }

        datalist.add(series);
        return datalist;
    }

    public boolean deleteSupplement(String id) throws SQLException {
        String sql = "DELETE from supplements where ID = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public boolean updateSupplement(Supplement supplement) throws SQLException {
        String sql = "UPDATE supplements SET Description=?, UnitPrice=?, Qty=? WHERE ID=?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, supplement.getProductName());
        pstm.setObject(2, supplement.getUnitPrice());
        pstm.setObject(3, supplement.getQty());
        pstm.setObject(4, supplement.getId());

        return pstm.executeUpdate() > 0;
    }

    public int countSupplement() throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "Select count(ID) as sup_count from supplements";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            int supCount = Integer.parseInt(resultSet.getString("sup_count"));
            return supCount;
        }
        return Integer.parseInt(null);
    }
}
