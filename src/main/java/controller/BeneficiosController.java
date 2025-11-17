package controller;

import app.AppState;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Beneficio;
import model.Cliente;
import model.Monedero;

import java.io.IOException;

public class BeneficiosController {

    @FXML private Label lblPuntos, lblRango, lblMensaje;
    @FXML private ComboBox<Beneficio> comboBeneficio;

    private Cliente cliente;

    @FXML
    public void initialize() {
        cliente = AppState.getInstance().getClienteActual();
        actualizarLabels();
        comboBeneficio.getItems().setAll(Beneficio.values());
    }

    private void actualizarLabels() {
        lblPuntos.setText("Puntos: " + cliente.getPuntosTotales());
        lblRango.setText("Rango: " + cliente.getRango());
    }

    @FXML
    public void canjear() {

        // Obtener beneficio seleccionado del ComboBox
        Beneficio beneficio = comboBeneficio.getValue();

        if (beneficio == null) {
            lblMensaje.setText("Debe seleccionar un beneficio.");
            return;
        }

        // Intentar canjear usando el método del cliente
        boolean exito = cliente.canjearBeneficio(beneficio);

        if (exito) {
            lblMensaje.setText("¡Canje realizado exitosamente!");
            actualizarLabels();   // Actualizas puntos, etc.
        } else {
            lblMensaje.setText("No tienes suficientes puntos para este beneficio.");
        }
    }



    @FXML
    public void volver() throws IOException {
        Stage stage = (Stage) lblPuntos.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard.fxml"))));
    }
}
