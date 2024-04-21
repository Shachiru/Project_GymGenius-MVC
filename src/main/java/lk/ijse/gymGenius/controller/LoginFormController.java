package lk.ijse.gymGenius.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.gymGenius.db.DbConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormController {

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
    private TextField txtUserId;

    @FXML
    private Hyperlink hyperSignUp;

    @FXML
    void btnLoginOnAction(ActionEvent event) {
        String userId = txtUserId.getText();
        String pw = txtPw.getText();
        try{
            checkCredential(userId,pw);
        } catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,"Try Again").show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkCredential(String userId, String pw) throws SQLException, IOException {
        String sql = "Select user_id,password from user where user_id=?";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1,userId);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()){
            String dbPw = resultSet.getString(2);

            if(dbPw.equals(pw)){
                navigateToGlobalForm();
            } else {
                new Alert(Alert.AlertType.ERROR,"Wrong Password!").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR,"User Id not found!").show();
        }
    }

    private void navigateToGlobalForm() throws IOException {
        FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

        Scene scene = new Scene(rootNode);
        Stage primaryStage = (Stage) this.rootNode.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setTitle("Dashboard Form");
    }



    @FXML
    void linkSignUpOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/signup_form.fxml"));
        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Sign Up Form");
        stage.show();
    }

}
