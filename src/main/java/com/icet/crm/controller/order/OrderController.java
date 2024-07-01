package com.icet.crm.controller.order;

import com.icet.crm.controller.item.ItemController;
import com.icet.crm.database.DBConnection;
import com.icet.crm.dto.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderController {

    private static  OrderController instance;

    private OrderController(){}

    public boolean placeOrder(Order order) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {

            connection.setAutoCommit(false);
            String sql = "insert into orders values(?, ?, ?)";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1,order.getOrderId());
            stm.setObject(2,order.getOrderDate());
            stm.setObject(3,order.getCustomerId());
            boolean isAddOrder = stm.execute();
            if(!isAddOrder){
                boolean isAddOrderDetail = OrderDetailController.getInstance().addOderDetail(order.getOrderDetailsList());
                if(isAddOrderDetail){
                    boolean isUpdateStock = ItemController.getInstance().updateStock(order.getOrderDetailsList());
                    if (isUpdateStock){
                        connection.setAutoCommit(true);
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            connection.setAutoCommit(true);
        }

    }

    public static OrderController getInstance(){
        if(instance==null){
            instance = new OrderController();
        }
        return instance;
    }
}
