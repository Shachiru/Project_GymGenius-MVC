package lk.ijse.gymGenius.model;

import javafx.fxml.FXML;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Order {
    private String orderId;
    private String memberId;
    private Date date;
}
