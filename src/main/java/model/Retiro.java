package model;

public class Retiro extends Transaccion {

    public Retiro(double monto, Monedero origen) {
        super(monto,origen);
    }


    @Override
    public int calcularPuntos() {
        return (int)(2*(getMonto()/100));
    }

    @Override
    public boolean ejecutar() {
        double total=calcularComision()+getMonto();
        if(getOrigen().getSaldo()>=total){
            getOrigen().retirar(total);
            getOrigen().registrarTransaccion(this);
            System.out.println("Retiro de $" + total);
            getOrigen().getPropietario().calcularPuntos();
            return true;
        }else{
            System.out.println("Saldo insuficiente");
            return false;
        }


    }

    @Override
    public void revertir() {
        double total = getMonto() + calcularComision();
        getOrigen().depositar(total);
        getOrigen().registrarTransaccion(this);
        System.out.println("Se revirtió el retiro de " + getMonto());
    }
}
