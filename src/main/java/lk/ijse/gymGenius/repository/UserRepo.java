package lk.ijse.gymGenius.repository;

import lk.ijse.gymGenius.db.DbConnection;
import lk.ijse.gymGenius.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepo {
    public String generateNextId() throws SQLException {
        String sql = "Select ID from user order by ID desc limit 1";

        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement(sql).executeQuery();
        String currentId = null;
        if (resultSet.next()) {
            currentId = resultSet.getString(1);
            return splitId(currentId);
        }
        return splitId(null);
    }

    private String splitId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("U");
            int id = Integer.parseInt(split[1]);
            id++;
            return "U" + id;
        }
        return "U1";
    }

    public boolean addUser(User user) throws SQLException {
        String sql = "Insert into user values(?,?,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, user.getUserId());
        pstm.setString(2, user.getUserName());
        pstm.setString(3, user.getPassword());

        return pstm.executeUpdate() > 0;
    }
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

