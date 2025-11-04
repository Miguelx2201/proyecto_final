package model;

import java.time.LocalDate;

public abstract class Transaccion implements Puntuable{
    private String codigo;
    private LocalDate fecha;
    private double monto;
    private double comision;
    private Monedero origen;

    public Transaccion(double monto, double comision,Monedero origen) {
        this.codigo = codigo;
        this.origen = origen;
        this.fecha = LocalDate.now();
        this.monto = monto;
        this.comision = comision;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public double getComision() {
        return comision;
    }

    public void setComision(double comision) {
        this.comision = comision;
    }

    public Monedero getOrigen() {
        return origen;
    }

    public void setOrigen(Monedero origen) {
        this.origen = origen;
    }

    public abstract void ejecutar();

}
