package com.icet.crm.controller.order;

import com.icet.crm.dto.OrderDetails;
import com.icet.crm.dto.tablemodel.Table06TM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewOrderFormController implements Initializable {
    public AnchorPane orderDetailForm;
    public TableView tblOrderDetails;
    public TableColumn colOrderId;
    public TableColumn colItemCode;
    public TableColumn colQuantity;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        OrderDetailController.getInstance().loadOrderDetails();
        loadTable6();
    }

    private void loadTable6(){
        ObservableList<Table06TM> table06Data = FXCollections.observableArrayList();
        ObservableList<OrderDetails> orderDetailsList= OrderDetailController.getInstance().loadOrderDetails();

        orderDetailsList.forEach(orderDetails ->{
            Table06TM table01 = new Table06TM(
                    orderDetails.getOrderId(),
                    orderDetails.getItemCode(),
                    orderDetails.getQuantity()
            );
            table06Data.add(table01);
        });
        tblOrderDetails.setItems(table06Data);
    }

    public void imgBackToPlaceOrderOnClick(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/place-order-form.fxml"));
            Scene scene = new Scene(root);
            Stage stage=new Stage();
            stage.setScene(scene);
            stage.show();
            closeOrderDetailsForm();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void closeOrderDetailsForm() {
        Stage stage = (Stage) orderDetailForm.getScene().getWindow();
        stage.close();
    }
}
