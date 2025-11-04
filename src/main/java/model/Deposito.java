package model;

public class Deposito extends Transaccion {
    public Deposito(double monto, double comision, Monedero origen) {
        super(monto, comision, origen);
    }

    @Override
    public int calcularPuntos() {
        return (int)(getMonto()/100);
    }

    @Override
    public void ejecutar() {
        getOrigen().depositar(getMonto());
        System.out.println("Deposito realizado");
    }
}
