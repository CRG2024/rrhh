package app.controller;


import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

import app.model.Categoria;
import app.util.DataBase;

public class CategoriasEditController {

	@FXML
	private TextField idCategoriaField;
	@FXML
	private TextField nombreField;
	@FXML
	private TextArea descripcionField;

    private Stage dialogStage;
    private Categoria categoria;
    private boolean okClicked = false;
    private DataBase bbdd;


    @FXML
    private void initialize() {

    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setCategoria(Categoria categoria, DataBase bbdd) throws SQLException {
    	this.bbdd = bbdd;
        this.categoria = categoria;
    	idCategoriaField.setText(Integer.toString(categoria.getIdCategoria()));
    	nombreField.setText(categoria.getNombre());
    	descripcionField.setText(categoria.getDescripcion());
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

        	categoria.setIdCategoria((Integer.parseInt(idCategoriaField.getText())));
        	categoria.setNombre(nombreField.getText());
        	categoria.setDescripcion(descripcionField.getText());
        	okClicked = true;
            bbdd.actualizarCategoria(categoria, categoria.getIdCategoria());
            dialogStage.close();
        }
    }

    @FXML
    private void nuevoOk() throws SQLException {
        if (isInputValid()) {

        	categoria.setIdCategoria((Integer.parseInt(idCategoriaField.getText())));
        	categoria.setNombre(nombreField.getText());
        	categoria.setDescripcion(descripcionField.getText());
        	okClicked = true;
            bbdd.insertarCategoria(categoria);
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
