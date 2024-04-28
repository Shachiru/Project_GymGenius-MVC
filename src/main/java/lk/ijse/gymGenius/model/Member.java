package lk.ijse.gymGenius.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Member {

    private String id;
    private String name;
    private String address;
    private String mobile;
    private String dob;
    private String gender;

}
