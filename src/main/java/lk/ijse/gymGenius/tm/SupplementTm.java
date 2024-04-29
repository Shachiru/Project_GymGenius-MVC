package lk.ijse.gymGenius.tm;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class SupplementTm {

    private String id;
    private String productName;
    private String unitPrice;
    private String qty;
}
