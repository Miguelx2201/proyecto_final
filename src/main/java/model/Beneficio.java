package model;

public enum Beneficio {
    DESCUENTO(100,"descuento"),
    RETIROSGRATIS(500,"Mes sin cargos"),
    BONOSALDO(1000,"Bono saldo");

    private int puntosNecesarios;
    private String descripcion;

    Beneficio(int puntosNecesarios, String descripcion) {
        this.puntosNecesarios = puntosNecesarios;
        this.descripcion = descripcion;
    }

    public int getPuntosNecesarios() {
        return puntosNecesarios;
    }

    public String getDescripcion() {
        return descripcion;
    }

}
