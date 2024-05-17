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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import lk.ijse.gymGenius.db.DbConnection;
import lk.ijse.gymGenius.model.*;
import lk.ijse.gymGenius.repository.MemberRepo;
import lk.ijse.gymGenius.repository.OrderRepo;
import lk.ijse.gymGenius.repository.PlaceOrderRepo;
import lk.ijse.gymGenius.repository.SupplementRepo;
import lk.ijse.gymGenius.tm.OrderTm;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class OrderPlaceFormController implements Initializable {

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
    private TableView<OrderTm> tblShopCart;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblMemberName;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblOrderId;

    @FXML
    private TextField txtQty;

    @FXML
    private Label lblQtyOnHand;

    @FXML
    private TextField txtTotal;

    @FXML
    private Label lblUnitPrice;

    private ObservableList<OrderTm> cartList = FXCollections.observableArrayList();
    private double netTotal = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            lblOrderId.setText(OrderRepo.generateNextId());
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
        lblOrderDate.setText(String.valueOf(now));
    }

    private void getSupplementId() {
        ObservableList<String> supplementList = FXCollections.observableArrayList();
        try {
            List<String> supplementIdList = OrderRepo.getSupId();
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
            lblMemberName.setText(member.getName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String supplementId = cmbSupplementId.getValue();
        String desc = lblDescription.getText();
        double unitPrice = Double.parseDouble(lblUnitPrice.getText());
        int qty = Integer.parseInt(txtQty.getText());
        double total = qty * unitPrice;
        JFXButton btnRemove = new JFXButton("Remove");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction((e) -> {
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

        for (int i = 0; i < tblShopCart.getItems().size(); i++) {
            if (supplementId.equals(colSupplementId.getCellData(i))) {
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

        OrderTm orderTm = new OrderTm(supplementId, desc, unitPrice, qty, total, btnRemove);
        cartList.add(orderTm);

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
        String orderId = lblOrderId.getText();
        Date date = Date.valueOf(lblOrderDate.getText());
        String memberId = cmbMemberId.getValue();

        double orderAmount = 0;
        Order order = new Order(orderId, date, memberId);
        List<OrderDetail> orderList = new ArrayList<>();

        for (int i = 0; i < tblShopCart.getItems().size(); i++) {
            OrderTm orderTm = cartList.get(i);

            OrderDetail orderDetail = new OrderDetail(
                    orderId,
                    orderTm.getSupplement_id(),
                    orderTm.getQty(),
                    orderTm.getUnitPrice(),
                    orderTm.getQty() * orderTm.getUnitPrice()
            );
            orderList.add(orderDetail);
        }
        PlaceOrder placeOrder = new PlaceOrder(order, orderList);

        try {
            boolean isOrderPlaced = PlaceOrderRepo.orderPlaced(placeOrder);
            if (isOrderPlaced) {
                ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);
                Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Order Successfully.. Do you want print a bill ?", yes, no).showAndWait();

                if (result.orElse(no) == yes) {
                    JasperDesign jasperDesign =
                            JRXmlLoader.load("src/main/resources/view/reports/GymBill.jrxml");

                    JRDesignQuery jrDesignQuery = new JRDesignQuery();
                    jrDesignQuery.setText("SELECT * FROM orders o INNER JOIN order_detail od ON o.o_id = od.order_id WHERE o.o_id = (SELECT MAX(o.o_id) FROM orders) ORDER BY od.order_id DESC LIMIT 1");

                    jasperDesign.setQuery(jrDesignQuery);

                    JasperReport jasperReport =
                            JasperCompileManager.compileReport(jasperDesign);

                    JasperPrint jasperPrint =
                            JasperFillManager.fillReport(
                                    jasperReport,
                                    null,
                                    DbConnection.getInstance().getConnection());

                    JasperViewer.viewReport(jasperPrint,false);
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Something went Wrong").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbSupplementIdOnAction(ActionEvent event) {
        String supplementId = cmbSupplementId.getValue();
        try {
            Supplement supplement = SupplementRepo.searchSupplement(supplementId);
            if (supplement != null) {
                lblDescription.setText(supplement.getProductName());
                lblUnitPrice.setText(supplement.getUnitPrice());
                lblQtyOnHand.setText(supplement.getQty());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getMemberId() {
        ObservableList<String> memberList = FXCollections.observableArrayList();

        try {
            List<String> memberIdList = OrderRepo.getMemId();

            for (String memberId : memberIdList) {
                memberList.add(memberId);
            }
            cmbMemberId.setItems(memberList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
