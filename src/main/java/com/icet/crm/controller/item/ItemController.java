package com.icet.crm.controller.item;

import com.icet.crm.util.CrudUtil;
import com.icet.crm.database.DBConnection;
import com.icet.crm.dto.Item;
import com.icet.crm.dto.OrderDetails;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ItemController {

    private static ItemController instance;
    private ItemController(){}

    public static ItemController getInstance(){
        if(instance==null){
            instance=new ItemController();
        }
        return instance;

    }
    public ObservableList<Item> loadItems() {
        ObservableList<Item> itemList= FXCollections.observableArrayList();
        try {
            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM item");
            while (resultSet.next()) {
                Item item = new Item(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4),
                        resultSet.getString(5),
                        resultSet.getInt(6),
                        resultSet.getString(7)
                );
                itemList.add(item);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return itemList;
    }

    public Item getItemById(String itemCode){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement stm = connection.prepareStatement("select * from item where item_code = ?");
            stm.setString(1,itemCode);
            ResultSet rst  = stm.executeQuery();
            while(rst.next()){
                Item item = new Item(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getInt(4),
                        rst.getString(5),
                        rst.getInt(6),
                        rst.getString(7)
                );
                return item;
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public boolean updateStock(List<OrderDetails> orderDetailsList) {
        boolean isUpdate = false;
        for(OrderDetails orderDetails: orderDetailsList){
            isUpdate = updateStock(orderDetails);
        }
        return isUpdate;

    }

    public boolean updateStock(OrderDetails orderDetails) {
        try {
            Object isUpdate = CrudUtil.execute("update item set quantity = quantity-? where item_code=?",
                    orderDetails.getQuantity(),
                    orderDetails.getItemCode()
            );
            return (boolean) isUpdate;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

