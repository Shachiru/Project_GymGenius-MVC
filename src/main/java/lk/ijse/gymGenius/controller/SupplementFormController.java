package lk.ijse.gymGenius.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import lk.ijse.gymGenius.model.Supplement;
import lk.ijse.gymGenius.repository.SupplementRepo;
import lk.ijse.gymGenius.tm.SupplementTm;
import lk.ijse.gymGenius.util.DataValidateController;

import javax.xml.crypto.Data;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SupplementFormController implements Initializable {

    @FXML
    private TableColumn<?, ?> colProductName;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colSupplementId;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Pane pagingPane;

    @FXML
    private TableView<SupplementTm> tblSupplement;

    @FXML
    private TextField txtProductName;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtSupplementId;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private Label lblSupName;

    @FXML
    private Label lblSupQTY;

    @FXML
    private Label lblUnitPrice;

    SupplementRepo supplementRepo = new SupplementRepo();

    private List<Supplement> supplementList = new ArrayList<>();

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtSupplementId.setText("");
        txtProductName.setText("");
        txtUnitPrice.setText("");
        txtQty.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtSupplementId.getText();
        SupplementRepo supplementRepo = new SupplementRepo();

        try {
            boolean isDeleted = supplementRepo.deleteSupplement(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION,"Supplement deleted").show();
                loadSupplementTable();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtSupplementId.getText();
        String productName = txtProductName.getText();
        String unitPrice = txtUnitPrice.getText();
        String qty = txtQty.getText();

        Supplement supplement = new Supplement(id,productName,unitPrice,qty);

        if (DataValidateController.validateSupplementQty(txtQty.getText())) {
            lblSupQTY.setText("");

            if (DataValidateController.validateSupplementPrice(txtUnitPrice.getText())) {
                lblUnitPrice.setText("");

                if (DataValidateController.validateSupplementName(txtProductName.getText())) {
                    lblSupName.setText("");

                    try {
                        boolean isSaved = SupplementRepo.saveSupplement(supplement);
                        if (isSaved) {
                            new Alert(Alert.AlertType.CONFIRMATION, "Supplement added").show();
                            loadSupplementTable();
                        }
                    } catch (SQLException e) {
                        new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                    }
                } else {
                    lblSupName.setText("Invalid Name");
                }
            } else {
                lblUnitPrice.setText("Invalid Price");
            }
        }else {
            lblSupQTY.setText("Invalid Quantity");
        }
    }

    private void loadSupplementTable() {
        SupplementRepo supplementRepo = new SupplementRepo();
        ObservableList<SupplementTm> tmList = FXCollections.observableArrayList();

        try{
            List<Supplement> supplementList = supplementRepo.getSupplement();
            for (Supplement supplement : supplementList) {
                SupplementTm supplementTm = new SupplementTm(
                        supplement.getId(),
                        supplement.getProductName(),
                        supplement.getUnitPrice(),
                        supplement.getQty()
                );
                tmList.add(supplementTm);
            }
            tblSupplement.setItems(tmList);
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
        }
    }

    private List<Supplement> getSupplement() {
        List<Supplement> supplementList = null;

        try {
            supplementList = SupplementRepo.getSupplement();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return supplementList;
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtSupplementId.getText();
        String productName = txtProductName.getText();
        String unitPrice = txtUnitPrice.getText();
        String qty = txtQty.getText();

        Supplement supplement = new Supplement(id,productName,unitPrice,qty);

        try {
            boolean isUpdated = supplementRepo.updateSupplement(supplement);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION,"Supplement updated").show();
                loadSupplementTable();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            txtSupplementId.setText(supplementRepo.generateNextId());
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        setCellValueFactory();
        loadSupplementTable();
        this.supplementList = getSupplement();
    }

    private void setCellValueFactory() {
        colSupplementId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
    }

    @FXML
    void supplementTableClick(MouseEvent event) {
        TablePosition pos = tblSupplement.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        ObservableList<TableColumn<SupplementTm,?> > columns = tblSupplement.getColumns();

        txtSupplementId.setText(columns.get(0).getCellData(row).toString());
        txtProductName.setText(columns.get(1).getCellData(row).toString());
        txtUnitPrice.setText(columns.get(2).getCellData(row).toString());
        txtQty.setText(columns.get(3).getCellData(row).toString());
    }
}