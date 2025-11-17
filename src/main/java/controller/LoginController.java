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
        String password = txtPassword.getText();

        Cliente cliente = AppState.getInstance().buscarCliente(cedula);

        if (cliente == null) {
            lblError.setText("Cédula no encontrada.");
            return;
        }

        // Aquí llamas a tu método para validar la contraseña
        boolean passwordCorrecta = AppState.getInstance().validarPassword(cliente, password);

        if (!passwordCorrecta) {
            lblError.setText("Contraseña incorrecta.");
            return;
        }

        // Si pasa ambas validaciones, iniciar sesión
        AppState.getInstance().setClienteActual(cliente);

        Stage stage = (Stage) txtCedula.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard.fxml"))));
    }


    @FXML
    public void goToRegistro() throws IOException {
        Stage stage = (Stage) txtCedula.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/registro.fxml"))));
    }
}
