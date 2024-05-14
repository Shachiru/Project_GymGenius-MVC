package lk.ijse.gymGenius.model;

import javafx.scene.chart.PieChart;
import lk.ijse.gymGenius.db.DbConnection;
import lk.ijse.gymGenius.repository.SupplementRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DashboardDetail {
    SupplementRepo supplementRepo = new SupplementRepo();

    public ArrayList<PieChart.Data> getPieChartData() throws SQLException {
        Connection connection =DbConnection.getInstance().getConnection();
        String sql = "select sup_id,SUM(qty)as orderCount from order_detail group by sup_id order by ordercount desc limit 5";

        PreparedStatement pstm = connection.prepareStatement(sql);

        ArrayList<PieChart.Data> data = new ArrayList<>();
        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
           Supplement supplement = supplementRepo.searchSupplement(resultSet.getString(1));
            data.add(
                    new PieChart.Data(supplement.getProductName(), resultSet.getInt(2))
            );
        }
        return data;
    }
}
