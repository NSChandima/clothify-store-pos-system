package com.icet.crm.controller.customer;

import com.icet.crm.bo.BoFactory;
import com.icet.crm.bo.custom.CustomerBo;
import com.icet.crm.dto.Customer;
import com.icet.crm.dto.tablemodel.Table01TM;
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

public class CustomerFormController implements Initializable {
    public JFXTextField txtCustomerId;
    public JFXTextField txtCustomerName;
    public JFXTextField txtAddress;
    public JFXTextField txtContact;
    public TableView tblCustomer;
    public TableColumn colCustomerId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colContact;
    public AnchorPane addCustomerForm;


    private CustomerBo customerBo = BoFactory.getInstance().getBo(BoType.CUSTOMER);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        CustomerController.getInstance().loadCustomers();
        loadTable01();
    }

    public void btnAddOnAction(ActionEvent actionEvent) {

        Customer customer = new Customer();
        customer.setCustomerId(txtCustomerId.getText());
        customer.setName(txtCustomerName.getText());
        customer.setAddress(txtAddress.getText());
        customer.setContact(Integer.valueOf(txtContact.getText()));

        customerBo.saveCustomer(customer);
        clearText();
        CustomerController.getInstance().loadCustomers();
        loadTable01();

    }
    public void btnUpdateOnAction(ActionEvent actionEvent) {

        Customer customer = new Customer();
        customer.setCustomerId(txtCustomerId.getText());
        customer.setName(txtCustomerName.getText());
        customer.setAddress(txtAddress.getText());
        customer.setContact(Integer.valueOf(txtContact.getText()));

        customerBo.updateCustomer(customer);
        clearText();
        CustomerController.getInstance().loadCustomers();
        loadTable01();
        updateNotification();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {

        customerBo.deleteCustomer(txtCustomerId.getText());
        clearText();
        CustomerController.getInstance().loadCustomers();
        loadTable01();
        deleteNotification();

    }

    public void clearText(){
        txtCustomerId.setText(null);
        txtCustomerName.setText(null);
        txtAddress.setText(null);
        txtContact.setText(null);
    }

    private void loadTable01() {
        ObservableList<Table01TM> table01Data = FXCollections.observableArrayList();
        ObservableList<Customer> allCustomers=CustomerController.getInstance().loadCustomers();

        allCustomers.forEach(customer ->{
            Table01TM table01 = new Table01TM(
                    customer.getCustomerId(),
                    customer.getName(),
                    customer.getAddress(),
                    customer.getContact()
            );
            table01Data.add(table01);
        });
        tblCustomer.setItems(table01Data);

    }
    private void deleteNotification(){
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notification");
        alert.setContentText("Delete Successfully ! ");
        alert.show();
    }

    private void updateNotification(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notification");
        alert.setContentText("Update Successfully");
        alert.show();
    }

    public void imgBackToDashBoardOnClick(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/dashboard-form.fxml"));
            Scene scene = new Scene(root);
            Stage stage=new Stage();
            stage.setScene(scene);
            stage.show();
            closeAddCustomerForm();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void closeAddCustomerForm(){
        Stage stage = (Stage) addCustomerForm.getScene().getWindow();
        stage.close();
    }
}
