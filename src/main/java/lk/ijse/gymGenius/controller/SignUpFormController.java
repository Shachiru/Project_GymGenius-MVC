package lk.ijse.gymGenius.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpFormController {

    @FXML
    private JFXButton btnBack;

    @FXML
    private AnchorPane rootSignUp;

    @FXML
    private PasswordField txtConfirmPw;

    @FXML
    private PasswordField txtPw;

    @FXML
    private TextField txtUsername;

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));
        Stage stage = (Stage) btnBack.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Login Form");
        stage.centerOnScreen();
    }

    @FXML
    void btnSignUpOnAction(ActionEvent event) {

    }
}
