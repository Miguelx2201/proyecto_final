package controller;

import app.AppState;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {

    @FXML private Label lblBienvenida;

    @FXML
    public void initialize() {
        lblBienvenida.setText("Bienvenido, " + AppState.getInstance().getClienteActual().getNombre());
    }

    @FXML
    public void goMonederos() throws IOException {
        Stage stage = (Stage) lblBienvenida.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/monedero.fxml"))));
    }

    @FXML
    public void goTransacciones() throws IOException {
        Stage stage = (Stage) lblBienvenida.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/transaccion.fxml"))));
    }

    @FXML
    public void goBeneficios() throws IOException {
        Stage stage = (Stage) lblBienvenida.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/beneficios.fxml"))));
    }

    @FXML
    public void logout() throws IOException {
        AppState.getInstance().setClienteActual(null);
        Stage stage = (Stage) lblBienvenida.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/login.fxml"))));
    }
    @FXML
    public void goReporte() throws IOException {
        Stage stage = (Stage) lblBienvenida.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/reporte.fxml"))));
    }
}
