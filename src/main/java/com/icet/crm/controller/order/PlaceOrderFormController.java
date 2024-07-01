package com.icet.crm.controller.order;

import com.icet.crm.controller.customer.CustomerController;
import com.icet.crm.controller.item.ItemController;
import com.icet.crm.database.DBConnection;
import com.icet.crm.dto.Customer;
import com.icet.crm.dto.Item;
import com.icet.crm.dto.Order;
import com.icet.crm.dto.OrderDetails;
import com.icet.crm.dto.tablemodel.Table04TM;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlaceOrderFormController implements Initializable {
    public JFXComboBox cmbCustomerId;
    public JFXComboBox cmbItemCode;
    public Label lblOrderId;
    public JFXTextField txtCustomerName;
    public Label lblDate;
    public JFXTextField txtItemName;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtQauntity;
    public Label lblNetTotal;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colQuantity;
    public TableColumn colUnitPrice;
    public TableColumn colTotal;
    public TableView tblOrders;
    public AnchorPane placeOrderForm;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDate();
        loadCustomerId();
        loadItemCode();
        generateOrderId();

        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener(
                (observable,oldvalue,newValue) -> setCustomerData((String) newValue));

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldValue, newvalue) ->setItemData((String) newvalue));

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
    }

    private void loadDate(){
        Date date = new Date();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(fmt.format(date));
    }
    private void loadCustomerId(){
        ObservableList<Customer> allCustomers = CustomerController.getInstance().loadCustomers();
        ObservableList<String> customerIds = FXCollections.observableArrayList();
        allCustomers.forEach(customer -> customerIds.add(customer.getCustomerId()));
        cmbCustomerId.setItems(customerIds);

    }
    private void loadItemCode(){
        ObservableList<Item> allItems = ItemController.getInstance().loadItems();
        ObservableList<String> itemCodes = FXCollections.observableArrayList();
        allItems.forEach(item -> itemCodes.add(item.getItemCode()));
        cmbItemCode.setItems(itemCodes);
    }
    private void setCustomerData(String customerId){
        Customer customer = CustomerController.getInstance().getCustomerById(customerId);
        txtCustomerName.setText(customer.getName());
    }

    private void setItemData(String itemCode){
        Item item = ItemController.getInstance().getItemById(itemCode);
        txtItemName.setText(item.getName());
        txtUnitPrice.setText(item.getUnitPrice());
        txtQtyOnHand.setText(String.valueOf(item.getQuantity()));
    }

    public void imgMouseOnClick(MouseEvent mouseEvent) {
    }

    ObservableList<Table04TM> cartList = FXCollections.observableArrayList();
    public void btnAddToCartOnAction(ActionEvent actionEvent) {
        Table04TM tblCart = new Table04TM();
        tblCart.setItemCode((String) cmbItemCode.getValue());
        tblCart.setDescription(txtItemName.getText());
        tblCart.setQuantity(Integer.valueOf(txtQauntity.getText()));
        tblCart.setUnitPrice(Double.valueOf(txtUnitPrice.getText()));
        Integer qty= Integer.valueOf(txtQauntity.getText());
        Integer qtyOnHand = Integer.valueOf(txtQtyOnHand.getText());
        Double unitPrice= Double.valueOf(txtUnitPrice.getText());
        double total=qty * unitPrice;
        tblCart.setTotal(total);

        if(qty>qtyOnHand) {
            new Alert(Alert.AlertType.ERROR, "Invalid Quantity !").show();
            return;
        }
        
        cartList.add(tblCart);
        tblOrders.setItems(cartList);
        getNetTotal();
    }
    private void getNetTotal(){
        double netTotal=0;
        for(Table04TM cart: cartList){
            netTotal+=cart.getTotal();
        }
        lblNetTotal.setText(String.valueOf(netTotal));
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {

        try {
            String orderId = lblOrderId.getText();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date orderDate = format.parse(lblDate.getText());
            String customerId = cmbCustomerId.getValue().toString();

            List<OrderDetails> orderDetailsList = new ArrayList<>();

            for (Table04TM tblCart:cartList){
                String itemCode = tblCart.getItemCode();
                Integer quantity = tblCart.getQuantity();

                orderDetailsList.add(new OrderDetails(orderId,itemCode,quantity));
            }
            Order order = new Order(orderId,orderDate,customerId,orderDetailsList);
            boolean isAdd = OrderController.getInstance().placeOrder(order);
            if (isAdd){
                new Alert(Alert.AlertType.INFORMATION,"Place Order SuccessFully !").show();
                generateOrderId();
                clearCart();
                clearText();
            }

        } catch (ParseException | SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public  void generateOrderId() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM orders");
            Integer count = 0;
            while (resultSet.next()){
                count = resultSet.getInt(1);

            }
            if (count == 0) {
                lblOrderId.setText("O001");
            }
            String lastOrderId="";
            ResultSet resultSet1 = connection.createStatement().executeQuery("SELECT order_id\n" +
                    "FROM orders\n" +
                    "ORDER BY order_id DESC\n" +
                    "LIMIT 1;");
            if (resultSet1.next()){
                lastOrderId = resultSet1.getString(1);
                Pattern pattern = Pattern.compile("[A-Za-z](\\d+)");
                Matcher matcher = pattern.matcher(lastOrderId);
                if (matcher.find()) {
                    int number = Integer.parseInt(matcher.group(1));
                    number++;
                    lblOrderId.setText(String.format("O%03d", number));
                } else {
                    new Alert(Alert.AlertType.WARNING,"hello").show();
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    private void clearCart(){
        for(int i=0; i<tblOrders.getItems().size(); i++){
            tblOrders.getItems().clear();
        }
    }
    private void clearText(){
        cmbCustomerId.setItems(null);
        cmbItemCode.setItems(null);
        txtCustomerName.setText(null);
        txtItemName.setText(null);
        txtQauntity.setText(null);
        txtQtyOnHand.setText(null);
        txtUnitPrice.setText(null);
        lblNetTotal.setText(null);
    }

    public void imgBackToDashBoardOnClick(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/dashboard-form.fxml"));
            Scene scene = new Scene(root);
            Stage stage=new Stage();
            stage.setScene(scene);
            stage.show();
            closePlaceOrderForm();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void closePlaceOrderForm() {
        Stage stage = (Stage) placeOrderForm.getScene().getWindow();
        stage.close();
    }

    public void btnViewOnAction(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/view-order-form.fxml"));
            Scene scene = new Scene(root);
            Stage stage=new Stage();
            stage.setScene(scene);
            stage.show();
            closePlaceOrderForm();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
