package app.view;


import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

import app.model.Trabajador;
import app.util.DataBase;

public class WorkersEditController {

	@FXML
	private TextField dniField;
	@FXML
	private TextField nombreField;
	@FXML
	private TextField apellido1Field;
	@FXML
	private TextField apellido2Field;
	@FXML
	private TextField fechaNacimientoField;

	@FXML
	private DatePicker fechaNacimientoDatePicker;
	@FXML
	private TextField nacionalidadField;
	@FXML
	private TextField domicilioField;
	@FXML
	private TextField ciudadField;
	@FXML
	private TextField poblacionField;
	@FXML
	private TextField cpField;
	@FXML
	private TextField nssField;
	@FXML
	private TextField emailField;
	@FXML
	private TextField telefono1Field;
	@FXML
	private TextField telefono2Field;
	@FXML
	private TextField cuentaField;
	@FXML
	private TextField carnetField;
	@FXML
	private CheckBox carnetCheckbox;

	@FXML
	private TextField vehiculoField;
	@FXML
	private CheckBox vehiculoCheckbox;
	@FXML
	private TextField permisoTrabajoField;
	@FXML
	private CheckBox permisoTrabajoCheckbox;

	@FXML
	private TextField discapacidadesField;
	@FXML
	private CheckBox discapacidadesCheckbox;

    private Stage dialogStage;
    private Trabajador worker;
    private boolean okClicked = false;
    private DataBase bbdd;


    @FXML
    private void initialize() {

    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setPerson(Trabajador worker, DataBase bbdd) throws SQLException {
    	this.bbdd = bbdd;
        this.worker = worker;
    	dniField.setText(worker.getDni());
    	nombreField.setText(worker.getNombre());
    	apellido1Field.setText(worker.getApellido1());
    	apellido2Field.setText(worker.getApellido2());
    	fechaNacimientoDatePicker.setValue(worker.getFechaNacimiento());
    	nacionalidadField.setText(worker.getNacionalidad());
    	domicilioField.setText(worker.getDomicilio());
    	ciudadField.setText(worker.getCiudad());
    	poblacionField.setText(worker.getPoblacion());
    	cpField.setText(Integer.toString(worker.getCp()));
    	nssField.setText(worker.getnss());
    	emailField.setText(worker.getEmail());
    	telefono1Field.setText(worker.getTelefono1());
    	telefono2Field.setText(worker.getTelefono2());
    	cuentaField.setText(worker.getCuenta());


    	if(worker.getCarnet()==null){
    		worker.setCarnet("0");
    		carnetCheckbox.setSelected(false);
    	}
    	if(Integer.parseInt(worker.getCarnet())==1){
    		carnetCheckbox.setSelected(true);
    	}




    	if(worker.getVehiculo()==null){
    		worker.setVehiculo("0");
    		vehiculoCheckbox.setSelected(false);
    	}
    	if(Integer.parseInt(worker.getVehiculo())==1){
    		vehiculoCheckbox.setSelected(true);
    	}




    	if(worker.getPermisoTrabajo()==null){
    		worker.setPermisoTrabajo("0");
    		permisoTrabajoCheckbox.setSelected(false);
    	}
    	if(Integer.parseInt(worker.getPermisoTrabajo())==1){
    		permisoTrabajoCheckbox.setSelected(true);
    	}



    	if(worker.getDiscapacidades()==null){
    		worker.setDiscapacidades("0");
    		discapacidadesCheckbox.setSelected(false);
    	}
    	if(Integer.parseInt(worker.getDiscapacidades())==1){
    		discapacidadesCheckbox.setSelected(true);
    	}
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     * @throws SQLException
     */
    @FXML
    private void handleOk() throws SQLException {
        if (isInputValid()) {

        	worker.setDni(dniField.getText());
        	worker.setNombre(nombreField.getText());
        	worker.setApellido1(apellido1Field.getText());
        	worker.setApellido2(apellido2Field.getText());
        	worker.setFechaNacimiento(fechaNacimientoDatePicker.getValue());
        	worker.setNacionalidad(nacionalidadField.getText());
        	worker.setDomicilio(domicilioField.getText());
        	worker.setCiudad(ciudadField.getText());
        	worker.setPoblacion(poblacionField.getText());
        	worker.setCp(Integer.parseInt(cpField.getText()));
        	worker.setnss(nssField.getText());
        	worker.setEmail(emailField.getText());
        	worker.setTelefono1(telefono1Field.getText());
        	worker.setTelefono2(telefono2Field.getText());
        	worker.setCuenta(cuentaField.getText());
        	if(carnetCheckbox.isSelected()){
        		worker.setCarnet("1");
        	}else{
        		worker.setCarnet("0");
        	}
        	if(vehiculoCheckbox.isSelected()){
        		worker.setVehiculo("1");
        	}else{
        		worker.setVehiculo("0");
        	}
        	if(permisoTrabajoCheckbox.isSelected()){
        		worker.setPermisoTrabajo("1");
        	}else{
        		worker.setPermisoTrabajo("0");
        	}
        	if(discapacidadesCheckbox.isSelected()){
        		worker.setDiscapacidades("1");
        	}else{
        		worker.setDiscapacidades("0");
        	}

            okClicked = true;
            bbdd.actualizarTrabajador(worker, worker.getDni());
            dialogStage.close();
        }
    }

    @FXML
    private void nuevoOk() throws SQLException {
        if (isInputValid()) {

        	worker.setDni(dniField.getText());
        	worker.setNombre(nombreField.getText());
        	worker.setApellido1(apellido1Field.getText());
        	worker.setApellido2(apellido2Field.getText());
        	worker.setFechaNacimiento(fechaNacimientoDatePicker.getValue());
        	worker.setNacionalidad(nacionalidadField.getText());
        	worker.setDomicilio(domicilioField.getText());
        	worker.setCiudad(ciudadField.getText());
        	worker.setPoblacion(poblacionField.getText());
        	worker.setCp(Integer.parseInt(cpField.getText()));
        	worker.setnss(nssField.getText());
        	worker.setEmail(emailField.getText());
        	worker.setTelefono1(telefono1Field.getText());
        	worker.setTelefono2(telefono2Field.getText());
        	worker.setCuenta(cuentaField.getText());
        	if(carnetCheckbox.isSelected()){
        		worker.setCarnet("1");
        	}else{
        		worker.setCarnet("0");
        	}
        	if(vehiculoCheckbox.isSelected()){
        		worker.setVehiculo("1");
        	}else{
        		worker.setVehiculo("0");
        	}
        	if(permisoTrabajoCheckbox.isSelected()){
        		worker.setPermisoTrabajo("1");
        	}else{
        		worker.setPermisoTrabajo("0");
        	}
        	if(discapacidadesCheckbox.isSelected()){
        		worker.setDiscapacidades("1");
        	}else{
        		worker.setDiscapacidades("0");
        	}

            okClicked = true;
            bbdd.insertarTrabajador(worker);
            dialogStage.close();
        }
    }
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {

    	return true;
    }
}
