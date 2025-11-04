package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cliente implements Puntuable,Notificable {
    private String nombre;
    private String cedula;
    private String direccion;
    private String correo;
    private int edad;
    private int puntosTotales;
    private List<Monedero> listaMonederos;
    private RangoCliente rango;
    private boolean descuentoTransferencia;
    private boolean retirosGratis;
    private LocalDate fechaFinRetirosGratis;

    public Cliente(String nombre, String cedula, String direccion, String correo,int edad) {
        if(nombre.isBlank()||cedula.isBlank()||direccion.isBlank()||correo.isBlank()) {
            throw  new IllegalArgumentException("Llene los datos correctamente");
        }
        this.nombre = nombre;
        this.cedula = cedula;
        this.direccion = direccion;
        this.correo = correo;
        this.edad = edad;
        this.fechaFinRetirosGratis=fechaFinRetirosGratis;
        this.descuentoTransferencia = false;
        this.retirosGratis = false;
        this.listaMonederos = new ArrayList<>();
        this.puntosTotales = calcularPuntos();
        this.rango=actualizarRango();
    }

    public RangoCliente getRango() {
        return rango;
    }

    public void setRango(RangoCliente rango) {
        this.rango = rango;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getPuntosTotales() {
        return puntosTotales;
    }

    public void setPuntosTotales(int puntosTotales) {
        this.puntosTotales = puntosTotales;
    }

    public List<Monedero> getListaMonederos() {
        return listaMonederos;
    }

    public void setListaMonederos(ArrayList<Monedero> listaMonederos) {
        this.listaMonederos = listaMonederos;
    }

    @Override
    public int calcularPuntos(){
        puntosTotales=0;
        for(Monedero m: listaMonederos){
            puntosTotales+=m.getPuntos();
        }
        return puntosTotales;
    }

    public RangoCliente actualizarRango(){
        if(puntosTotales<=500){
            rango=RangoCliente.BRONCE;
        }else if(puntosTotales>500&&puntosTotales<=1000){
            rango=RangoCliente.PLATA;
        }else if(puntosTotales>1000&&puntosTotales<=5000){
            rango=RangoCliente.ORO;
        }else{
            rango=RangoCliente.PLATINO;
        }
        return rango;
    }


    @Override
    public void enviarNotificacion(String mensaje) {
        System.out.println("🔔 Notificación para " + nombre + ": " + mensaje);
    }

    public void canjearBeneficio(Beneficio beneficio, Monedero monedero){
        if (puntosTotales >= beneficio.getPuntosNecesarios()) {
            puntosTotales -= beneficio.getPuntosNecesarios();
            switch (beneficio) {
                case DESCUENTO:
                    this.descuentoTransferencia = true;
                    System.out.println("Has canjeado un 10% de descuento en comisiones por transferencias.");
                    break;

                case RETIROSGRATIS:
                    this.retirosGratis = true;
                    this.fechaFinRetirosGratis = LocalDate.now().plusMonths(1);
                    System.out.println("Tienes un mes sin cargos por retiros.");
                    break;

                case BONOSALDO:
                    monedero.depositar(50000);
                    System.out.println("Se acreditó un bono de 50000 a tu monedero.");
                    break;
            }
            actualizarRango();
        } else {
            System.out.println("No tienes suficientes puntos para este beneficio.");
        }

    }
}
