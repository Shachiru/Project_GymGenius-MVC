package lk.ijse.gymGenius.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import lk.ijse.gymGenius.model.Member;
import lk.ijse.gymGenius.model.Order;
import lk.ijse.gymGenius.model.Supplement;
import lk.ijse.gymGenius.repository.MemberRepo;
import lk.ijse.gymGenius.repository.ShopRepo;
import lk.ijse.gymGenius.repository.SupplementRepo;
import lk.ijse.gymGenius.tm.ShopTm;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
    private TableView<ShopTm> tblShopCart;

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

    private ObservableList<ShopTm> cartList = FXCollections.observableArrayList();
    private double netTotal = 0;

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

    private void setCellValueFactory() {
        colSupplementId.setCellValueFactory(new PropertyValueFactory<>("supplement_id"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
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
    void btnAddToCartOnAction(ActionEvent event) {
        String supplementId = cmbSupplementId.getValue();
        String desc = txtDescription.getText();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int qty = Integer.parseInt(txtQty.getText());
        double total = qty * unitPrice;
        JFXButton btnRemove = new JFXButton("Remove");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction((e) ->{
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                int selectedIndex = tblShopCart.getSelectionModel().getSelectedIndex();
                cartList.remove(selectedIndex);

                tblShopCart.refresh();
                calculateNetTotal();
            }
        });

        for (int i=0; i<tblShopCart.getItems().size(); i++){
            if (supplementId.equals(colSupplementId.getCellData(i))){
                qty += cartList.get(i).getQty();
                total = unitPrice * qty;

                cartList.get(i).setQty(qty);
                cartList.get(i).setTotal(total);

                tblShopCart.refresh();
                calculateNetTotal();
                txtQty.setText("");
                return;
            }
        }

        ShopTm shopTm = new ShopTm(supplementId, desc, unitPrice, qty, total, btnRemove);
        cartList.add(shopTm);

        tblShopCart.setItems(cartList);
        txtQty.setText("");
        calculateNetTotal();
    }

    private void calculateNetTotal() {
        netTotal = 0;
        for (int i = 0; i < tblShopCart.getItems().size(); i++) {
            netTotal += (double) colTotal.getCellData(i);
        }
        txtTotal.setText(String.valueOf(netTotal));
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
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
}
