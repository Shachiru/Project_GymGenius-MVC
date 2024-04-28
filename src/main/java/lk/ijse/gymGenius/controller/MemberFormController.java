package lk.ijse.gymGenius.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import lk.ijse.gymGenius.model.Member;
import lk.ijse.gymGenius.repository.MemberRepo;
import lk.ijse.gymGenius.tm.MemberTm;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MemberFormController  implements Initializable{

    @FXML
    private TableColumn<String,String> colMemberAddress;

    @FXML
    private TableColumn<String,String> colMemberDOB;

    @FXML
    private TableColumn<String,String> colMemberId;

    @FXML
    private TableColumn<String,String> colMemberMobile;

    @FXML
    private TableColumn<String,String> colMemberName;

    @FXML
    private TableColumn<String,String> colMemberGender;

    @FXML
    private Pane pagingPane;

    @FXML
    private TableView<MemberTm> tblMember;

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

    MemberRepo memberRepo = new MemberRepo();

    private List<Member> memberList = new ArrayList<>();

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtMemberId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String mobile = txtMobileNo.getText();
        String dob = txtDOB.getText();
        String gender = txtGender.getText();

        Member member = new Member(id, name, address, mobile, dob, gender);

        try {
            boolean isSaved = MemberRepo.saveMember(member);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.CONFIRMATION,"Member saved").show();
        }
    }
    private void setCellValueFactory() {
        colMemberId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colMemberName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colMemberAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colMemberMobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        colMemberDOB.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colMemberGender.setCellValueFactory(new PropertyValueFactory<>("gender"));

    }

    private void loadMemberTable(){

        MemberRepo memberRepo = new MemberRepo();
        ObservableList<MemberTm> tmList = FXCollections.observableArrayList();
        try{
            List<Member> memberList = memberRepo.getMember();
            for (Member member : memberList) {
                MemberTm memberTm = new MemberTm(
                        member.getId(),
                        member.getName(),
                        member.getAddress(),
                        member.getMobile(),
                        member.getDob(),
                        member.getGender()
                );
                tmList.add(memberTm);
            }
            tblMember.setItems(tmList);
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
        }
    }

    private List<Member> getAllMember() {
        List<Member> memberList = null;

        try {
            memberList = MemberRepo.getMember();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return memberList;
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            txtMemberId.setText(memberRepo.generateNextId());

        }catch (SQLException e){
            throw new RuntimeException(e)   ;
        }
        setCellValueFactory();
        loadMemberTable();
        this.memberList = getAllMember();
    }
}
