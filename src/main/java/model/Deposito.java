package model;

public class Deposito extends Transaccion {
    private Monedero origen;
    public Deposito(double monto) {
        super(monto, 0.01);
        this.origen=origen;
    }

    public Monedero getOrigen() {
        return origen;
    }

    public void setOrigen(Monedero origen) {
        this.origen = origen;
    }

    @Override
    public int calcularPuntos() {
        return (int)(getMonto()/100);
    }

    @Override
    public void ejecutar() {
        origen.depositar(getMonto());
        System.out.println("Deposito realizado");
    }
}
