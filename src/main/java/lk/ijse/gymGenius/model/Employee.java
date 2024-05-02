package lk.ijse.gymGenius.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Employee {
    private String id;
    private String name;
    private String address;
    private String mobile;
    private String empRole;
    private String userId;
}