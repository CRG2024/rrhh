package app.validators;

import java.util.List;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.TextField;
import javafx.application.Platform;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DataValidator {

    private ContextMenu campoValidador;
    private List<TextField> campos;

    public DataValidator(List<TextField> campos){
        this.campos = campos;
        campoValidador = new ContextMenu();
        campoValidador.setAutoHide(false);
        final ContextMenu passValidator = new ContextMenu();
        passValidator.setAutoHide(false);
        campoValidador.getItems().clear();
    }

    public ContextMenu getValidador(){
        return campoValidador;
    }

    public MenuItem crearMenuItem(String mensaje){

        MenuItem menuItem = new MenuItem(mensaje);
        return  menuItem;
    }

    public List<TextField> getCampos(){
        return campos;
    }

    /**
     * Valida los campos obligatorios
     * @return devuelve true si el campo es válido
     */
    public boolean validarCamposVacios(){
        for (TextField campo: campos) {
            if(!mensajeValidarCampoVacío(campo))return false;
        }
        return true;
    }
    /**
     * Valida que el campo no esté vacío
     * @param  textField  campo a validar
     * @return  false si está vacío.
     */
    private boolean mensajeValidarCampoVacío(TextField textField){
        if(textField.getText().equals("")){
            campoValidador.getItems().add(crearMenuItem("Este campo no puede estar vacío."));
            campoValidador.show(textField, Side.BOTTOM, 10, 0);
            hideCampoValidador(campoValidador);
            return false;
        }
        return true;
    }


    /*
     * Muestra un mensaje de error en el campo elegido con el mensaje proporcionado
     * @param  textField  campo
     * @param  mensaje  texto de error
     * @param  x  posición x del mensaje
     * @param  y  posición y del mensaje
     */

    public void mostrarMensajeError(TextField textField, String mensaje){
        campoValidador.getItems().add(crearMenuItem(mensaje));
        campoValidador.show(textField, Side.BOTTOM, 10, 0);
        hideCampoValidador(campoValidador);
    }

    public boolean comprobarRepetirPass(PasswordField pass, PasswordField repeatPass){

        if(!pass.getText().equals(repeatPass.getText())){
            mostrarMensajeError(repeatPass, "Las contraseñas no coinciden");
            return false;
        }
        return true;
    }

    public boolean comprobarFormatoIp(TextField ipField){

        String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
        if(ipField.getText().matches(PATTERN)){
            mostrarMensajeError(ipField, "El formato de la ip no es el adecuado");
            return false;
        }
        return true;
    }

    public boolean comprobarPreguntaRespuestasNoVacias(TextField pregunta){
        if(!pregunta.getText().equals("")){
            if(!validarCamposVacios()){
                return false;
            }
        }
        return true;
    }

    public boolean comprobarCamposQuizObligados(){
        if(validarCamposVacios()){
            if(comprobarTiempoYPuntuacion()){
                return true;
            }
        }

        return false;
    }

    public boolean comprobarTiempoYPuntuacion(){
        for (TextField textField : campos) {
            if(textField.getId().equals("timeLabel")&& isInteger(textField.getText())){
                if(Integer.parseInt(textField.getText())< 10){
                    mostrarMensajeError(textField, "El tiempo mínimo es de 10 segundos");
                    return false;
                }
            }

            if(textField.getId().equals("pointsLabel")){
                if(!isInteger(textField.getText())){
                    mostrarMensajeError(textField, "La puntuación debe ser un número entero, ya sea positivo o negativo");
                    return false;
                }
            }
        }

        return true;
    }

    public boolean comprobarEmailFormato(TextField mailField){
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

        if(!mailField.getText().matches(regex)){
            mostrarMensajeError(mailField, "El correo no tiene un formato válido.");
            return false;
        }
        return true;
    }

    public void mailInexistente(TextField mailField){
        mostrarMensajeError(mailField, "El correo no existe en la base de datos.");
    }


    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        return true;
    }
    /**
     *Esconde el campo pasado 1,5 segundos
     */
    private void hideCampoValidador(ContextMenu campoValidadorMostrado){

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        campoValidadorMostrado.hide();
                    }
                });
            }
        }, 1500);
    }
}