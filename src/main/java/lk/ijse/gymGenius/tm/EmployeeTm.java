package lk.ijse.gymGenius.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor

public class EmployeeTm {
    private String id;
    private String name;
    private String address;
    private String mobile;
    private String empRole;
    private String userId;
}
