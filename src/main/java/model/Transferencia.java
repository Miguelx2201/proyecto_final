package model;

public class Transferencia extends Transaccion{
    private Monedero destino;

    public Transferencia(double monto, double comision, Monedero origen, Monedero destino) {
        super(monto, comision,origen);
        this.destino = destino;
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

    @Override
    public void ejecutar() {
            if (getOrigen().getSaldo() >= getMonto()) {
                getOrigen().retirar(getMonto());
                destino.depositar(getMonto());
                System.out.println("Transferencia de $" + getMonto());
            } else {
                System.out.println("Saldo insuficiente para realizar la transferencia.");
            }

    }

    private double calcularComision{
        double base= monto*
    }
}
