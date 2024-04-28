package lk.ijse.gymGenius.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gymGenius.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignUpFormController {

    @FXML
    private JFXButton btnSignUp;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private PasswordField txtConfirmPw;

    @FXML
    private PasswordField txtPw;

    @FXML
    private TextField txtUsername;

    @FXML
    void btnSignUpOnAction(ActionEvent event) {
        String username = txtUsername.getText();
        String password = txtPw.getText();
        String confirmPassword = txtConfirmPw.getText();

        saveUser(username, password, confirmPassword);
    }

    private void saveUser(String username, String password, String confirmPassword) {
        try{
            String sql = "INSERT INTO user VALUES (?,?)";
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            if (preparedStatement.executeUpdate() > 0){
                new Alert(Alert.AlertType.CONFIRMATION, "User Added").show();
            }
        } catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,"something went wrong").show();
        }
    }
}
