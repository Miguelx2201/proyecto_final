package model;

public class Retiro extends Transaccion {
    private Monedero origen;

    public Retiro(double monto, double comision, Monedero origen) {
        super(monto, comision);
        this.origen = origen;
    }

    public Monedero getOrigen() {
        return origen;
    }

    public void setOrigen(Monedero origen) {
        this.origen = origen;
    }

    @Override
    public int calcularPuntos() {
        return (int)(3*(getMonto()/100));
    }
}
