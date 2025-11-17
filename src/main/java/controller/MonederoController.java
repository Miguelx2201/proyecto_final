package controller;

import app.AppState;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Cliente;
import model.Monedero;
import model.TipoMonedero;

import java.io.IOException;

public class MonederoController {

    @FXML private Label lblSaldo;
    @FXML private TableView<Monedero> tablaMonederos;
    @FXML private TableColumn<Monedero, String> columnaCodigo;
    @FXML private TableColumn<Monedero, Double> columnaSaldo;

    @FXML
    public void initialize() {
        Cliente c = AppState.getInstance().getClienteActual();

        // Si no tiene monederos, agregamos uno por defecto
        if (c.getListaMonederos().isEmpty()) {
            c.getListaMonederos().add(new Monedero(c, 0, "M001", TipoMonedero.AHORROS));
        }

        // Configurar columnas
        columnaCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        columnaSaldo.setCellValueFactory(new PropertyValueFactory<>("saldo"));

        // Cargar datos en la tabla
        tablaMonederos.setItems(FXCollections.observableArrayList(c.getListaMonederos()));

        // Mostrar saldo total (puede ser suma de todos los monederos)
        double saldoTotal = c.getListaMonederos().stream()
                .mapToDouble(Monedero::getSaldo)
                .sum();

        lblSaldo.setText("Saldo total: $" + saldoTotal);
    }


    @FXML
    public void volver() throws IOException {
        Stage stage = (Stage) lblSaldo.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard.fxml"))));
    }
    @FXML
    public void goAñadirMonedero() throws IOException {
        Stage stage = (Stage) lblSaldo.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/addMonedero.fxml"))));
    }
}
