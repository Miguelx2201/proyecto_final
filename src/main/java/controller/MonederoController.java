package controller;

import app.AppState;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Cliente;
import model.Monedero;

import java.io.IOException;

public class MonederoController {

    @FXML private Label lblSaldo;

    @FXML
    public void initialize() {
        Cliente c = AppState.getInstance().getClienteActual();
        if(c.getListaMonederos().isEmpty()) {
            c.getListaMonederos().add(new Monedero(c,0,"M001", null));
        }
        lblSaldo.setText("Saldo total: $" + c.getListaMonederos().get(0).getSaldo());
    }

    @FXML
    public void depositar() {
        Cliente c = AppState.getInstance().getClienteActual();
        Monedero m = c.getListaMonederos().get(0);
        m.depositar(10000); // ejemplo fijo
        lblSaldo.setText("Saldo total: $" + m.getSaldo());
    }

    @FXML
    public void retirar() {
        Cliente c = AppState.getInstance().getClienteActual();
        Monedero m = c.getListaMonederos().get(0);
        m.retirar(5000); // ejemplo fijo
        lblSaldo.setText("Saldo total: $" + m.getSaldo());
    }

    @FXML
    public void volver() throws IOException {
        Stage stage = (Stage) lblSaldo.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard.fxml"))));
    }
}
