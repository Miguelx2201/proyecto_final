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

//    public void ordenarTransaccionesPorFecha() {
//        int n = listaTransaccionesProgramadas.size();
//        for (int i = 0; i < n - 1; i++) {
//            for (int j = 0; j < n - i - 1; j++) {
//                LocalDate f1 = listaTransaccionesProgramadas.get(j).getFechaProgramada();
//                LocalDate f2 = listaTransaccionesProgramadas.get(j + 1).getFechaProgramada();
//                if (f1.isAfter(f2)) {
//                    TransaccionProgramada temp = listaTransaccionesProgramadas.get(j);
//                    listaTransaccionesProgramadas.set(j, listaTransaccionesProgramadas.get(j + 1));
//                    listaTransaccionesProgramadas.set(j + 1, temp);
//                }
//            }
//        }
//    }
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


}
