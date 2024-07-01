package com.icet.crm.controller.order;

import com.icet.crm.database.DBConnection;
import com.icet.crm.util.CrudUtil;
import com.icet.crm.dto.OrderDetails;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailController {

    private static OrderDetailController instance;
    private OrderDetailController(){}

    public boolean addOderDetail(List<OrderDetails> orderDetailsList){
        boolean isAdd=false;
        for (OrderDetails orderDetails: orderDetailsList){
            isAdd = addOrderDetail(orderDetails);
        }
        if (isAdd){
            return true;
        }
        return false;
    }

    public boolean addOrderDetail(OrderDetails orderDetails){
        try {
            System.out.println(orderDetails);

            Object isAdd = CrudUtil.execute(
                    "insert into orderdetail values(?,?,?)",
                    orderDetails.getOrderId(),
                    orderDetails.getItemCode(),
                    orderDetails.getQuantity()
            );
            return (boolean) isAdd;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public ObservableList<OrderDetails> loadOrderDetails(){
        ObservableList<OrderDetails> orderDetailsList= FXCollections.observableArrayList();
        try {
            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM orderDetail");
            while (resultSet.next()) {
                OrderDetails orderDetails = new OrderDetails(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getInt(3)
                );
                orderDetailsList.add(orderDetails);
            }
            return orderDetailsList;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    public static OrderDetailController getInstance(){
        if(instance==null){
            instance=new OrderDetailController();
        }
        return instance;
    }

}
