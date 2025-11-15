package model;

public class Deposito extends Transaccion {
    public Deposito(double monto, Monedero origen) {
        super(monto, origen);
    }

    @Override
    public int calcularPuntos() {
        return (int)(getMonto()/100);
    }

    @Override
    public void ejecutar() {
        getOrigen().depositar(getMonto());
        getOrigen().registrarTransaccion(this);
        System.out.println("Deposito realizado");
    }

    @Override
    public void revertir() {
        getOrigen().retirar(getMonto());
        getOrigen().registrarTransaccion(this);
        System.out.println("Deposito revertido");
    }
}
