package lk.ijse.gymGenius.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import lk.ijse.gymGenius.util.Navigation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardFormController implements Initializable {

    @FXML
    private JFXButton btnHome;

    @FXML
    private JFXButton btnLogout;

    @FXML
    private JFXButton btnMembers;

    @FXML
    private JFXButton btnSalary;

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
    void btnHomeOnAction(ActionEvent event) throws IOException {
        Navigation.switchPaging(pagingPane,"dashboarddetail_form.fxml ");
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
    void btnSalaryOnAction(ActionEvent event) throws IOException {
        Navigation.switchPaging(pagingPane,"salary_form.fxml");
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pagingPane.setVisible(true);
        try {
            Navigation.switchPaging(pagingPane,"dashboarddetail_form.fxml ");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
