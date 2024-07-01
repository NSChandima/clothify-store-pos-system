package com.icet.crm.controller.user;

import com.icet.crm.bo.BoFactory;
import com.icet.crm.bo.custom.UserBo;
import com.icet.crm.database.DBConnection;
import com.icet.crm.dto.User;
import com.icet.crm.dto.tablemodel.Table03TM;
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
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserFormController implements Initializable {
    public JFXTextField txtUserId;
    public JFXTextField txtUserName;
    public JFXTextField txtEmail;
    public JFXTextField txtContact;
    public TableView tblUser;
    public TableColumn colUserId;
    public TableColumn colName;
    public TableColumn colContact;
    public TableColumn colEmail;
    public AnchorPane addEmployeeForm;

    private List<User> userList;

    private UserBo userBo = BoFactory.getInstance().getBo(BoType.USER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        loadUsers();
        loadTable03();

    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        User user = new User();
        user.setUserId(txtUserId.getText());
        user.setName(txtUserName.getText());
        user.setEmail(txtEmail.getText());
        user.setContact(Integer.valueOf(txtContact.getText()));

        userBo.saveUser(user);
        clearText();
        loadUsers();
        loadTable03();

    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {

        User user = new User();
        user.setUserId(txtUserId.getText());
        user.setName(txtUserName.getText());
        user.setEmail(txtEmail.getText());
        user.setContact(Integer.valueOf(txtContact.getText()));

        userBo.updateUser(user);
        clearText();
        loadUsers();
        loadTable03();
        updateNotification();

    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        try {
            String sql = "delete from user where user_id='"+ txtUserId.getText()+"'";
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            boolean isDelete = stm.execute(sql);
            userBo.deleteUser(txtUserId.getText());
            loadUsers();
            loadTable03();
            clearText();
            deleteNotification();

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void clearText(){
        txtUserId.setText(null);
        txtUserName.setText(null);
        txtEmail.setText(null);
        txtContact.setText(null);
    }

    private void loadUsers(){
        userList=new ArrayList<>();
        try {
            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM user");
            while (resultSet.next()) {
               User user = new User(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4)
                );
                userList.add(user);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    private void loadTable03(){
        ObservableList<Table03TM> table03Data = FXCollections.observableArrayList();

        userList.forEach(user ->{
            Table03TM table03 = new Table03TM(
                    user.getUserId(),
                    user.getName(),
                    user.getEmail(),
                    user.getContact()
            );
            table03Data.add(table03);
        });
        tblUser.setItems(table03Data);

    }
    public void imgMouseOnClick(MouseEvent mouseEvent) {
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
            closeAddEmployeeForm();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void closeAddEmployeeForm() {
        Stage stage = (Stage) addEmployeeForm.getScene().getWindow();
        stage.close();
    }
}
