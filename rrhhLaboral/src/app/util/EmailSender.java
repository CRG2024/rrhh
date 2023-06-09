package app.util;

import com.itextpdf.text.Document;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.List;
import java.util.Properties;

import javax.mail.Session;

public class EmailSender {

    private Properties properties;
    private Session session;
    private BodyPart texto;
    private String sender = "grupoasbenicasim@gmail.com";
    private String pass = "hgbdjvycisorftwj";

    public EmailSender(){

        properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.user", sender);
        properties.put("mail.smtp.password", pass);
        properties.put("mail.smtp.starttls.enable", "true");

        session = Session.getInstance(properties, new Authenticator()
        {
            @Override
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(properties.getProperty("mail.smtp.user"),
                        properties.getProperty("mail.smtp.password"));
            }
        });


    }

    public void mandarCorreo(String contenido, String titulo, String dest,String archivo) throws MessagingException {

        try {


            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(sender));

            InternetAddress addressTo = new InternetAddress(dest);
            message.setRecipient(Message.RecipientType.TO, addressTo);
            message.setSubject(titulo);

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(contenido);

            Multipart multipart = new MimeMultipart();

            multipart.addBodyPart(messageBodyPart);

            if(archivo!=null){
                messageBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(archivo);
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(archivo);
                multipart.addBodyPart(messageBodyPart);
            }
            message.setContent(multipart);

            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    //TODO ver si sirve de algo
    public void mensajesSolicitudPendiente(String emailPropietario, String emailCliente) throws MessagingException {
        EmailThread h1 = new EmailThread("Tiene una solicitud pendiente en una de sus ofertas. Entre en  la aplicación para Aceptar o Rechazarla",
                "SOLICITUD PENDIENTE",emailPropietario,null);
        EmailThread h2= new EmailThread("Se ha enviado un correo al propietario de la plaza para que pueda tramitar su solcitud",
                "SOLICITUD PENDIENTE",emailCliente,null);
        h1.start();
        h2.start();
    }

    public void mandarCorreoVariosArchivos(List<String> documentos, String correoTrabajador) {
        try {


            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(sender));

            InternetAddress addressTo = new InternetAddress(correoTrabajador);
            message.setRecipient(Message.RecipientType.TO, addressTo);
            message.setSubject("DOCUMENTACION VILLA SOFIA");

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Buenos días.\n Adjuntamos documentación pendiente de firma y" +
                    " que solicitamos nos envíe lo antes posible. En caso de no poder devolverla firmada" +
                    "electrónicamente rogamos pase por las oficinas a firmarla en persona.\n Un saludo.");

            Multipart multipart = new MimeMultipart();

            multipart.addBodyPart(messageBodyPart);

            for (String doc:documentos) {
                messageBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource("src/LlamamientosDoc/"+doc);
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(doc);
                multipart.addBodyPart(messageBodyPart);
            }
            message.setContent(multipart);

            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
