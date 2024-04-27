package lk.ijse.gymGenius.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import lk.ijse.gymGenius.repository.MemberRepo;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MemberFormController implements Initializable {

    @FXML
    private TableColumn<?, ?> colMemberAddress;

    @FXML
    private TableColumn<?, ?> colMemberDOB;

    @FXML
    private TableColumn<?, ?> colMemberId;

    @FXML
    private TableColumn<?, ?> colMemberMobile;

    @FXML
    private TableColumn<?, ?> colMemberName;

    @FXML
    private Pane pagingPane;

    @FXML
    private TableView<?> tblMember;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtDOB;

    @FXML
    private TextField txtGender;

    @FXML
    private TextField txtMemberId;

    @FXML
    private TextField txtMobileNo;

    @FXML
    private TextField txtName;

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpadateOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            txtMemberId.setText(MemberRepo.generateNextId());
        }catch (SQLException e){
            throw new RuntimeException(e)   ;
        }
    }
}
