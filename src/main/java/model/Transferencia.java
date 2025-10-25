package model;

public class Transferencia extends Transaccion{
    private Monedero origen;
    private Monedero destino;

    public Transferencia(double monto, double comision, Monedero origen, Monedero destino) {
        super(monto, comision);
        this.origen = origen;
        this.destino = destino;
    }

    public Monedero getOrigen() {
        return origen;
    }

    public void setOrigen(Monedero origen) {
        this.origen = origen;
    }

    public Monedero getDestino() {
        return destino;
    }

    public void setDestino(Monedero destino) {
        this.destino = destino;
    }

    @Override
    public int calcularPuntos() {
        return (int)(3*(getMonto()/100));
    }

}
