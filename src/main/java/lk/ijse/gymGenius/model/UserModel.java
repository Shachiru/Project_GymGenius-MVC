package lk.ijse.gymGenius.model;

import lk.ijse.gymGenius.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {
    public static boolean verifyCredentials(String Username, String Password) {
        try {
            DbConnection instance = DbConnection.getInstance();
            Connection connection = instance.getConnection();

            String sql = "select Password from user where Username=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, Username);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                if (Password.equals(resultSet.getString(1))){
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
