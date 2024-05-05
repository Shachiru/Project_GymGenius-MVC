package lk.ijse.gymGenius.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import lk.ijse.gymGenius.model.Member;
import lk.ijse.gymGenius.model.Supplement;
import lk.ijse.gymGenius.repository.MemberRepo;
import lk.ijse.gymGenius.repository.ShopRepo;
import lk.ijse.gymGenius.repository.SupplementRepo;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ShopFormController implements Initializable {

    @FXML
    private JFXButton btnAddToCart;

    @FXML
    private JFXButton btnPlaceOrder;

    @FXML
    private JFXComboBox<String> cmbMemberId;

    @FXML
    private JFXComboBox<String> cmbSupplementId;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colDesc;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colSupplementId;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Pane pagingPane;

    @FXML
    private TableView<?> tblShopCart;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtMemberName;

    @FXML
    private TextField txtOrderDate;

    @FXML
    private TextField txtOrderId;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtQtyOnHand;

    @FXML
    private TextField txtTotal;

    @FXML
    private TextField txtUnitPrice;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            txtOrderId.setText(ShopRepo.generateNextId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setCellValueFactory();
        getMemberId();
        getSupplementId();
        setDate();
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        txtOrderDate.setText(String.valueOf(now));
    }

    private void getSupplementId() {
        ObservableList<String> supplementList = FXCollections.observableArrayList();
        try {
            List<String> supplementIdList = ShopRepo.getSupId();
            for (String sId : supplementIdList) {
                supplementList.add(sId);
            }
            cmbSupplementId.setItems(supplementList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {

    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {

    }

    @FXML
    void cmbMemberIdOnAction(ActionEvent event) {
        String memberId = cmbMemberId.getValue();
        try {
            Member member = MemberRepo.searchMember(memberId);
            txtMemberName.setText(member.getName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbSupplementIdOnAction(ActionEvent event) {
        String supplementId = cmbSupplementId.getValue();
        try {
            Supplement supplement = SupplementRepo.searchSupplement(supplementId);
            if (supplement != null) {
                txtDescription.setText(supplement.getProductName());
                txtUnitPrice.setText(supplement.getUnitPrice());
                txtQtyOnHand.setText(supplement.getQty());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getMemberId() {
        ObservableList<String> memberList = FXCollections.observableArrayList();

        try {
            List<String> memberIdList = ShopRepo.getMemId();

            for (String memberId : memberIdList) {
                memberList.add(memberId);
            }
            cmbMemberId.setItems(memberList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colSupplementId.setCellValueFactory(new PropertyValueFactory<>("supplement_id"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unit_price"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("deleteBtn"));
    }

}
