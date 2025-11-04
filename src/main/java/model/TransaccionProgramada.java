package model;

import java.time.LocalDate;

public class TransaccionProgramada {
    private Transaccion transaccion;
    private LocalDate fechaProgramada;
    private boolean ejecutada;
    private Frecuencia frecuencia;

    public TransaccionProgramada(Transaccion transaccion, LocalDate fechaProgramada,Frecuencia frecuencia) {
        this.transaccion = transaccion;
        this.fechaProgramada = fechaProgramada;
        this.frecuencia=frecuencia;
        this.ejecutada = false;
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

    public Frecuencia getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(Frecuencia frecuencia) {
        this.frecuencia = frecuencia;
    }

    public void ejecutarSiCorresponde(LocalDate fechaActual) {
        if (!ejecutada && fechaActual.equals(fechaProgramada)) {
            transaccion.ejecutar();
            switch (frecuencia) {
                case SEMANAL:
                    fechaProgramada = fechaProgramada.plusWeeks(1);
                    break;
                case MENSUAL:
                    fechaProgramada = fechaProgramada.plusMonths(1);
                    break;
                default:
                    ejecutada = true; // única ejecución
                    break;
            }
            System.out.println("Transacción programada ejecutada el " + fechaActual);
        }
    }
}
