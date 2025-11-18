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
import java.time.LocalDate;
import java.util.Arrays;

public class TransaccionController {

    @FXML
    private TextField txtMonto, txtCodigo;
    @FXML
    private ListView<String> listaTransacciones;
    @FXML
    private Label lblError;
    @FXML
    private CheckBox checkProgramada;
    @FXML
    private DatePicker dateProgramada;
    @FXML
    private ComboBox<String> comboTransaccion;
    @FXML private ComboBox<FrecuenciaTransaccionProg> comboFrecuencia;
    @FXML
    private VBox panelProgramada;

    private Cliente cliente;
    private Monedero monedero;

    @FXML
    public void initialize() {
        cliente = AppState.getInstance().getClienteActual();
        if (cliente.getListaMonederos().isEmpty()) {
            cliente.getListaMonederos().add(new Monedero(cliente, 0, "M001", null));
        }
        monedero = cliente.getListaMonederos().get(0);
        actualizarLista();
        comboFrecuencia.getItems().setAll(FrecuenciaTransaccionProg.values());
        comboTransaccion.getItems().setAll("Deposito", "Retiro", "Transferencia");
    }

    @FXML
    public void depositar() {
        try {
            double monto = Double.parseDouble(txtMonto.getText());

            // Crear la transacción
            Deposito deposito = new Deposito(monto, monedero);

            // Ejecutar la transacción usando su propio método ejecutar()
            boolean exito = deposito.ejecutar();

            if (exito) {
                // Registrar la transacción en el monedero
                //monedero.registrarTransaccion(deposito);
                deposito.getOrigen().getPropietario().enviarNotificacion("Nuevo deposito de :"+deposito.getMonto() );
                actualizarLista();
                lblError.setText("");
            } else {
                lblError.setText("No se pudo realizar el depósito.");
            }

        } catch (NumberFormatException e) {
            lblError.setText("Monto inválido.");
        }
    }


    @FXML
    public void retirar() {
        try {
            double monto = Double.parseDouble(txtMonto.getText());

            // Crear la transacción
            Retiro retiro = new Retiro(monto, monedero);

            // Ejecutar la transacción (usa la lógica interna de Retiro)
            boolean exito = retiro.ejecutar();

            if (exito) {
                // Registrar en el historial del monedero
                //monedero.registrarTransaccion(retiro);
                retiro.getOrigen().getPropietario().enviarNotificacion("Nuevo retiro de :"+retiro.getMonto() );
                actualizarLista();
                lblError.setText("");
            } else {
                // Si ejecutar() falló
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

            // Crear la transacción
            Transferencia trans = new Transferencia(monto, monedero, destino);

            // Ejecutar la transacción usando su propia lógica
            boolean exito = trans.ejecutar();

            if (!exito) { // si ejecutar() falló, por ejemplo por falta de saldo
                lblError.setText("Saldo insuficiente.");
                return;
            }
            trans.getOrigen().getPropietario().enviarNotificacion("Nuevo tranferencia de :"+trans.getMonto() );
            // Registrar transacción en ambos monederos
            //monedero.registrarTransaccion(trans);  // origen
            //destino.registrarTransaccion(trans);   // destino

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
    @FXML
    public void programar() {

        if (!checkProgramada.isSelected()) {
            lblError.setText("Debe marcar la opción de transacción programada.");
            return;
        }

        String montoStr = txtMonto.getText();
        LocalDate fecha = dateProgramada.getValue();
        FrecuenciaTransaccionProg frecuencia = (comboFrecuencia.getValue());
        String tipoStr = comboTransaccion.getValue();
        // -> en TU caso este ComboBox debería contener: "Depósito", "Retiro", "Transferencia"

        // Validaciones
        if (montoStr.isEmpty() || tipoStr == null || fecha == null || frecuencia == null) {
            lblError.setText("Debe completar todos los campos de la transacción programada.");
            return;
        }

        double monto;
        try {
            monto = Double.parseDouble(montoStr);
        } catch (NumberFormatException e) {
            lblError.setText("El monto debe ser un número válido.");
            return;
        }

        if (monto <= 0) {
            lblError.setText("El monto debe ser mayor a 0.");
            return;
        }

        Cliente clienteActual = AppState.getInstance().getClienteActual();
        Monedero origen = this.monedero;
        Monedero destino = null;

    /* ---------------------------
       CONTRUIMOS LA TRANSACCIÓN
       --------------------------- */

        Transaccion transaccion = null;

        switch (tipoStr) {

            case "Depósito":
                transaccion = new Deposito(monto, origen);
                break;

            case "Retiro":
                transaccion = new Retiro(monto, origen);
                break;

            case "Transferencia":
                String codigoDestino = txtCodigo.getText();

                if (codigoDestino.isEmpty()) {
                    lblError.setText("Debe ingresar el código de monedero destino.");
                    return;
                }

                destino = clienteActual.getListaMonederos().stream()
                        .filter(m -> m.getCodigo().equalsIgnoreCase(codigoDestino))
                        .findFirst()
                        .orElse(null);

                if (destino == null) {
                    lblError.setText("No existe un monedero destino con ese código.");
                    return;
                }

                if (destino == origen) {
                    lblError.setText("No puede transferirse a sí mismo.");
                    return;
                }

                transaccion = new Transferencia(monto, origen, destino);
                break;

            default:
                lblError.setText("Tipo de transacción inválido.");
                return;
        }

    /* ---------------------------
       CREAMOS LA TRANSACCIÓN PROGRAMADA
       --------------------------- */

        TransaccionProgramada programada =
                new TransaccionProgramada(transaccion, fecha, frecuencia);

        AppState.getInstance().getTransaccionesProgramadas().add(programada);

        lblError.setText("Transacción programada correctamente.");
    }

}
