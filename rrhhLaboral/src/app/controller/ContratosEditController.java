package app.controller;


import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

import app.model.TipoContrato;
import app.util.DataBase;

public class ContratosEditController {

	@FXML
	private TextField idContratoField;
	@FXML
	private TextField nombreField;
	@FXML
	private TextField codContratoField;

    private Stage dialogStage;
    private TipoContrato contrato;
    private boolean okClicked = false;
    private DataBase bbdd;


    @FXML
    private void initialize() {

    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setContrato(TipoContrato contrato, DataBase bbdd) throws SQLException {
    	this.bbdd = bbdd;
        this.contrato = contrato;
    	idContratoField.setText(Integer.toString(contrato.getIdTipoContrato()));
    	nombreField.setText(contrato.getNombre());
    	codContratoField.setText(contrato.getCodContrato());
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

        	contrato.setIdTipoContrato(((Integer.parseInt(idContratoField.getText()))));
        	contrato.setNombre(nombreField.getText());
        	contrato.setCodContrato(codContratoField.getText());
        	okClicked = true;
            bbdd.actualizarContrato(contrato, contrato.getIdTipoContrato());
            dialogStage.close();
        }
    }

    @FXML
    private void nuevoOk() throws SQLException {
        if (isInputValid()) {

        	contrato.setIdTipoContrato((Integer.parseInt(idContratoField.getText())));
        	contrato.setNombre(nombreField.getText());
        	contrato.setCodContrato((codContratoField.getText()));
        	okClicked = true;
            bbdd.insertarContrato(contrato);
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
