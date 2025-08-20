package app.validators;

import javafx.application.Platform;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidadorCampos {
	private ContextMenu campoValidador;
	public ValidadorCampos(){
		campoValidador = new ContextMenu();
		campoValidador.setAutoHide(false);
		campoValidador.getItems().clear();
	}

	public ContextMenu getValidador(){
		return campoValidador;
	}

	public MenuItem crearMenuItem(String mensaje){

		MenuItem menuItem = new MenuItem(mensaje);
		return  menuItem;
	}
	public boolean validarCampoVacío(TextField textField){

		if(textField.getText() ==  null || textField.getText().replaceAll("\\s", "").equals("")){
			campoValidador.getItems().add(crearMenuItem("Este campo no puede estar vacío."));
			campoValidador.show(textField, Side.BOTTOM, 10, 0);
			hideCampoValidador(campoValidador);
			return false;
		}
		return true;
	}

	public void mostrarMensajeError(TextField textField, String mensaje){
		campoValidador.getItems().add(crearMenuItem(mensaje));
		campoValidador.show(textField, Side.BOTTOM, 10, 0);
		hideCampoValidador(campoValidador);
	}

	public void mostrarMensajeErrorFecha(DatePicker date, String mensaje){
		campoValidador.getItems().add(crearMenuItem(mensaje));
		campoValidador.show(date, Side.BOTTOM, 10, 0);
		hideCampoValidador(campoValidador);
	}

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

	public boolean validarDni(TextField dniField){
		if (!validarCampoVacío(dniField)){
			return false;
		}
		if (formatoDni(dniField.getText()) ){
			return true;
		}
		if (validarNIE(dniField.getText()) ){
			return true;
		}
		mostrarMensajeError(dniField,"El formato es incorrecto. Revise que DNI o el NIE introducido.");
		return false;
	}
	public boolean formatoDni(String dni) {
		String letraMayuscula = "";
		if(dni.length() != 9 || Character.isLetter(dni.charAt(8)) == false ) {
			return false;
		}
		letraMayuscula = (dni.substring(8)).toUpperCase();
		String numerosDni = dni.substring(0,8);
		if(soloNumeros(numerosDni) == true && letraDNI(dni).equals(letraMayuscula)) {
			return true;
		}
		return false;
	}

	public boolean soloNumeros(String texto) {
		String[] unoNueve = {"0","1","2","3","4","5","6","7","8","9"};
		List<String> listaNumeros = Arrays.asList(unoNueve);
		String texto_sin_espacios = texto.replaceAll("\\s","");
		String[] textoNum = texto_sin_espacios.split("");

		for(String numero:textoNum){
			if (!listaNumeros.contains(numero)){
				return false;
			}
		}
		return true;
	}

	private String letraDNI(String dni) {
		int miDNI = Integer.parseInt(dni.substring(0,8));
		int resto = 0;
		String miLetra = "";
		String[] asignacionLetra = {"T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E"};
		resto = miDNI % 23;
		miLetra = asignacionLetra[resto];
		return miLetra;
	}

	public boolean validarNIE(String nie) {

		if(nie.length() != 9 ) {
			return false;
		}
		if(!Character.isLetter(nie.charAt(8)) ) {
			return false;
		}
		if(!Character.isLetter(nie.charAt(0)) ) {
			return false;
		}
		if(!soloNumeros(nie.substring(1,8)) ) {
			return false;
		}
		return true;
	}

	public boolean validarSoloLetras(TextField textoField) {

		if (!validarCampoVacío(textoField)){
			return false;
		}
		String cadena = textoField.getText();
		for (int x = 0; x < cadena.length(); x++) {
			char c = cadena.charAt(x);
			// Si no está entre a y z, ni entre A y Z, ni es un espacio, ni tiene acento, ni es ñ o Ñ
			if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')
					|| c == ' ' || c == '-'
					|| (c >= 'á' && c <= 'ú') || (c >= 'Á' && c <= 'Ú')
					|| c == 'ñ' || c == 'Ñ')) {
				mostrarMensajeError(textoField, "El formato es incorrecto. Sólo puede tener letras, espacios, -, y tildes.");
				return false;
			}
		}
		return true;
	}

	public boolean validarSoloNumeros(TextField textoField) {
		if (!validarCampoVacío(textoField)){
			return false;
		}
		String texto = textoField.getText();
		if(!soloNumeros(texto.substring(0,texto.length()))) {
			mostrarMensajeError(textoField,"El campo sólo puede contener números.");
			return false;
		}
		return true;
	}
	public boolean validarCuentaBancaria(TextField field) {
		if (!validarCampoVacío(field)){
			return false;
		}
		mostrarMensajeError(field,"Advertencia: Confirm que la cuenta es correcta antes de aceptar.");
		return true;
	}

	public boolean validarEmail(TextField email) {
		if (!validarCampoVacío(email)){
			return false;
		}
		Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
						+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher mather = pattern.matcher(email.getText());

		if (!mather.find()) {
			mostrarMensajeError(email, "El formato del email no es correcto");
			return false;
		}
		return true;
	}

	public boolean validarFecha(DatePicker fecha) {
		if(fecha.getValue() == null){
			mostrarMensajeErrorFecha(fecha, "Hay que seleccionar una fecha válida.");
			return false;
		}
		String date = fecha.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String dateFormat = "dd/MM/yyyy";
		DateFormat df = new SimpleDateFormat(dateFormat);
		df.setLenient(false);
		try {
			df.parse(date);
		} catch (ParseException e) {
			mostrarMensajeErrorFecha(fecha, "El formato fecha no es correcto: dd/MM/yyyy");
			return false;
		}

		return true;
	}
}
