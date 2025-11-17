package controller;

import app.AppState;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.util.Arrays;

public class TransaccionController {

    @FXML private TextField txtMonto, txtCodigo;
    @FXML private ListView<String> listaTransacciones;
    @FXML private Label lblError;
    @FXML private CheckBox checkProgramada;
    @FXML private DatePicker dateProgramada;
    @FXML private ComboBox<String> comboFrecuencia;
    @FXML private HBox panelProgramada;

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
        comboFrecuencia.getItems().setAll(Arrays.toString(FrecuenciaTransaccionProg.values()));
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
    @FXML
    public void transferir() {
        try {
            double monto = Double.parseDouble(txtMonto.getText());
            String codigoDestino = txtCodigo.getText();

            // Validaciones básicas
            if (codigoDestino == null || codigoDestino.trim().isEmpty()) {
                lblError.setText("Debe ingresar el código del monedero destino.");
                return;
            }

            if (monto <= 0) {
                lblError.setText("El monto debe ser mayor a 0.");
                return;
            }

            // Buscar monedero destino dentro del cliente actual
            Cliente clienteActual = AppState.getInstance().getClienteActual();
            Monedero destino = clienteActual.getListaMonederos().stream()
                    .filter(m -> m.getCodigo().equalsIgnoreCase(codigoDestino))
                    .findFirst()
                    .orElse(null);

            if (destino == null) {
                lblError.setText("No existe un monedero con ese código.");
                return;
            }

            // Evitar transferencias al mismo monedero
            if (destino == monedero) {
                lblError.setText("No puede transferirse a sí mismo.");
                return;
            }

            // Validar saldo
            if (monedero.getSaldo() < monto) {
                lblError.setText("Saldo insuficiente.");
                return;
            }

            // Realizar transferencia
            monedero.retirar(monto);   // resta al origen
            destino.depositar(monto);  // suma al destino

            // Registrar transacción en ambos monederos
            Transferencia trans = new Transferencia(monto, monedero, destino);

            monedero.registrarTransaccion(trans);  // en origen
            destino.registrarTransaccion(trans);   // en destino

            actualizarLista();
            lblError.setText("Transferencia realizada.");

        } catch (NumberFormatException e) {
            lblError.setText("Monto inválido.");
        }
    }
    @FXML
    public void toggleProgramada() {
        boolean activa = checkProgramada.isSelected();
        panelProgramada.setVisible(activa);
        panelProgramada.setManaged(activa);
    }

}
