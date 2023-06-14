package app.view;


import app.model.*;
import app.util.ComboBoxAutoComplete;
import app.util.DataBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class LlamamientosEditController {

	@FXML
	private ComboBox tipoMovimientoSelect;
	@FXML
	private ComboBox dniSelect;
    @FXML
    private TextField nombreField;
    @FXML
    private ComboBox centroSelect;
    @FXML
    private ComboBox categoriaSelect;
    @FXML
    private ComboBox tipoContratoSelect;
    @FXML
    private ComboBox horarioSelect;
    @FXML
    private DatePicker fechaInicioDatePicker;
    @FXML
    private DatePicker fechaFinDatePicker;
    @FXML
    private TextField importeBajaField;

    private Stage dialogStage;
    private Movimiento movimiento;
    private boolean okClicked = false;
    private DataBase bbdd;


	private ObservableList<String> dniTrabajadores = FXCollections.observableArrayList();
    private ObservableList<String> nombresMovimientos = FXCollections.observableArrayList();
    private ObservableList<String> nombresCentro = FXCollections.observableArrayList();
    private ObservableList<String> nombresCategoria = FXCollections.observableArrayList();
    private ObservableList<String> nombresTipoContrato = FXCollections.observableArrayList();
    private ObservableList<String> nombresHorarios = FXCollections.observableArrayList();

    @FXML
    private void initialize() throws SQLException {
        bbdd = new DataBase();
        actualizarListasSelect();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    public void setMovimiento(Movimiento movimiento, DataBase bbdd) throws SQLException {
    	this.bbdd = bbdd;
        this.movimiento = movimiento;

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

        	/*movimiento.setDni("");
        	movimiento.setNombreTipoMovimiento(tipoMovimientoField.getText());*/

            okClicked = true;
            bbdd.actualizarMovimiento(movimiento, movimiento.getIdMovimiento());
            dialogStage.close();
        }
    }

    @FXML
    private void nuevoOk() throws SQLException {
        if (isInputValid()) {

            okClicked = true;
            bbdd.insertarMovimiento(movimiento);
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

    private void actualizarListasSelect() throws SQLException {
        //DNIS
        ObservableList<Trabajador> trabajadores = FXCollections.observableArrayList();
        trabajadores = bbdd.obtenerDatosTrabajadores();
        for(Trabajador trabajador: trabajadores){
            dniTrabajadores.add(trabajador.getDni());
        }
        dniSelect.setItems(dniTrabajadores);
        new ComboBoxAutoComplete<String>(dniSelect);

        //TIPOS MOVIMIENTOS
        ObservableList<TipoMovimiento> tipoMovimientos = FXCollections.observableArrayList();
        tipoMovimientos = bbdd.obtenerDatosTipoMovimientos();
        for(TipoMovimiento tipoMovimiento: tipoMovimientos){
            nombresMovimientos.add(tipoMovimiento.getNombre());
        }
        tipoMovimientoSelect.setItems(nombresMovimientos);
        new ComboBoxAutoComplete<String>(tipoMovimientoSelect);

        //CENTROS
        ObservableList<Centro> centros = FXCollections.observableArrayList();
        centros = bbdd.obtenerDatosCentros();
        for(Centro centro: centros){
            nombresCentro.add(centro.getNombre());
        }
        centroSelect.setItems(nombresCentro);
        new ComboBoxAutoComplete<String>(centroSelect);

        //CATEGORIAS
        ObservableList<Categoria> categorias = FXCollections.observableArrayList();
        categorias = bbdd.obtenerDatosCategorias();
        for(Categoria categoria: categorias){
            nombresCategoria.add(categoria.getNombre());
        }
        categoriaSelect.setItems(nombresCategoria);
        new ComboBoxAutoComplete<String>(categoriaSelect);

        //TIPOS CONTRATO
        ObservableList<TipoContrato> tipoContratos = FXCollections.observableArrayList();
        tipoContratos = bbdd.obtenerDatosContratos();
        for(TipoContrato tipoContrato: tipoContratos){
            nombresTipoContrato.add(tipoContrato.getNombre());
        }
        tipoContratoSelect.setItems(nombresTipoContrato);
        new ComboBoxAutoComplete<String>(tipoContratoSelect);

        //HORARIOS
        ObservableList<Horario> horarios = FXCollections.observableArrayList();
        horarios = bbdd.obtenerDatosHorarios();
        for(Horario horario: horarios){
            nombresHorarios.add(horario.getNombre());
        }
        horarioSelect.setItems(nombresHorarios);
        new ComboBoxAutoComplete<String>(horarioSelect);
    }

}
