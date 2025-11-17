package controller;

import app.AppState;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;

public class AddMonederoController {

    @FXML private TextField txtCodigo;
    @FXML private TextField txtSaldo;
    @FXML private ComboBox<TipoMonedero> comboTipo;
    @FXML private Label lblError;

    @FXML
    public void initialize() {
        comboTipo.getItems().setAll(TipoMonedero.values());
    }

    @FXML
    public void handleCrear() throws IOException {

        String codigo = txtCodigo.getText();
        String saldoStr = txtSaldo.getText();
        TipoMonedero tipo = comboTipo.getValue();

        // Validaciones
        if (codigo.isEmpty() || saldoStr.isEmpty() || tipo == null) {
            lblError.setText("Todos los campos son obligatorios.");
            return;
        }

        double saldo;
        try {
            saldo = Double.parseDouble(saldoStr);
        } catch (NumberFormatException e) {
            lblError.setText("El saldo debe ser un número válido.");
            return;
        }

        if (saldo < 0) {
            lblError.setText("El saldo no puede ser negativo.");
            return;
        }

        Cliente clienteActual = AppState.getInstance().getClienteActual();

        // Verificar si ya existe un monedero con ese código
        boolean existe = clienteActual.getListaMonederos().stream()
                .anyMatch(m -> m.getCodigo().equalsIgnoreCase(codigo));

        if (existe) {
            lblError.setText("Ya existe un monedero con ese código.");
            return;
        }

        // Crear el monedero
        Monedero nuevo = new Monedero(clienteActual, saldo, codigo, tipo);
        clienteActual.getListaMonederos().add(nuevo);

        Stage stage = (Stage) lblError.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/monedero.fxml"))));
    }

    @FXML
    public void handleCancelar() throws IOException {
        Stage stage = (Stage) lblError.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/monedero.fxml"))));
    }
}
