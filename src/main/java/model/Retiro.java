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

    @Override
    public void ejecutar() {
        if(origen.getSaldo()>=getMonto()){
            origen.retirar(getMonto());
            System.out.println("Retiro de $" + getMonto());
        }else{
            System.out.println("Saldo insuficiente");
        }

    }
}
