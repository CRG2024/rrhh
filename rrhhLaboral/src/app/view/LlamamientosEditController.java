package app.view;


import app.Main;
import app.model.*;
import app.util.ComboBoxAutoComplete;
import app.util.DataBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
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
    private ObservableList<Movimiento> movimientos = FXCollections.observableArrayList();;

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
            crearMovimiento();
            okClicked = true;
            //bbdd.actualizarMovimiento(movimiento, movimiento.getIdMovimiento());
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

    @FXML
    public boolean showNewCategoria() throws SQLException {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/CategoriasCreateView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Categoria categoria = new Categoria();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Crear Categoria");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            CategoriasEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setCategoria(categoria, bbdd);

            dialogStage.showAndWait();
            actualizarListasSelect();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    @FXML
    public boolean showNewHorario() throws SQLException {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/HorariosCreateView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Horario horario = new Horario();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Crear Horario");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            HorariosEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setHorario(horario, bbdd);

            dialogStage.showAndWait();
            actualizarListasSelect();


            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    @FXML
    public boolean showNewCentro() throws SQLException {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/CentroCreateView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Centro centro = new Centro();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Centro");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            CentrosEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setCentro(centro, bbdd);

            dialogStage.showAndWait();
            actualizarListasSelect();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    @FXML
    public boolean showNewContrato() throws SQLException {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/ContratosCreateView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            TipoContrato contrato = new TipoContrato();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Contrato");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            ContratosEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setContrato(contrato, bbdd);

            dialogStage.showAndWait();
            actualizarListasSelect();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    @FXML
    public boolean showNewTrabajador() throws SQLException {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/WorkerCreateView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Trabajador trabajador = new Trabajador();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            WorkersEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(trabajador, bbdd);

            dialogStage.showAndWait();
            actualizarListasSelect();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    @FXML
    public boolean showNewTipoMovimiento() throws SQLException {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/TipoMovimientoCreateView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            TipoMovimiento tipoMovimiento = new TipoMovimiento();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Crear Movimiento");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            TiposMovimientosEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMovimiento(tipoMovimiento, bbdd);

            dialogStage.showAndWait();
            actualizarListasSelect();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean crearMovimiento() throws SQLException {
        //Crear movimiento
        Movimiento mov = new Movimiento(
                0,
                bbdd.obtenerTrabajador(dniSelect.getValue().toString()),
                bbdd.obtenerCentroPorNombre(centroSelect.getValue().toString()),
                bbdd.obtenerHorarioPorNombre(horarioSelect.getValue().toString()),
                fechaInicioDatePicker.getValue(),
                fechaFinDatePicker.getValue(),
                Integer.parseInt(importeBajaField.getText()),
                bbdd.obtenerCategoriaPorNombre(categoriaSelect.getValue().toString()),
                bbdd.obtenerTipoContratoPorNombre(tipoContratoSelect.getValue().toString()),
                bbdd.obtenerTipoMovimientoPorNombre(tipoMovimientoSelect.getValue().toString())
                );
        movimientos.add(mov);

        return false;

    }

    public void setListaMovimientos(ObservableList<Movimiento> movimientosPrueba) {
        this.movimientos = movimientosPrueba;
    }

    public ObservableList<Movimiento> getListaMovimientos() {
        return this.movimientos;
    }
}
