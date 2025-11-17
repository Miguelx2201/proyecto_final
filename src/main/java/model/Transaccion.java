package model;

import java.time.LocalDate;

public abstract class Transaccion implements Puntuable{
    private String codigo;
    private final LocalDate fecha;
    private double monto;
    private double comision;
    private Monedero origen;

    public Transaccion(double monto,Monedero origen) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero.");
        }
        if (origen == null) {
            throw new IllegalArgumentException("El monedero de origen no puede ser nulo.");
        }
        this.codigo = codigo;
        this.origen = origen;
        this.fecha = LocalDate.now();
        this.monto = monto;
        this.comision = 0.01;
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

    public abstract boolean ejecutar();

    public abstract void revertir();
    public double calcularComision(){
        return monto*comision;
    }

}
