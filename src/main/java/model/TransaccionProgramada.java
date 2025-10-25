package model;

import java.time.LocalDate;

public class TransaccionProgramada {
    private Transaccion transaccion;
    private LocalDate fechaProgramada;
    private boolean ejecutada;
    private Monedero monederoDestino;
    private Monedero monederOrigen;

    public TransaccionProgramada(Transaccion transaccion, LocalDate fechaProgramada) {
        this.transaccion = transaccion;
        this.fechaProgramada = fechaProgramada;
        this.ejecutada = false;
        this.monederoDestino = monederoDestino;
        this.monederOrigen = monederOrigen;
    }

    public Transaccion getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(Transaccion transaccion) {
        this.transaccion = transaccion;
    }

    public LocalDate getFechaProgramada() {
        return fechaProgramada;
    }

    public void setFechaProgramada(LocalDate fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }

    public boolean isEjecutada() {
        return ejecutada;
    }

    public void setEjecutada(boolean ejecutada) {
        this.ejecutada = ejecutada;
    }

    public Monedero getMonederoDestino() {
        return monederoDestino;
    }

    public void setMonederoDestino(Monedero monederoDestino) {
        this.monederoDestino = monederoDestino;
    }

    public Monedero getMonederOrigen() {
        return monederOrigen;
    }

    public void setMonederOrigen(Monedero monederOrigen) {
        this.monederOrigen = monederOrigen;
    }

    public void ejecutarSiCorresponde(LocalDate fechaActual) {
        if (!ejecutada && fechaActual.equals(fechaProgramada)) {
            transaccion.ejecutar();
            ejecutada = true;
            System.out.println("Transacción programada ejecutada el " + fechaActual);
        }
    }
}
