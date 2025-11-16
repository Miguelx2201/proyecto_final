package controller;

import app.AppState;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Cliente;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField txtCedula;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Label lblError;

    @FXML
    public void handleLogin() throws IOException {
        String cedula = txtCedula.getText();
        Cliente cliente = AppState.getInstance().buscarCliente(cedula);

        if(cliente != null) {
            AppState.getInstance().setClienteActual(cliente);
            // Abrir dashboard
            Stage stage = (Stage) txtCedula.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard.fxml"))));
        } else {
            lblError.setText("Cédula no encontrada.");
        }
    }

    @FXML
    public void goToRegistro() throws IOException {
        Stage stage = (Stage) txtCedula.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/registro.fxml"))));
    }
}
