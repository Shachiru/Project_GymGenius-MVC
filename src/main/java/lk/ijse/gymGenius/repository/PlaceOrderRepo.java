package lk.ijse.gymGenius.repository;

import lk.ijse.gymGenius.db.DbConnection;
import lk.ijse.gymGenius.model.PlaceOrder;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceOrderRepo {

    public static boolean orderPlaced(PlaceOrder placeOrder) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        try {
            boolean isOrderSaved = OrderRepo.saveOrder(placeOrder.getOrder());
            if (isOrderSaved) {
                boolean isOrderDetailSaved = OrderDetailRepo.saveOrderDetails(placeOrder.getOrderDetail());
                if (isOrderDetailSaved) {
                    boolean isSupplementUpdated = SupplementRepo.updateSupplementQty(placeOrder.getOrderDetail());
                    if (isSupplementUpdated) {
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        }catch (SQLException e) {
            connection.rollback();
            return false;
        }finally {
            connection.setAutoCommit(true);
        }
    }
}
