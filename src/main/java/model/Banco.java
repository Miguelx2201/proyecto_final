package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Banco {
    private String nombre;
    private String nit;
    private List<Cliente> listaClientes;
    private List<Monedero> listaMonederos;
    private List<TransaccionProgramada> listaTransaccionesProgramadas;

    public Banco(String nombre, String nit) {
        if (nombre.isBlank() || nit.isBlank()) {
            throw new IllegalArgumentException("El nombre y el NIT no pueden estar vacíos.");
        }
        this.nombre = nombre;
        this.nit = nit;
        this.listaClientes = new ArrayList<>();
        this.listaMonederos = new ArrayList<>();
        this.listaTransaccionesProgramadas = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public List<Cliente> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(ArrayList<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public List<Monedero> getListaMonederos() {
        return listaMonederos;
    }

    public void setListaMonederos(ArrayList<Monedero> listaMonederos) {
        this.listaMonederos = listaMonederos;
    }

    public List<TransaccionProgramada> getListaTransaccionesProgramadas() {
        return listaTransaccionesProgramadas;
    }

    public void setListaTransaccionesProgramadas(ArrayList<TransaccionProgramada> listaTransaccionesProgramadas) {
        this.listaTransaccionesProgramadas = listaTransaccionesProgramadas;
    }

    public boolean registrarCliente(Cliente cliente) {
        boolean existe = listaClientes.stream().anyMatch(c -> c.getCedula().equals(cliente.getCedula()));
        if (existe) return false;
        return listaClientes.add(cliente);
    }

    public Cliente buscarCliente(String cedula) {
        return listaClientes.stream().filter(c -> c.getCedula().equals(cedula))
                .findFirst()
                .orElse(null);
    }

    public boolean actualizarCliente(String cedula, String nuevoNombre,
                                     String nuevaDireccion, String nuevoCorreo) {
        Cliente cliente = buscarCliente(cedula);
        if (cliente == null) return false;
        cliente.setNombre(nuevoNombre);
        cliente.setDireccion(nuevaDireccion);
        cliente.setCorreo(nuevoCorreo);
        return true;
    }

    public boolean eliminarCliente(String cedula) {
        return listaClientes.removeIf(c -> c.getCedula().equals(cedula));
    }

    public boolean registrarMonedero(Monedero monedero) {
        boolean existe = listaMonederos.stream()
                .anyMatch(m -> m.getCodigo().equals(monedero.getCodigo()));

        if (existe) return false; // ya existe

        return listaMonederos.add(monedero);
    }

    public Monedero buscarMonedero(String codigo) {
        return listaMonederos
                .stream()
                .filter(m -> m.getCodigo().equals(codigo))
                .findFirst()
                .orElse(null);
    }

    public boolean actualizarMonedero(String id, TipoMonedero tipoMonederoNuevo) {
        Monedero m = buscarMonedero(id);
        if (m == null) return false;
        m.setTipoMonedero(tipoMonederoNuevo);
        return true;
    }

    public boolean eliminarMonedero(String codigo) {
        return listaMonederos.removeIf(m -> m.getCodigo().equals(codigo));
    }

    public boolean registrarTransaccionProgramada(TransaccionProgramada tp) {
        boolean existe = listaTransaccionesProgramadas.stream()
                .anyMatch(t ->
                        t.getFechaProgramada().equals(tp.getFechaProgramada()) &&
                                t.getTransaccion().getOrigen().equals(tp.getTransaccion().getOrigen()) &&
                                t.getTransaccion().getMonto() == tp.getTransaccion().getMonto()
                );

        if (existe) return false; // ya existe

        return listaTransaccionesProgramadas.add(tp);
    }

    public TransaccionProgramada buscarTransaccionProgramada(LocalDate fecha) {
        return listaTransaccionesProgramadas.stream()
                .filter(tp -> tp.getFechaProgramada().equals(fecha))
                .findFirst()
                .orElse(null);
    }

    public boolean actualizarTransaccionProgramada(LocalDate fechaActual, LocalDate nuevaFecha) {
        TransaccionProgramada tp = buscarTransaccionProgramada(fechaActual);
        if (tp == null) return false;
        tp.setFechaProgramada(nuevaFecha);
        return true;
    }

    public boolean eliminarTransaccionProgramada(LocalDate fecha) {
        return listaTransaccionesProgramadas.removeIf(tp -> tp.getFechaProgramada().equals(fecha));
    }


    public void ordenarTransaccionesPorFecha() {
    // Ordena directamente usando una expresión lambda para el Comparator
    Collections.sort(
            listaTransaccionesProgramadas,
            (t1, t2) -> t1.getFechaProgramada().compareTo(t2.getFechaProgramada())
    );
}

    public void procesarTransaccionesOrdenadas(LocalDate fechaActual) {
        ordenarTransaccionesPorFecha();
        for (TransaccionProgramada tp : listaTransaccionesProgramadas) {
            tp.ejecutarSiCorresponde(fechaActual);
        }
    }

    public void revisarRecordatorios() {
        LocalDate hoy = LocalDate.now();
        for (TransaccionProgramada tp : listaTransaccionesProgramadas) {
            long diasRestantes = java.time.temporal.ChronoUnit.DAYS.between(hoy, tp.getFechaProgramada());
            if (diasRestantes == 1) {
                Cliente c = tp.getTransaccion().getOrigen().getPropietario();
                c.enviarNotificacion("Recordatorio: mañana se ejecutará una transacción programada de $" + tp.getTransaccion().getMonto());
            }
        }
    }

    public boolean verificarCliente(String cedula, String contraseña) {
        Cliente cliente = buscarCliente(cedula);
        if(cliente == null) return false;
        return cliente.getContraseña().equals(contraseña);
    }


}
