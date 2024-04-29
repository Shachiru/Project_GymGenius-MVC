package lk.ijse.gymGenius.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class MemberTm {

    private String id;
    private String name;
    private String address;
    private String mobile;
    private String dob;
    private String gender;
}
