package app.util;

import javax.mail.MessagingException;

public class EmailThread extends Thread{
    Thread hilo;
    private String contenido;
    private String titulo;
    private String dest;
    private String archivo;

    public EmailThread(String contenido, String titulo, String dest, String archivo){
        this.contenido = contenido;
        this.titulo = titulo;
        this.dest = dest;
        this.archivo = archivo;
    }

    //Punto de entrada de hilo.
    public void run(){
        EmailSender email = new EmailSender();
        try {
            email.mandarCorreo(contenido,titulo,dest,archivo);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
