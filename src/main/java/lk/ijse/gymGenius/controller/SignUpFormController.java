package lk.ijse.gymGenius.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gymGenius.model.User;
import lk.ijse.gymGenius.repository.UserRepo;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignUpFormController implements Initializable {

    @FXML
    private JFXButton btnSignUp;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private PasswordField txtPw;

    @FXML
    private TextField txtUserId;

    @FXML
    private TextField txtUsername;

    UserRepo userRepo = new UserRepo();

    @FXML
    void btnSignUpOnAction(ActionEvent event) {
        String userId = txtUserId.getText();
        String username = txtUsername.getText();
        String password = txtPw.getText();

        User user = new User(userId, username, password);
        try{
            boolean isAdded = userRepo.addUser(user);
            if(isAdded){
                new Alert(Alert.AlertType.CONFIRMATION, "User Added").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            txtUserId.setText(userRepo.generateNextId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
