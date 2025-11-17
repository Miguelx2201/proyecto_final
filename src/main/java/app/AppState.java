package app;

import model.Cliente;
import model.TransaccionProgramada;

import java.util.ArrayList;
import java.util.List;

public class AppState {

    // -------- SINGLETON --------
    private static AppState instance;

    public static AppState getInstance() {
        if (instance == null) {
            instance = new AppState();
        }
        return instance;
    }

    // -------- DATOS COMPARTIDOS --------
    private List<Cliente> clientes;
    private Cliente clienteActual;
    private List<TransaccionProgramada> transaccionesProgramadas;

    private AppState() {
        this.clientes = new ArrayList<>();
        this.transaccionesProgramadas = new ArrayList<>();
    }

    // -------- MANEJO DE CLIENTES --------

    public void agregarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public Cliente buscarCliente(String cedula) {
        return clientes.stream()
                .filter(c -> c.getCedula().equals(cedula))
                .findFirst()
                .orElse(null);
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public Cliente getClienteActual() {
        return clienteActual;
    }

    public void setClienteActual(Cliente clienteActual) {
        this.clienteActual = clienteActual;
    }

    // -------- TRANSACCIONES PROGRAMADAS --------

    public void agregarTransaccionProgramada(TransaccionProgramada t) {
        transaccionesProgramadas.add(t);
    }

    public List<TransaccionProgramada> getTransaccionesProgramadas() {
        return transaccionesProgramadas;
    }

    public boolean validarPassword(Cliente cliente, String contraseña) {
        return cliente.getContraseña().equals(contraseña);
    }
}
