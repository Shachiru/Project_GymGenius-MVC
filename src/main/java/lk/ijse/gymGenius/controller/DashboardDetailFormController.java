package lk.ijse.gymGenius.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import lk.ijse.gymGenius.model.DashboardDetail;
import lk.ijse.gymGenius.repository.EmployeeRepo;
import lk.ijse.gymGenius.repository.MemberRepo;
import lk.ijse.gymGenius.repository.SupplementRepo;

import java.sql.SQLException;
import java.util.ArrayList;

public class DashboardDetailFormController {

    @FXML
    private Label lblEmployeeCount;

    @FXML
    private Label lblMemberCount;

    @FXML
    private Label lblSupplements;

    @FXML
    private Pane pagingPane;

    @FXML
    private PieChart pieChart;

    @FXML
    private BarChart<String, Integer> barChart;

    DashboardDetail dashboardDetail = new DashboardDetail();
    SupplementRepo supplementRepo = new SupplementRepo();

    public void initialize() throws SQLException {
        setPieChart();
        setDataToBarChart();
        countMembers();
        countEmployee();
        countSupplement();
    }

    public void setDataToBarChart() throws SQLException {
        ObservableList<XYChart.Series<String, Integer>> barChartData = SupplementRepo.getDataToBarChart();

        barChart.setData(barChartData);
    }

    void setPieChart() throws SQLException {

        try {
            ObservableList<PieChart.Data> obList = FXCollections.observableArrayList();
            ArrayList<PieChart.Data> data =dashboardDetail.getPieChartData();
            for (PieChart.Data datum : data) {
                obList.add(datum);
            }
            pieChart.getData().addAll(obList);
            pieChart.setTitle("Most Trending Products");
            pieChart.setStartAngle(180);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    void countMembers(){
        MemberRepo memberRepo = new MemberRepo();

        try{
            int count = memberRepo.countMember();
            lblMemberCount.setText(String.valueOf("0"+count));

        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }
    void countEmployee(){
        EmployeeRepo employeeRepo = new EmployeeRepo();

        try{
            int count = employeeRepo.countEmployee();
            lblEmployeeCount.setText(String.valueOf("0"+count));

        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

    private void countSupplement() {
        SupplementRepo supplementRepo = new SupplementRepo();

        try{
            int count = supplementRepo.countSupplement();
            lblSupplements.setText(String.valueOf("0"+count));

        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }
}
