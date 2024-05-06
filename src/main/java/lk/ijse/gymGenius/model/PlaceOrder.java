package lk.ijse.gymGenius.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class PlaceOrder {
    private Order order;
    private List<OrderDetail>orderDetail;
}
