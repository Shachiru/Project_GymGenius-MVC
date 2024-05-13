package lk.ijse.gymGenius.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataValidateController {


    //userLogin
    public static boolean validateName(String name) {
        String nameRegex = "admin";
            Pattern pattern = Pattern.compile(nameRegex);
            Matcher matcher = pattern.matcher(name);
            return matcher.matches();
        }

    public static boolean validatePassword(String password) {
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
            Pattern pattern = Pattern.compile(passwordRegex);
            Matcher matcher = pattern.matcher(password);
            return matcher.matches();
    }

    // employee
    public static boolean validateEmpName(String empName) {
        String nameRegex = "^[A-Z][a-z]*$";
            Pattern pattern = Pattern.compile(nameRegex);
            Matcher matcher = pattern.matcher(empName);
            return matcher.matches();
    }
    public static boolean validateEmpMobile(String empMobile) {
        String mobileRegex = "\\d{10}";
            Pattern pattern = Pattern.compile(mobileRegex);
            Matcher matcher = pattern.matcher(empMobile);
            return matcher.matches();
    }
    public static boolean validateEmpAddress(String empAddress) {
        String addressRegex = "^[A-Z][a-z]*$";
            Pattern pattern = Pattern.compile(addressRegex);
            Matcher matcher = pattern.matcher(empAddress);
            return matcher.matches();
    }
    public static boolean validateEmpRole(String empRole) {
        String nameRegex = "^[A-Z][a-z]*$";
            Pattern pattern = Pattern.compile(nameRegex);
            Matcher matcher = pattern.matcher(empRole);
            return matcher.matches();
    }
}
