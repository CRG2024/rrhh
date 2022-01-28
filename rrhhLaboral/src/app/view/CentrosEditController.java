package app.view;


import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

import app.model.Centro;
import app.util.DataBase;

public class CentrosEditController {

	@FXML
	private TextField idCentroField;
	@FXML
	private TextField nombreField;
	@FXML
	private TextField direccionField;
	@FXML
	private TextField cpField;
	@FXML
	private TextField ciudadField;


    private Stage dialogStage;
    private Centro centro;
    private boolean okClicked = false;
    private DataBase bbdd;


    @FXML
    private void initialize() {

    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setCentro(Centro centro, DataBase bbdd) throws SQLException {
    	this.bbdd = bbdd;
        this.centro = centro;
    	idCentroField.setText(Integer.toString(centro.getIdCentro()));
    	nombreField.setText(centro.getNombre());
    	direccionField.setText(centro.getDireccion());
    	cpField.setText(Integer.toString(centro.getCp()));
    	ciudadField.setText(centro.getCiudad());
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

        	centro.setIdCentro(Integer.parseInt(idCentroField.getText()));
        	centro.setNombre(nombreField.getText());
        	centro.setDireccion(direccionField.getText());
        	centro.setCp(Integer.parseInt(cpField.getText()));
        	centro.setCiudad(ciudadField.getText());
        	okClicked = true;
            bbdd.actualizarCentro(centro, centro.getIdCentro());
            dialogStage.close();
        }
    }

    @FXML
    private void nuevoOk() throws SQLException {
        if (isInputValid()) {

        	centro.setIdCentro(Integer.parseInt(idCentroField.getText()));
        	centro.setNombre(nombreField.getText());
        	centro.setDireccion(direccionField.getText());
        	centro.setCp(Integer.parseInt(cpField.getText()));
        	centro.setCiudad(ciudadField.getText());
        	okClicked = true;
            bbdd.insertarCentro(centro);
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
