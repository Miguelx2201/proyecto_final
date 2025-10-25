package model;

import java.util.ArrayList;

public class Monedero implements Puntuable {
    private double saldo;
    private int puntos;
    private int numTransacciones;
    private String clave;
    private TipoMonedero tipoMonedero;
    private ArrayList<Transaccion> listaTransacciones;

    public Monedero(double saldo,String clave, TipoMonedero tipoMonedero) {
        this.saldo = saldo;
        this.clave = clave;
        this.tipoMonedero = tipoMonedero;
        this.listaTransacciones = new ArrayList<>();
        this.puntos = calcularPuntos();
        this.numTransacciones = listaTransacciones.size();
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getNumTransacciones() {
        return numTransacciones;
    }

    public void setNumTransacciones(int numTransacciones) {
        this.numTransacciones = numTransacciones;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public TipoMonedero getTipoMonedero() {
        return tipoMonedero;
    }

    public void setTipoMonedero(TipoMonedero tipoMonedero) {
        this.tipoMonedero = tipoMonedero;
    }

    public ArrayList<Transaccion> getListaTransacciones() {
        return listaTransacciones;
    }

    public void setListaTransacciones(ArrayList<Transaccion> listaTransacciones) {
        this.listaTransacciones = listaTransacciones;
    }

    public int calcularPuntos(){
        for(Transaccion transaccion : listaTransacciones){
            puntos+=transaccion.calcularPuntos();
        }
        return puntos;
    }

    public void depositar(double monto) {
        if (monto > 0) {
            saldo += monto;
            System.out.println("Depósito exitoso: $" + monto);
        } else {
            System.out.println("Monto no válido para depósito.");
        }
    }

    public void retirar(double monto) {
        if (monto > 0 && saldo >= monto) {
            saldo -= monto;
            System.out.println("Retiro exitoso: $" + monto);
        } else {
            System.out.println("Fondos insuficientes o monto no válido.");
        }
    }


}
