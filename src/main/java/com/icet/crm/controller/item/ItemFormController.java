package com.icet.crm.controller.item;

import com.icet.crm.bo.BoFactory;
import com.icet.crm.bo.custom.ItemBo;
import com.icet.crm.controller.supplier.SupplierController;
import com.icet.crm.dto.Item;
import com.icet.crm.dto.Supplier;
import com.icet.crm.dto.tablemodel.Table05TM;
import com.icet.crm.util.BoType;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ItemFormController implements Initializable {
    public JFXTextField txtItemCode;
    public JFXTextField txtName;
    public JFXTextField txtSize;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQauntityOnHand;
    public JFXComboBox cmbCategory;
    public JFXComboBox cmbSupplierId;
    public TableView tblItem;
    public TableColumn colItemCode;
    public TableColumn colName;
    public TableColumn colCategory;
    public TableColumn colSize;
    public TableColumn colUnitPrize;
    public TableColumn colQtyOnHand;
    public TableColumn colSupplierId;
    public AnchorPane addItemForm;

    private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colUnitPrize.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        ItemController.getInstance().loadItems();
        loadTable05();
        loadCategory();
        loadSuppliers();
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        Item item=new Item();
        item.setItemCode(txtItemCode.getText());
        item.setName(txtName.getText());
        item.setSize(Integer.valueOf(txtSize.getText()));
        item.setUnitPrice(txtUnitPrice.getText());
        item.setQuantity(Integer.valueOf(txtQauntityOnHand.getText()));
        item.setCategory((String) cmbCategory.getValue());
        item.setSupplierId((String) cmbSupplierId.getValue());
        itemBo.saveItem(item);

        clearText();
        ItemController.getInstance().loadItems();
        loadTable05();
    }


    public void btnDeleteOnAction(ActionEvent actionEvent) {
        boolean isDelete = itemBo.deleteItem(txtItemCode.getText());
        if (isDelete) {
            new Alert(Alert.AlertType.CONFIRMATION, "Delete Successfully !");
        }
        clearText();
        ItemController.getInstance().loadItems();
        loadTable05();
    }
    public void clearText(){
        txtItemCode.setText(null);
        txtName.setText(null);
        txtSize.setText(null);
        txtUnitPrice.setText(null);
        txtQauntityOnHand.setText(null);
        cmbCategory.setValue(null);
        cmbSupplierId.setValue(null);
    }

    private void loadTable05(){
        ObservableList<Table05TM> table05Data = FXCollections.observableArrayList();
        ObservableList<Item> allItems= ItemController.getInstance().loadItems();

        allItems.forEach(item ->{
            Table05TM table01 = new Table05TM(
                    item.getItemCode(),
                    item.getName(),
                    item.getCategory(),
                    item.getSize(),
                    item.getUnitPrice(),
                    item.getQuantity(),
                    item.getSupplierId()
            );
            table05Data.add(table01);
        });
        tblItem.setItems(table05Data);
    }

    private void loadCategory(){
        ObservableList<String> categoryList = FXCollections.observableArrayList();
        categoryList.add("Male");
        categoryList.add("Female");
        categoryList.add("Kids");
        cmbCategory.setItems(categoryList);
    }

    public void loadSuppliers(){
        ObservableList<Supplier> allSuppliers = SupplierController.getInstance().loadSuppliers();
        List<String> idList = FXCollections.observableArrayList();
        allSuppliers.forEach(supplier -> {
            idList.add(supplier.getSupplierId());
        });
        cmbSupplierId.setItems((ObservableList) idList);

    }

    public void imgBackToDashBoardOnClick(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/dashboard-form.fxml"));
            Scene scene = new Scene(root);
            Stage stage=new Stage();
            stage.setScene(scene);
            stage.show();
            closeAddItemForm();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void closeAddItemForm() {
        Stage stage = (Stage) addItemForm.getScene().getWindow();
        stage.close();
    }
}
