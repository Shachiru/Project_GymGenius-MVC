package lk.ijse.gymGenius.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Supplement {

    private String id;
    private String productName;
    private String unitPrice;
    private String qty;
}
