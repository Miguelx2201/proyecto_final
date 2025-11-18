package controller;

import app.AppState;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.Cliente;
import model.Monedero;

import java.net.URL;
import java.util.ResourceBundle;

public class ReporteController implements Initializable {

    @FXML
    private TextArea txtInforme;

    private Cliente cliente;
    private Monedero monedero;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Obtener cliente actual desde AppState
        cliente = AppState.getInstance().getClienteActual();

        // Asegurarse de que tenga un monedero
        if (cliente.getListaMonederos().isEmpty()) {
            cliente.getListaMonederos()
                    .add(new Monedero(cliente, 0, "M001", null));
        }

        // Usar el primer monedero
        monedero = cliente.getListaMonederos().get(0);

        // Cargar informe en la vista
        cargarInforme();
    }

    private void cargarInforme() {
        String informe = monedero.generarInformePatrones();
        txtInforme.setText(informe);
    }

    @FXML
    public void volver() throws Exception {
        Stage stage = (Stage) txtInforme.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard.fxml"))));
    }
}
