package lk.ijse.gymGenius.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import lk.ijse.gymGenius.model.Employee;
import lk.ijse.gymGenius.model.User;
import lk.ijse.gymGenius.repository.EmployeeRepo;
import lk.ijse.gymGenius.tm.EmployeeTm;
import lk.ijse.gymGenius.util.DataValidateController;
import lk.ijse.gymGenius.util.ValidateUtil;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class EmployeeFormController implements Initializable {

    @FXML
    private TableColumn<?, ?> colEmpAddress;

    @FXML
    private TableColumn<?, ?> colEmpId;

    @FXML
    private TableColumn<?, ?> colEmpMobile;

    @FXML
    private TableColumn<?, ?> colEmpName;

    @FXML
    private TableColumn<?, ?> colEmpRole;

    @FXML
    private Pane pagingPane;

    @FXML
    private TableView<EmployeeTm> tblEmployee;

    @FXML
    private TextField txtEmpAddress;

    @FXML
    private TextField txtEmpId;

    @FXML
    private TextField txtEmpMobile;

    @FXML
    private TextField txtEmpName;

    @FXML
    private TextField txtEmpRole;

    @FXML
    private Label lblAddress;

    @FXML
    private Label lblMobile;

    @FXML
    private Label lblName;

    @FXML
    private Label lblRole;

    EmployeeRepo employeeRepo = new EmployeeRepo();

    private List<Employee> employeeList = new ArrayList<>();

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            txtEmpId.setText(employeeRepo.generateNextId());
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Pattern name = Pattern.compile("^[A-z|\\s]{3,}$");
        Pattern number = Pattern.compile("^([+]94{1,3}|[0])([1-9]{2})([0-9]){7}$");

        map.put(txtEmpName,name);
        map.put(txtEmpMobile,number);

        setCellValueFactory();
        loadEmployeeTable();
        this.employeeList = getAllEmployee();
    }

    private List<Employee> getAllEmployee() {
        List<Employee> employeeList = null;

        try {
            employeeList = EmployeeRepo.getEmployee();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employeeList;
    }

    private void loadEmployeeTable() {
        EmployeeRepo employeeRepo = new EmployeeRepo();
        ObservableList<EmployeeTm> tmList = FXCollections.observableArrayList();
        try{
            List<Employee> employeeList = employeeRepo.getEmployee();
            for (Employee employee : employeeList) {
                EmployeeTm employeeTm = new EmployeeTm(
                        employee.getId(),
                        employee.getName(),
                        employee.getAddress(),
                        employee.getMobile(),
                        employee.getEmpRole(),
                        employee.getUserId()
                );
                tmList.add(employeeTm);
            }
            tblEmployee.setItems(tmList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {

        txtEmpId.setText("");
        txtEmpName.setText("");
        txtEmpAddress.setText("");
        txtEmpMobile.setText("");
        txtEmpRole.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtEmpId.getText();
        EmployeeRepo employeeRepo = new EmployeeRepo();
        try{
            boolean isDeleted = employeeRepo.deleteEmployee(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee deleted").show();
                loadEmployeeTable();
            }
        }catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

        String id = txtEmpId.getText();
        String name = txtEmpName.getText();
        String address = txtEmpAddress.getText();
        String mobile = txtEmpMobile.getText();
        String role = txtEmpRole.getText();
        String userId = "U001";

        Employee employee = new Employee(id, name, address, mobile, role, userId);

        if (DataValidateController.validateEmpRole(txtEmpRole.getText())) {
            lblRole.setText("");

            if (DataValidateController.validateEmpAddress(txtEmpAddress.getText())) {
                lblAddress.setText("");

                if (DataValidateController.validateEmpMobile(txtEmpMobile.getText())) {
                    lblMobile.setText("");

                    if (DataValidateController.validateEmpName(txtEmpName.getText())) {
                        lblName.setText("");

                        try {
                            boolean isSaved = EmployeeRepo.saveEmployee(employee);
                            if (isSaved) {
                                new Alert(Alert.AlertType.CONFIRMATION, "Employee saved").show();
                                loadEmployeeTable();
                            }
                        } catch (SQLException e) {
                            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                        }
                    } else {
                        lblName.setText("Invalid Name");
                    }
                } else {
                    lblMobile.setText("Invalid Mobile");
                }
            } else {
                lblAddress.setText("Invalid Address");
            }
        } else {
            lblRole.setText("Invalid Role");
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtEmpId.getText();
        String name = txtEmpName.getText();
        String address = txtEmpAddress.getText();
        String mobile = txtEmpMobile.getText();
        String role = txtEmpRole.getText();
        String userId = "U001";

        Employee employee = new Employee(id, name, address, mobile, role, userId);

        try {
            boolean isUpdated = EmployeeRepo.updateEmployee(employee);
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Employee updated").show();
                loadEmployeeTable();
            }
        }catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    private void setCellValueFactory() {

        colEmpId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEmpName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmpAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmpMobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        colEmpRole.setCellValueFactory(new PropertyValueFactory<>("empRole"));

    }

    @FXML
    void empTableClick(MouseEvent mouseEvent) {
        TablePosition pos = tblEmployee.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        ObservableList<TableColumn<EmployeeTm,?>> columns = tblEmployee.getColumns();

        txtEmpId.setText(columns.get(0).getCellData(row).toString());
        txtEmpName.setText(columns.get(1).getCellData(row).toString());
        txtEmpAddress.setText(columns.get(2).getCellData(row).toString());
        txtEmpMobile.setText(columns.get(3).getCellData(row).toString());
        txtEmpRole.setText(columns.get(4).getCellData(row).toString());

    }
    @FXML
    void txtKeyOnReleased(KeyEvent event) {
        ValidateUtil.validation(map);
    }


}





