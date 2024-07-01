package com.icet.crm.controller.customer;

import com.icet.crm.database.DBConnection;
import com.icet.crm.dto.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class CustomerController {

    private static  CustomerController instance;
    private CustomerController(){}

    public static CustomerController getInstance(){
        if(instance==null){
            instance=new CustomerController();
        }
        return instance;

    }
    public ObservableList<Customer> loadCustomers() {
        ObservableList<Customer> customerList= FXCollections.observableArrayList();
        try {
            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM customer");
            while (resultSet.next()) {
                Customer customer = new Customer(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4)
                );
                customerList.add(customer);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return customerList;
    }

    public Customer getCustomerById(String customerId) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement stm = connection.prepareStatement("select * from customer where customer_id = ?");
            stm.setString(1,customerId);
            ResultSet rst  = stm.executeQuery();
            while(rst.next()){
                Customer customer = new Customer(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getInt(4)
                );
                return customer;
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
