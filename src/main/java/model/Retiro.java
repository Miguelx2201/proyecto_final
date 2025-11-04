package model;

public class Retiro extends Transaccion {

    public Retiro(double monto, double comision, Monedero origen) {
        super(monto, comision,origen);
    }


    @Override
    public int calcularPuntos() {
        return (int)(3*(getMonto()/100));
    }

    @Override
    public void ejecutar() {
        if(getOrigen().getSaldo()>=getMonto()){
            getOrigen().retirar(getMonto());
            System.out.println("Retiro de $" + getMonto());
        }else{
            System.out.println("Saldo insuficiente");
        }

    }
}
