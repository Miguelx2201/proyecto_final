package controller;

import app.AppState;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;

public class TransaccionController {

    @FXML private TextField txtMonto;
    @FXML private ListView<String> listaTransacciones;
    @FXML private Label lblError;

    private Cliente cliente;
    private Monedero monedero;

    @FXML
    public void initialize() {
        cliente = AppState.getInstance().getClienteActual();
        if(cliente.getListaMonederos().isEmpty()) {
            cliente.getListaMonederos().add(new Monedero(cliente, 0, "M001", null));
        }
        monedero = cliente.getListaMonederos().get(0);
        actualizarLista();
    }

    @FXML
    public void depositar() {
        try {
            double monto = Double.parseDouble(txtMonto.getText());
            monedero.depositar(monto);
            monedero.registrarTransaccion(new Deposito(monto,this.monedero ));
            actualizarLista();
            lblError.setText("");
        } catch (NumberFormatException e) {
            lblError.setText("Monto inválido.");
        }
    }

    @FXML
    public void retirar() {
        try {
            double monto = Double.parseDouble(txtMonto.getText());
            if(monedero.getSaldo() >= monto) {
                monedero.retirar(monto);
                monedero.registrarTransaccion(new Retiro(monto, this.monedero));
                actualizarLista();
                lblError.setText("");
            } else {
                lblError.setText("Saldo insuficiente.");
            }
        } catch (NumberFormatException e) {
            lblError.setText("Monto inválido.");
        }
    }

    private void actualizarLista() {
        listaTransacciones.setItems(FXCollections.observableArrayList(
                monedero.getListaTransacciones().stream()
                        .map(t -> {
                            String tipo;
                            if (t instanceof Deposito) {
                                tipo = "Depósito";
                            } else if (t instanceof Retiro) {
                                tipo = "Retiro";
                            } else if (t instanceof Transferencia transferencia) {
                                tipo = "Transferencia a " + transferencia.getDestino().getCodigo();
                            } else {
                                tipo = "Otra transacción";
                            }

                            return String.format("%s - $%.2f - Fecha: %s - Comisión: $%.2f",
                                    tipo,
                                    t.getMonto(),
                                    t.getFecha(),
                                    t.calcularComision()
                            );
                        })
                        .toList()
        ));
    }

    @FXML
    public void volver() throws IOException {
        Stage stage = (Stage) listaTransacciones.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard.fxml"))));
    }
}
