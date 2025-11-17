package controller;

import app.AppState;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Cliente;

import java.io.IOException;

public class RegistroController {

    @FXML private TextField txtNombre, txtCedula, txtDireccion, txtCorreo, txtEdad, txtContraseña;
    @FXML private Label lblError;

    @FXML
    public void handleRegistro() throws IOException {
        try {
            String nombre = txtNombre.getText();
            String cedula = txtCedula.getText();
            String direccion = txtDireccion.getText();
            String correo = txtCorreo.getText();
            int edad = Integer.parseInt(txtEdad.getText());
            String contraseña  = txtContraseña.getText();

            Cliente cliente = new Cliente(nombre, cedula, direccion, correo, edad, contraseña);
            AppState.getInstance().agregarCliente(cliente);
            AppState.getInstance().setClienteActual(cliente);

            // Ir al dashboard
            Stage stage = (Stage) txtNombre.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard.fxml"))));

        } catch (Exception e) {
            lblError.setText("Error al registrar: " + e.getMessage());
        }
    }
}
