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
        double total=calcularComision()+getMonto();
            if (getOrigen().getSaldo() >= getMonto()) {
                getOrigen().retirar(getMonto());
                destino.depositar(getMonto());
                System.out.println("Transferencia de $" + getMonto());
                calcularPuntos();
            } else {
                System.out.println("Saldo insuficiente para realizar la transferencia.");
            }

    }

    @Override
    public void revertir() {
        double total = getMonto() + calcularComision();
        if (destino.getSaldo() >= getMonto()) {
            destino.retirar(getMonto());
            getOrigen().depositar(total);
            System.out.println("Se revirtió la transferencia de " + getMonto());
        } else {
            System.out.println("No se puede revertir: destino no tiene el saldo suficiente.");
        }
    }

}
