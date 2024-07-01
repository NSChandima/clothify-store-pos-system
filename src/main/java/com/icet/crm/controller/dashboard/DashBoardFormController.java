package com.icet.crm.controller.dashboard;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashBoardFormController {

    public AnchorPane dashBoardForm;

    public void imgAddCustomerOnClick(MouseEvent mouseEvent) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/add-customer-form.fxml"));
            Scene scene = new Scene(root);
            Stage stage=new Stage();
            stage.setScene(scene);
            stage.show();
            closeDashboardForm();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void closeDashboardForm(){
        Stage stage = (Stage) dashBoardForm.getScene().getWindow();
        stage.close();
    }

    public void imgAddItemOnClick(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/add-item-form.fxml"));
            Scene scene = new Scene(root);
            Stage stage=new Stage();
            stage.setScene(scene);
            stage.show();
            closeDashboardForm();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void imgPlaceOrderOnClick(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/place-order-form.fxml"));
            Scene scene = new Scene(root);
            Stage stage=new Stage();
            stage.setScene(scene);
            stage.show();
            closeDashboardForm();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void imgAddSupplierOnClick(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/add-supplier-form.fxml"));
            Scene scene = new Scene(root);
            Stage stage=new Stage();
            stage.setScene(scene);
            stage.show();
            closeDashboardForm();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void imgAddUserOnClick(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/add-user-form.fxml"));
            Scene scene = new Scene(root);
            Stage stage=new Stage();
            stage.setScene(scene);
            stage.show();
            closeDashboardForm();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void imgGenerateReportOnClick(MouseEvent mouseEvent) {

    }
}
