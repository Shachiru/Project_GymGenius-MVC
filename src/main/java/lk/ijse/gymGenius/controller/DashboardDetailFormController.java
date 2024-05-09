package lk.ijse.gymGenius.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import lk.ijse.gymGenius.model.DashboardDetail;
import lk.ijse.gymGenius.repository.EmployeeRepo;
import lk.ijse.gymGenius.repository.MemberRepo;

import java.sql.SQLException;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DashboardDetailFormController {

    @FXML
    private Label lblDate;

    @FXML
    private Label lblEmployeeCount;

    @FXML
    private Label lblMemberCount;

    @FXML
    private Label lblSupplements;

    @FXML
    private Label lblTime;

    @FXML
    private Pane pagingPane;

    @FXML
    private PieChart pieChart;

    @FXML
    private BarChart<?, ?> barChart;

    DashboardDetail dashboardDetail = new DashboardDetail();

    public void initialize() {
        //setPieChart();
        timenow();
        countMembers();
        countEmployee();
    }

    void setPieChart() throws SQLException {

        /*try{
            ObservableList<PieChart.Data> obList = FXCollections.observableArrayList();
            ArrayList<PieChart.Data> data = DashboardDetail.getPieChartData();
            for (PieChart.Data d : data) {
                obList.add(d);
            }
            pieChart.getId().addAll(obList);
            pieChart.setTitle("Most sell products");
            pieChart.setStartAngle(180);
        } catch (SQLException e) {
            throwable.printStackTrace();
        }*/
    }

    private void timenow() {
        Thread thread = new Thread(() -> {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
            SimpleDateFormat sdf1 = new SimpleDateFormat("MMMM,  dd, yyyy");
            while (true) {
                try {
                    Thread.sleep(1000);

                } catch (Exception e) {
                    System.out.println(e);
                }
                final String timenow = sdf.format(new Date());
                String timenow1 = sdf1.format(new Date());

                Platform.runLater(() -> {
                    lblTime.setText(timenow);
                    lblDate.setText(timenow1);
                });
            }
        });
        thread.start();
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


}
