package com.icet.crm.controller.supplier;

import com.icet.crm.bo.BoFactory;
import com.icet.crm.bo.custom.SupplierBo;
import com.icet.crm.dto.Supplier;
import com.icet.crm.dto.tablemodel.Table02TM;
import com.icet.crm.util.BoType;
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
import java.util.ResourceBundle;

public class SupplierFormController implements Initializable {
    public TableColumn colSupplierId;
    public TableColumn colName;
    public TableColumn colCompany;
    public TableColumn colContact;
    public TableColumn colEmail;
    public JFXTextField txtSupplierId;
    public JFXTextField txtName;
    public JFXTextField txtCompany;
    public JFXTextField txtContact;
    public JFXTextField txtEmail;
    public TableView tblSupplier;
    public AnchorPane addSupplierForm;


    private SupplierBo supplierBo = BoFactory.getInstance().getBo(BoType.SUPPLIER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCompany.setCellValueFactory(new PropertyValueFactory<>("company"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        SupplierController.getInstance().loadSuppliers();
        loadTable02();
    }
    
    public void btnAddOnAction(ActionEvent actionEvent) {
        Supplier supplier = new Supplier();
        supplier.setSupplierId(txtSupplierId.getText());
        supplier.setName(txtName.getText());
        supplier.setCompany(txtCompany.getText());
        supplier.setContact(Integer.valueOf(txtContact.getText()));
        supplier.setEmail(txtEmail.getText());

        supplierBo.saveSupplier(supplier);
        clearText();
        SupplierController.getInstance().loadSuppliers();
        loadTable02();

    }
    public void btnUpdateOnAction(ActionEvent actionEvent) {

        Supplier supplier = new Supplier();
        supplier.setSupplierId(txtSupplierId.getText());
        supplier.setName(txtName.getText());
        supplier.setCompany(txtCompany.getText());
        supplier.setContact(Integer.valueOf(txtContact.getText()));
        supplier.setEmail(txtEmail.getText());

        supplierBo.updateSupplier(supplier);
        clearText();
        SupplierController.getInstance().loadSuppliers();
        loadTable02();
        updateNotification();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {

        supplierBo.deleteSupplier(txtSupplierId.getText());
        SupplierController.getInstance().loadSuppliers();
        loadTable02();
        clearText();
        deleteNotification();
    }

    private void loadTable02(){
        ObservableList<Table02TM> table02Data = FXCollections.observableArrayList();
        ObservableList<Supplier> supplierList = SupplierController.getInstance().loadSuppliers();

        supplierList.forEach(supplier ->{
            Table02TM table02 = new Table02TM(
                    supplier.getSupplierId(),
                    supplier.getName(),
                    supplier.getCompany(),
                    supplier.getContact(),
                    supplier.getEmail()
            );
            table02Data.add(table02);
        });
        tblSupplier.setItems(table02Data);

    }
    private void clearText(){
        txtSupplierId.setText(null);
        txtName.setText(null);
        txtCompany.setText(null);
        txtContact.setText(null);
        txtEmail.setText(null);
    }
    private void updateNotification(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notification");
        alert.setContentText("Update Successfully");
        alert.show();
    }
    private void deleteNotification(){
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notification");
        alert.setContentText("Delete Successfully ! ");
        alert.show();
    }

    private void closeAddSupplierForm() {
        Stage stage = (Stage) addSupplierForm.getScene().getWindow();
        stage.close();
    }

    public void imgBackToDashBoardOnClick(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/dashboard-form.fxml"));
            Scene scene = new Scene(root);
            Stage stage=new Stage();
            stage.setScene(scene);
            stage.show();
            closeAddSupplierForm();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
