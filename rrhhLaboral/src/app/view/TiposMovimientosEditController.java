package app.view;


import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

import org.jdesktop.swingx.tips.TipOfTheDayModel.Tip;

import app.model.Categoria;
import app.model.TipoMovimiento;
import app.util.DataBase;

public class TiposMovimientosEditController {

	@FXML
	private TextField idTipoMovimientoField;
	@FXML
	private TextField nombreField;

    private Stage dialogStage;
    private TipoMovimiento tipoMovimiento;
    private boolean okClicked = false;
    private DataBase bbdd;


    @FXML
    private void initialize() {

    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setMovimiento(TipoMovimiento tipoMovimiento, DataBase bbdd) throws SQLException {
    	this.bbdd = bbdd;
        this.tipoMovimiento = tipoMovimiento;
    	idTipoMovimientoField.setText(Integer.toString(tipoMovimiento.getIdTipoMovimiento()));
    	nombreField.setText(tipoMovimiento.getNombre());
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

        	tipoMovimiento.setIdTipoMovimiento((Integer.parseInt(idTipoMovimientoField.getText())));
        	tipoMovimiento.setNombre(nombreField.getText());
        	okClicked = true;
            bbdd.actualizarTipoMovimiento(tipoMovimiento, tipoMovimiento.getIdTipoMovimiento());
            dialogStage.close();
        }
    }

    @FXML
    private void nuevoOk() throws SQLException {
        if (isInputValid()) {

        	tipoMovimiento.setIdTipoMovimiento((Integer.parseInt(idTipoMovimientoField.getText())));
        	tipoMovimiento.setNombre(nombreField.getText());
        	okClicked = true;
            bbdd.insertarTipoMovimiento(tipoMovimiento);
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
