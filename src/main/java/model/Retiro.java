package model;

public class Retiro extends Transaccion {

    public Retiro(double monto, double comision, Monedero origen) {
        super(monto, comision,origen);
    }


    @Override
    public int calcularPuntos() {
        return (int)(2*(getMonto()/100));
    }

    @Override
    public void ejecutar() {
        double total=calcularComision()+getMonto();
        if(getOrigen().getSaldo()>=getMonto()){
            getOrigen().retirar(total);
            System.out.println("Retiro de $" + total);
            calcularPuntos();
        }else{
            System.out.println("Saldo insuficiente");
        }

    }

    @Override
    public void revertir() {
        double total = getMonto() + calcularComision();
        getOrigen().depositar(total);
        System.out.println("Se revirtió el retiro de " + getMonto());
    }
}
