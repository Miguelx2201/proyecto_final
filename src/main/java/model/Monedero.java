package model;

import java.util.ArrayList;
import java.util.List;

public class Monedero implements Puntuable {
    private double saldo;
    private Cliente propietario;
    private int puntos;
    private int numTransacciones;
    private String codigo;
    private TipoMonedero tipoMonedero;
    private List<Transaccion> listaTransacciones;

    public Monedero(Cliente propietario,double saldo,String codigo, TipoMonedero tipoMonedero) {
        this.saldo = saldo;
        this.codigo = codigo;
        this.tipoMonedero = tipoMonedero;
        this.propietario = propietario;
        this.listaTransacciones = new ArrayList<>();
        this.puntos = 0;
        this.numTransacciones =0;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public TipoMonedero getTipoMonedero() {
        return tipoMonedero;
    }

    public void setTipoMonedero(TipoMonedero tipoMonedero) {
        this.tipoMonedero = tipoMonedero;
    }

    public List<Transaccion> getListaTransacciones() {
        return listaTransacciones;
    }

    public void setListaTransacciones(List<Transaccion> listaTransacciones) {
        this.listaTransacciones = listaTransacciones;
    }

    public Cliente getPropietario() {
        return propietario;
    }

    public void setPropietario(Cliente propietario) {
        this.propietario = propietario;
    }



    public int calcularPuntos(){
        int total=0;
        for(Transaccion transaccion : listaTransacciones){
            total+=transaccion.calcularPuntos();
        }
        this.puntos=total;
        return total;
    }

    public void registrarTransaccion(Transaccion transaccion) {
        listaTransacciones.add(transaccion);
        numTransacciones = listaTransacciones.size();
        calcularPuntos();
    }

    public List<Transaccion> obtenerHistorial() {
        return listaTransacciones.stream()
                .sorted((t1, t2) -> t1.getFecha().compareTo(t2.getFecha()))
                .toList();
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

    public double totalGastado() {
        return listaTransacciones.stream()
                .filter(t -> t instanceof Retiro || t instanceof Transferencia)
                .mapToDouble(Transaccion::getMonto)
                .sum();
    }

    public double totalRecibido() {
        return listaTransacciones.stream()
                .filter(t -> t instanceof Deposito)
                .mapToDouble(Transaccion::getMonto)
                .sum();
    }

    public double balanceGeneral() {
        return totalRecibido() - totalGastado();
    }

    public long cantidadDepositos() {
        return listaTransacciones.stream()
                .filter(t -> t instanceof Deposito).count();
    }

    public long cantidadRetiros() {
        return listaTransacciones.stream()
                .filter(t -> t instanceof Retiro).count();
    }

    public long cantidadTransferencias() {
        return listaTransacciones.stream()
                .filter(t -> t instanceof Transferencia).count();
    }

    public String determinarTipoGastoPredominante() {
        long retiros = cantidadRetiros();
        long transferencias = cantidadTransferencias();
        if (retiros == 0 && transferencias == 0)
            return "No hay gastos registrados";
        if (retiros > transferencias)
            return "Retiros en efectivo";
        if (transferencias > retiros)
            return "Transferencias realizadas";
        return "Gasto equilibrado entre retiros y transferencias";
    }

    public String generarInformePatrones() {
        return """
            ANÁLISIS DE PATRONES DE GASTO
            Total gastado: $""" + totalGastado() +
                "\nTotal recibido: $" + totalRecibido() +
                "\nBalance general: $" + balanceGeneral() +
                "\n\nTransacciones por tipo:" +
                "\n - Depósitos: " + cantidadDepositos() +
                "\n - Retiros: " + cantidadRetiros() +
                "\n - Transferencias: " + cantidadTransferencias() +
                "\nTipo de gasto predominante: " + determinarTipoGastoPredominante();
    }


}
