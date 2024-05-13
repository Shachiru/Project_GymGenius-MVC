package lk.ijse.gymGenius.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.gymGenius.repository.UserRepo;
import lk.ijse.gymGenius.util.DataValidateController;
import lk.ijse.gymGenius.util.Navigation;

import java.io.IOException;

public class LoginFormController {

    @FXML
    private JFXButton btnLogin;

    @FXML
    private Hyperlink hyperSignUp;

    @FXML
    private Label lblPassword;

    @FXML
    private Label lblUsername;

    @FXML
    private Label lblWelcome;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private PasswordField txtPw;

    @FXML
    private TextField txtSlogan;

    @FXML
    private TextField txtUserName;

    @FXML
    private Label lblUsernameValidate;

    @FXML
    private Label lblPasswordValidate;

    /*@FXML
    private ImageView imgPower;

    @FXML
    void btnPower(MouseEvent event) {
        System.exit(1);
    }*/

    @FXML
    void btnLoginOnAction(ActionEvent event) {
        if (UserRepo.verifyCredentials(txtUserName.getText(), txtPw.getText())){

          /*if (DataValidateController.validateName(txtUserName.getText())) {
                lblUsernameValidate.setText("");*/

            try {
                Navigation.switchNavigation("dashboard_form.fxml",event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            /*} else{
                lblUsernameValidate.setText("Invalid Username");
            }*/
        }
    }

    @FXML
    void linkSignUpOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/signup_form.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.setTitle("SignUp Form");

        stage.show();
    }
}
