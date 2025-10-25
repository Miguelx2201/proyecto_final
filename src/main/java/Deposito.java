package model;

public class Deposito extends Transaccion {

    public Deposito(double monto, Monedero destino) {
        super(monto, 0.01);
    }


    @Override
    public int calcularPuntos() {
        return (int)(getMonto()/100);
    }
}
