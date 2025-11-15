package model;

import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.net.URI;
import java.math.BigDecimal;

public class Whatsapp {
    // Find your Account Sid and Token at twilio.com/console
    public static final String ACCOUNT_SID = System.getenv("TWILIO_SID");
    public static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");
    //El SID y el Token estan asignados a una variable de entorno debido a que al ser estos datos secretos no se podian
    // subir al repositorio, para hacerlo debemos crear las variables de entorno con los datos

    public static void enviarWhatsapp(String mensaje) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("whatsapp:+573203785092"),
                new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                mensaje
                )
                        .create();

        System.out.println(message.getSid());
    }
}