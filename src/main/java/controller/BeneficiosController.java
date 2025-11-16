package controller;

import app.AppState;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Cliente;

import java.io.IOException;

public class BeneficiosController {

    @FXML private Label lblPuntos, lblRango, lblMensaje;
    @FXML private TextField txtCanje;

    private Cliente cliente;

    @FXML
    public void initialize() {
        cliente = AppState.getInstance().getClienteActual();
        actualizarLabels();
    }

    private void actualizarLabels() {
        lblPuntos.setText("Puntos: " + cliente.getPuntosTotales());
        lblRango.setText("Rango: " + cliente.getRango());
    }

    @FXML
    public void canjear() {
        try {
            int monto = Integer.parseInt(txtCanje.getText());
            if(cliente.getPuntosTotales() >= monto) {
                cliente.setPuntosTotales(cliente.getPuntosTotales() - monto);
                lblMensaje.setText("Canje realizado exitosamente!");
                actualizarLabels();
            } else {
                lblMensaje.setText("No tienes suficientes puntos.");
            }
        } catch (NumberFormatException e) {
            lblMensaje.setText("Monto inválido.");
        }
    }

    @FXML
    public void volver() throws IOException {
        Stage stage = (Stage) lblPuntos.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard.fxml"))));
    }
}
