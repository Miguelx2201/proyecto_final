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
    private String contrasena;

    public Cliente(String nombre, String cedula, String direccion, String correo,int edad, String contrasena) {
        if(nombre.isBlank()||cedula.isBlank()||direccion.isBlank()||correo.isBlank()) {
            throw  new IllegalArgumentException("Llene los datos correctamente");
        }
        this.nombre = nombre;
        this.cedula = cedula;
        this.direccion = direccion;
        this.correo = correo;
        this.edad = edad;
        this.fechaFinRetirosGratis=null;
        this.descuentoTransferencia = false;
        this.retirosGratis = false;
        this.listaMonederos = new ArrayList<>();
        this.puntosTotales = calcularPuntos();
        this.rango=RangoCliente.BRONCE;
        this.contrasena = contrasena;
    }

    public String getContraseña() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
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

    public void setListaMonederos(List<Monedero> listaMonederos) {
        this.listaMonederos = listaMonederos;
    }

    public boolean isDescuentoTransferencia() {
        return descuentoTransferencia;
    }

    public void setDescuentoTransferencia(boolean descuentoTransferencia) {
        this.descuentoTransferencia = descuentoTransferencia;
    }

    public boolean isRetirosGratis() {
        return retirosGratis;
    }

    public void setRetirosGratis(boolean retirosGratis) {
        this.retirosGratis = retirosGratis;
    }

    public LocalDate getFechaFinRetirosGratis() {
        return fechaFinRetirosGratis;
    }

    public void setFechaFinRetirosGratis(LocalDate fechaFinRetirosGratis) {
        this.fechaFinRetirosGratis = fechaFinRetirosGratis;
    }

    @Override
    public int calcularPuntos(){
        int total=0;
        for(Monedero m: listaMonederos){
            total+=m.getPuntos();
        }
        this.puntosTotales=total;
        actualizarRango();
        return puntosTotales;
    }

    public void actualizarRango(){
        if(puntosTotales<=500){
            rango=RangoCliente.BRONCE;
        }else if(puntosTotales>500&&puntosTotales<=1000){
            rango=RangoCliente.PLATA;
        }else if(puntosTotales>1000&&puntosTotales<=5000){
            rango=RangoCliente.ORO;
        }else{
            rango=RangoCliente.PLATINO;
        }
    }

    public void consultarSaldo(){
        double saldoTotal=0;
        for(Monedero m: listaMonederos){
            saldoTotal+=m.getSaldo();
        }
        System.out.println("Su saldo total es de: "+saldoTotal);
    }


    @Override
    public void enviarNotificacion(String mensaje) {
        System.out.println("🔔 Notificación para " + nombre + ": " + mensaje);
        //Whatsapp.enviarWhatsapp(mensaje); //Puesto en comentarios para no gastar los mensajes de twilio
        Gmail.enviarGmail(mensaje, this.correo); // Este metodo deberia recibir como argumento tambien el correo del cliente y a
        // ese mismo correo enviar el gmail, sin embargo para mayor simplicidad dejamos un correo propio ya
        // preestablecido, el cual podemos cambiar si asi lo deseamos.
    }

    public boolean canjearBeneficio(Beneficio beneficio){
        if (puntosTotales >= beneficio.getPuntosNecesarios()) {
            puntosTotales -= beneficio.getPuntosNecesarios();
            switch (beneficio) {
                case DESCUENTO:
                    this.descuentoTransferencia = true;
                    System.out.println("Has canjeado un 10% de descuento en comisiones por transferencias.");
                    return true;


                case RETIROSGRATIS:
                    this.retirosGratis = true;
                    this.fechaFinRetirosGratis = LocalDate.now().plusMonths(1);
                    System.out.println("Tienes un mes sin cargos por retiros.");
                    return true;


                case BONOSALDO:
                    listaMonederos.getFirst().depositar(50000);
                    System.out.println("Se acreditó un bono de 50000 a tu monedero.");
                    return true;

            }
            actualizarRango();
        } else {
            System.out.println("No tienes suficientes puntos para este beneficio.");
            return false;
        }

        return false;
    }
}
