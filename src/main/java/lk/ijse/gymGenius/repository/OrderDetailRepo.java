package lk.ijse.gymGenius.repository;

import lk.ijse.gymGenius.db.DbConnection;
import lk.ijse.gymGenius.model.OrderDetail;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailRepo {

    public static boolean saveOrderDetails(List<OrderDetail> orderDetail) throws SQLException {
        for (OrderDetail od : orderDetail){
            if(!orderSave(od)){
                return false;
            }
        }
        return true;
    }

    private static boolean orderSave(OrderDetail od) throws SQLException {
        String sql = "Insert into order_detail values (?,?,?,?,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setString(1,od.getOrderId());
        pstm.setString(2,od.getSupplement_id());
        pstm.setInt(3,od.getQty());
        pstm.setDouble(4,od.getUnitPrice());
        pstm.setDouble(5,od.getTotal());

        return pstm.executeUpdate() > 0;
    }
}
