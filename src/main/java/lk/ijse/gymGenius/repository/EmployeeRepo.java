package lk.ijse.gymGenius.repository;

import lk.ijse.gymGenius.db.DbConnection;
import lk.ijse.gymGenius.model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepo {
    public static boolean saveEmployee(Employee employee) throws SQLException {
        String sql = "INSERT INTO employee VALUES(?,?,?,?,?,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, employee.getId());
        pstm.setObject(2, employee.getName());
        pstm.setObject(3, employee.getAddress());
        pstm.setObject(4, employee.getMobile());
        pstm.setObject(5, employee.getEmpRole());
        pstm.setObject(6, employee.getUserId());

        return pstm.executeUpdate() > 0;
    }

    public static List<Employee> getEmployee() throws SQLException {
        String sql = "SELECT * FROM employee";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Employee> employeeList = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String mobile = resultSet.getString(4);
            String empRole = resultSet.getString(5);
            String userId = resultSet.getString(6);

            Employee employee = new Employee(id, name, address, mobile, empRole, userId);
            employeeList.add(employee);
        }
        return employeeList;
    }

    public static String generateNextId() throws SQLException {
        String sql = "Select ID from employee order by ID desc limit 1";
        Connection connection = DbConnection.getInstance().getConnection();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String id = null;
        if (resultSet.next()) {
            id = resultSet.getString(1);
            return splitId(id);
        }
        return splitId(null);
    }

    private static String splitId(String id) {
        if (id != null) {
            String[] split = id.split("Emp ");
            int empId = Integer.parseInt(split[1]);
            empId++;
            return "Emp " + empId;
        }
        return "Emp 1";
    }

    public static boolean updateEmployee(Employee employee) throws SQLException {
        String sql = "UPDATE employee SET EmpName = ?, Address = ?, Mobile = ?, EmpRole = ?, UserID = ? WHERE ID = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, employee.getName());
        pstm.setObject(2, employee.getAddress());
        pstm.setObject(3, employee.getMobile());
        pstm.setObject(4, employee.getEmpRole());
        pstm.setObject(5, employee.getUserId());
        pstm.setObject(6, employee.getId());

        return pstm.executeUpdate() > 0;
    }

    public boolean deleteEmployee(String id) throws SQLException {
        String sql = "DELETE from employee where ID = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }
}