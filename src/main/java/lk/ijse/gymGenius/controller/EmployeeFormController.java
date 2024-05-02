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
import lk.ijse.gymGenius.model.Employee;
import lk.ijse.gymGenius.model.User;
import lk.ijse.gymGenius.repository.EmployeeRepo;
import lk.ijse.gymGenius.tm.EmployeeTm;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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

    EmployeeRepo employeeRepo = new EmployeeRepo();

    private List<Employee> employeeList = new ArrayList<>();

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

        try {
            boolean isSaved = EmployeeRepo.saveEmployee(employee);
        }catch (SQLException e) {
            new Alert(Alert.AlertType.CONFIRMATION, "Employee saved").show();
            loadEmployeeTable();
        }
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

    private List<Employee> getAllEmployee() {
        List<Employee> employeeList = null;

        try {
            employeeList = EmployeeRepo.getEmployee();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employeeList;
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            txtEmpId.setText(employeeRepo.generateNextId());
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setCellValueFactory();
        loadEmployeeTable();
        this.employeeList = getAllEmployee();
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

}





