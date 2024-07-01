package com.icet.crm.controller.supplier;

import com.icet.crm.database.DBConnection;
import com.icet.crm.dto.Supplier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierController {

    private static SupplierController instance;
    private SupplierController(){}

    public ObservableList<Supplier> loadSuppliers(){

        try {
            ObservableList<Supplier> supplierList= FXCollections.observableArrayList();
            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM supplier");
            while (resultSet.next()) {
                Supplier supplier = new Supplier(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4),
                        resultSet.getString(5)
                );
                supplierList.add(supplier);
            }
            return supplierList;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    public static SupplierController getInstance(){
        return instance!=null ? instance : (instance=new SupplierController());
    }
}
