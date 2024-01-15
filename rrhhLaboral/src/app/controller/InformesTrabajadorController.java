package app.controller;


import app.Main;
import app.model.Categoria;
import app.model.Centro;
import app.model.Movimiento;
import app.model.Trabajador;
import app.util.*;
import com.itextpdf.text.DocumentException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InformesTrabajadorController {


	@FXML
	private TableView<Movimiento> movimientoTableView;
	@FXML
	private TableColumn<Movimiento, String> movimientoColumn, horarioColumn, centroColumn;
	@FXML
	private TableColumn<Movimiento, LocalDate> fechaInicioColumn, fechaFinColumn;
	@FXML
	private ComboBox nombreSelect, centroSelect, categoriaSelect;
	@FXML
	private CheckBox checkDatos, checkMovimientos, checkRegistroHoras;
	@FXML
	private TextField nombreField, apellidosField, dniField, direccionField,telefonoField, emailField,
			categoriaField, estadoField, vacacionesField, adelantosField;
	@FXML
	private DatePicker inicioDatepicker, finDatepicker;
	private ObservableList<String> nombreTrabajadores = FXCollections.observableArrayList();
	private ObservableList<String> nombreCentro = FXCollections.observableArrayList();
	private ObservableList<String> nombreCategoria = FXCollections.observableArrayList();
	private ObservableList<Movimiento> movimientos = FXCollections.observableArrayList();
	private ObservableList<Trabajador> trabajadores = FXCollections.observableArrayList();
	private ObservableList<Centro> centros = FXCollections.observableArrayList();
	private ObservableList<Categoria> categorias = FXCollections.observableArrayList();

    private Stage dialogStage;
    private DataBase bbdd;
	private FiltraDatos filtroDatos;
	private PdfCreator pdfCreator;
	private Trabajador selectedTrabajador;
	private boolean okClicked = false;

    @FXML
    private void initialize() throws SQLException {
		bbdd = new DataBase();
		filtroDatos = new FiltraDatos();
		pdfCreator = new PdfCreator();
		desactivarCampos();
		actualizarListasSelect();
    }

	private void desactivarCampos(){
		campoFalse(nombreField);
		campoFalse(apellidosField);
		campoFalse(dniField);
		campoFalse(direccionField);
		campoFalse(telefonoField);
		campoFalse(emailField);
		campoFalse(categoriaField);
		campoFalse(estadoField);
		campoFalse(vacacionesField);
		campoFalse(adelantosField);
	}

	private void campoFalse(TextField field){
		field.setEditable(false);
		field.setFocusTraversable(false);
		field.setDisable(false);
	}

	private void actualizarListasSelect() throws SQLException {

		//Nombres
		trabajadores = bbdd.obtenerDatosTrabajadores();
		for(Trabajador trabajador: trabajadores){
			nombreTrabajadores.add(trabajador.getNombreCompleto());
		}
		nombreSelect.setItems(nombreTrabajadores);
		new ComboBoxAutoComplete<String>(nombreSelect);

		centros = bbdd.obtenerDatosCentros();
		for(Centro centro: centros){
			nombreCentro.add(centro.getNombre());
		}
		centroSelect.setItems(nombreCentro);
		new ComboBoxAutoComplete<String>(centroSelect);

		categorias = bbdd.obtenerDatosCategorias();
		for(Categoria categoria: categorias){
			nombreCategoria.add(categoria.getNombre());
		}
		categoriaSelect.setItems(nombreCategoria);
		new ComboBoxAutoComplete<String>(categoriaSelect);
	}

	@FXML
	public void actualizarDatosInforme() throws SQLException {
		actualizarTrabajador();
		actualizarTablaMovimientos();
	}
	@FXML
	public void actualizarTrabajador(){
		SingleSelectionModel selectionModel = nombreSelect.getSelectionModel();
		int index = selectionModel.getSelectedIndex();
		if (index > -1){
			selectedTrabajador = trabajadores.get(index);
			nombreField.setText(selectedTrabajador.getNombre());
			apellidosField.setText(selectedTrabajador.getSoloApellidos());
			dniField.setText(selectedTrabajador.getDni());
			direccionField.setText(selectedTrabajador.getDomicilio() +
					", "+
					selectedTrabajador.getCiudad() +
					" " +
					selectedTrabajador.getCp());
			telefonoField.setText(selectedTrabajador.getTelefono1());
			emailField.setText(selectedTrabajador.getEmail());
		}else{
			nombreField.setText("");
			apellidosField.setText("");
			dniField.setText("");
			direccionField.setText("");
			telefonoField.setText("");
			emailField.setText("");
		}
	}

	private void actualizarTablaMovimientos() throws SQLException {
		movimientoColumn.setCellValueFactory(cellData -> cellData.getValue().nombreTipoMovimientoProperty());
		horarioColumn.setCellValueFactory(cellData -> cellData.getValue().nombreHorarioProperty());
		centroColumn.setCellValueFactory(cellData -> cellData.getValue().nombreCentroProperty());
		fechaInicioColumn.setCellValueFactory(cellData -> cellData.getValue().fechaInicioProperty());

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		fechaInicioColumn.setCellFactory(column -> {
			return new TableCell<Movimiento, LocalDate>() {
				@Override
				protected void updateItem(LocalDate item, boolean empty) {
					super.updateItem(item, empty);

					if (item == null || empty) {
						setText(null);
					} else {
						setText(formatter.format(item));

					}
				}
			};
		});

		fechaFinColumn.setCellValueFactory(cellData -> cellData.getValue().fechaFinProperty());
		fechaFinColumn.setCellFactory(column -> {
			return new TableCell<Movimiento, LocalDate>() {
				@Override
				protected void updateItem(LocalDate item, boolean empty) {
					super.updateItem(item, empty);

					if (item == null || empty) {
						setText(null);
					} else {
						setText(formatter.format(item));

					}
				}
			};
		});

		bbdd = new DataBase();
		int indexNombre = nombreSelect.getSelectionModel().getSelectedIndex();
		int indexCategoria = categoriaSelect.getSelectionModel().getSelectedIndex();
		int indexCentro = centroSelect.getSelectionModel().getSelectedIndex();
		movimientos = filtrarMovimientos(indexNombre,indexCategoria,indexCentro);
		movimientoTableView.setItems(movimientos);
	}

	private ObservableList<Movimiento> filtrarMovimientos(
			int indexNombre,
			int indexCategoria,
			int indexCentro) throws SQLException {
		ObservableList<Movimiento> movimientosTrabajador = filtroDatos.filtrarMovimientosPorTrabajador(
				indexNombre,
				trabajadores,
				bbdd.obtenerDatosMovimientos());
		ObservableList<Movimiento> movimientosCategoria = filtroDatos.filtrarMovimientosPorCategoria(
				indexCategoria,
				categorias,
				movimientosTrabajador
		);
		ObservableList<Movimiento> movimientosCentro =  filtroDatos.filtrarMovimientosPorCentro(
				indexCentro,
				centros,
				movimientosCategoria
		);
		ObservableList<Movimiento> movimientosEntreFechas = filtroDatos.filtrarMovimientosPorFechas(
				inicioDatepicker.getValue(),
				finDatepicker.getValue(),
				movimientosCentro
		);
		return movimientosEntreFechas;
	}
	@FXML
	private void crearDocumentacion(ActionEvent event) throws SQLException {
		actualizarDatosInforme();
		if(checkDatos.isSelected()){
			crearDocumentacionDatos();
		}
		if(checkMovimientos.isSelected()){
			crearDocumentacionMovimientos();
		}
		if(checkRegistroHoras.isSelected()){
			crearDocumentacionRegistroHoras();
		}
	}

	private void crearDocumentacionDatos(){
		int index = nombreSelect.getSelectionModel().getSelectedIndex();
		if (index > -1){
			pdfCreator.crearPdfDatosTrabajador(selectedTrabajador);
		}else{
			for (Trabajador trab:trabajadores) {
				pdfCreator.crearPdfDatosTrabajador(trab);
			}
		}
	}

	private void crearDocumentacionMovimientos() throws SQLException {
		SingleSelectionModel selectionModel = nombreSelect.getSelectionModel();
		int index = selectionModel.getSelectedIndex();
		if (index > -1){
			pdfCreator.crearPdfMovimientos(movimientos);
		}else{
			for(int ind = 0; ind <= trabajadores.size();ind++){
				int indexNombre = ind;
				int indexCategoria = categoriaSelect.getSelectionModel().getSelectedIndex();
				int indexCentro = centroSelect.getSelectionModel().getSelectedIndex();
				pdfCreator.crearPdfMovimientos(filtrarMovimientos(indexNombre,indexCategoria,indexCentro));
			}
		}
	}
	private void crearDocumentacionRegistroHoras() throws SQLException {
		SingleSelectionModel selectionModel = nombreSelect.getSelectionModel();
		int index = selectionModel.getSelectedIndex();
		if (index > -1){
			pdfCreator.crearPdfRegistroHoras();
		}else{
			for(int ind = 0; ind <= trabajadores.size();ind++){
				int indexNombre = ind;
				int indexCategoria = categoriaSelect.getSelectionModel().getSelectedIndex();
				int indexCentro = centroSelect.getSelectionModel().getSelectedIndex();
				pdfCreator.crearPdfRegistroHoras();
			}
		}
	}

	public void setDialogStage(Stage dialogStage) {

		this.dialogStage = dialogStage;
		this.dialogStage.setMaximized(true);
    }

    public boolean isOkClicked() {
        return okClicked;
    }

	@FXML
    private void handleCancel(ActionEvent event) throws IOException {
    	Parent home_page_parent = FXMLLoader.load(getClass().getResource("../view/InformesView.fxml"));
	    Scene home_page_scene = new Scene(home_page_parent);
	    Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    app_stage.hide(); 
	    app_stage.setScene(home_page_scene);
	    app_stage.show();
    }

    private boolean isInputValid() {

    	return true;
    }

}
