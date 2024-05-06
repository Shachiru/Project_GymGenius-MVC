package lk.ijse.gymGenius.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class OrderDetail {

    private String orderId;
    private String supplement_id;
    private int qty;
    private double unitPrice;
    private double total;
}
