package lk.ijse.gymGenius.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Order {

    private String id;
    private String date;
    private String m_id;
    private String supplement_id;
    private String qty;
    private String total;
}
