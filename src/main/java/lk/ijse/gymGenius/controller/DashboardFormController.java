package lk.ijse.gymGenius.controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import lk.ijse.gymGenius.util.Navigation;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class DashboardFormController implements Initializable {

    @FXML
    private JFXButton btnHome;

    @FXML
    private JFXButton btnLogout;

    @FXML
    private JFXButton btnMembers;

    @FXML
    private JFXButton btnShop;

    @FXML
    private JFXButton btnEmp;

    @FXML
    private JFXButton btnSupplement;

    @FXML
    private Pane pagingPane;

    @FXML
    private AnchorPane rootNode;


    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    void btnHomeOnAction(ActionEvent event) throws IOException {
        Navigation.switchPaging(pagingPane,"dashboardDetail_form.fxml ");
    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) throws IOException {
        btnLogout.getScene().getWindow().hide();
        Navigation.changeStage("/view/login_form.fxml","Login Form");
    }

    @FXML
    void btnMembersOnAction(ActionEvent event) throws IOException {
        Navigation.switchPaging(pagingPane,"member_form.fxml");
    }

    @FXML
    void btnShopOnAction(ActionEvent event) throws IOException {
        Navigation.switchPaging(pagingPane,"order_form.fxml");
    }

    @FXML
    void btnEmpOnAction(ActionEvent event) throws IOException {
        Navigation.switchPaging(pagingPane,"employee_form.fxml");
    }

    @FXML
    void btnSupplementOnAction(ActionEvent event) throws IOException {
        Navigation.switchPaging(pagingPane,"supplement_form.fxml");
    }
    private void timeNow() {
        Thread thread = new Thread(() -> {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
            SimpleDateFormat sdf1 = new SimpleDateFormat("MMMM, dd, yyyy");
            while (true) {
                try {
                    Thread.sleep(1000);

                } catch (Exception e) {
                    System.out.println(e);
                }
                final String timeNow = sdf.format(new Date());
                String timeNow1 = sdf1.format(new Date());

                Platform.runLater(() -> {
                    lblTime.setText(timeNow);
                    lblDate.setText(timeNow1);
                });
            }
        });
        thread.start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeNow();
        pagingPane.setVisible(true);
        try {
            Navigation.switchPaging(pagingPane,"dashboardDetail_form.fxml ");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
