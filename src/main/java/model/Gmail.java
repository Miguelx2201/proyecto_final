package model;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Transport;
import jakarta.mail.Session;
import jakarta.mail.Authenticator;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class Gmail {

    public static void enviarGmail(String mensaje) {

        // Datos de tu cuenta Gmail
        final String correo = "juanm.gutierrezv@uqvirtual.edu.co";
        final String appPassword = "xatj mdlk upud bvgn";

        // Configuración del servidor SMTP de Gmail
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Crear la sesión con autenticación
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(correo, appPassword);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(correo));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("miguel1091885531@gmail.com") // Aqui es donde cambiariamos el correo por
                    // el correo del destinatario, si quisieramos mayor profesionalismo el metodo recibiria como
                    // parametro un correo, el del cliente, al cual enviariamos el correo asi que en este sitio es
                    // donde pondriamos el correo que recibimos como parametro
            );
            message.setSubject("Notificación banco UQ");
            message.setText(mensaje);


            Transport.send(message);
            System.out.println("¡Correo enviado con éxito!");

        } catch (Exception e) {
            System.out.println("Error al enviar el correo:");
            e.printStackTrace();
        }
    }
}
