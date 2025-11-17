package model;

public class Transferencia extends Transaccion{
    private Monedero destino;

    public Transferencia(double monto, Monedero origen, Monedero destino) {
        super(monto,origen);
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
    public boolean ejecutar() {
        double total=calcularComision()+getMonto();
            if (getOrigen().getSaldo() >=total) {
                getOrigen().retirar(getMonto());
                destino.depositar(getMonto());
                destino.registrarTransaccion(this);
                getOrigen().registrarTransaccion(this);
                System.out.println("Transferencia de $" + getMonto());
                getOrigen().getPropietario().calcularPuntos();
                return true;
            } else {
                System.out.println("Saldo insuficiente para realizar la transferencia.");
                return false;
            }

    }

    @Override
    public void revertir() {
        double total = getMonto() + calcularComision();
        if (destino.getSaldo() >= getMonto()) {
            destino.retirar(getMonto());
            getOrigen().depositar(total);
            getOrigen().registrarTransaccion(this);
            destino.registrarTransaccion(this);
            System.out.println("Se revirtió la transferencia de " + getMonto());
        } else {
            System.out.println("No se puede revertir: destino no tiene el saldo suficiente.");
        }
    }

}
