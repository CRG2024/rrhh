package app.view;


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
	private TableColumn<Movimiento, String> movimientoColumn;
	@FXML
	private TableColumn<Movimiento, String> horarioColumn;
	@FXML
	private TableColumn<Movimiento, String> centroColumn;
	@FXML
	private TableColumn<Movimiento, LocalDate> fechaInicioColumn;
	@FXML
	private TableColumn<Movimiento, LocalDate> fechaFinColumn;

	@FXML
	private ComboBox nombreSelect;

	@FXML
	private ComboBox centroSelect;
	@FXML
	private ComboBox categoriaSelect;

	@FXML
	private RadioButton radioButtonTodos;
	@FXML
	private TextField nombreField;
	@FXML
	private TextField apellidosField;
	@FXML
	private TextField dniField;
	@FXML
	private TextField direccionField;
	@FXML
	private TextField telefonoField;
	@FXML
	private TextField emailField;
	@FXML
	private TextField categoriaField;
	@FXML
	private TextField estadoField;
	@FXML
	private TextField vacacionesField;
	@FXML
	private TextField adelantosField;

	@FXML
	private DatePicker inicioDatepicker;

	@FXML
	private DatePicker finDatepicker;

	private ObservableList<String> nombreTrabajadores = FXCollections.observableArrayList();
	private ObservableList<String> nombreCentro = FXCollections.observableArrayList();
	private ObservableList<String> nombreCategoria = FXCollections.observableArrayList();


    private Stage dialogStage;
    private Movimiento movimiento;
    private boolean okClicked = false;
    private DataBase bbdd;
	private ObservableList<Movimiento> movimientos = FXCollections.observableArrayList();
	private ObservableList<Trabajador> trabajadores = FXCollections.observableArrayList();

	private ObservableList<Centro> centros = FXCollections.observableArrayList();

	private ObservableList<Categoria> categorias = FXCollections.observableArrayList();

	private PdfCreator pdfCreator;

	private Trabajador selectedTrabajador;


    @FXML
    private void initialize() throws SQLException {
		bbdd = new DataBase();
		pdfCreator = new PdfCreator();


		nombreField.setEditable(false);
		nombreField.setFocusTraversable(false);
		nombreField.setDisable(true);

		apellidosField.setEditable(false);
		apellidosField.setFocusTraversable(false);
		apellidosField.setDisable(true);

		dniField.setEditable(false);
		dniField.setFocusTraversable(false);
		dniField.setDisable(true);

		direccionField.setEditable(false);
		direccionField.setFocusTraversable(false);
		direccionField.setDisable(true);

		telefonoField.setEditable(false);
		telefonoField.setFocusTraversable(false);
		telefonoField.setDisable(true);

		emailField.setEditable(false);
		emailField.setFocusTraversable(false);
		emailField.setDisable(true);

		categoriaField.setEditable(false);
		categoriaField.setFocusTraversable(false);
		categoriaField.setDisable(true);

		estadoField.setEditable(false);
		estadoField.setFocusTraversable(false);
		estadoField.setDisable(true);

		vacacionesField.setEditable(false);
		vacacionesField.setFocusTraversable(false);
		vacacionesField.setDisable(true);

		adelantosField.setEditable(false);
		adelantosField.setFocusTraversable(false);
		adelantosField.setDisable(true);

		actualizarListasSelect();
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
	public void actualizarTrabajador() throws SQLException {
		SingleSelectionModel selectionModel = nombreSelect.getSelectionModel();
		int index = selectionModel.getSelectedIndex();
		if (index > -1){
			selectedTrabajador = trabajadores.get(index);
			nombreField.setText(selectedTrabajador.getNombre());
			apellidosField.setText(selectedTrabajador.getApellidos());
			dniField.setText(selectedTrabajador.getDni());
			direccionField.setText(selectedTrabajador.getDomicilio() +
					", "+
					selectedTrabajador.getCiudad() +
					" " +
					selectedTrabajador.getCp());
			telefonoField.setText(selectedTrabajador.getTelefono1());
			emailField.setText(selectedTrabajador.getEmail());

			actualizarTablaMovimientos(selectedTrabajador.getDni());
		}

	}

	private void actualizarTablaMovimientos(String dni) throws SQLException {
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
		ObservableList<Movimiento> movimientosFechas = bbdd.obtenerMovimientoTrabajadorFecha(dni, inicioDatepicker.getValue(), finDatepicker.getValue());
		movimientos = filtrarPorCentroYCategoria(movimientosFechas);
		movimientoTableView.setItems(movimientos);
	}

	private ObservableList<Movimiento> filtrarPorCentroYCategoria(ObservableList<Movimiento> movimientosFechas) {

		SingleSelectionModel selectionModelCentro = centroSelect.getSelectionModel();
		int indexCentro = selectionModelCentro.getSelectedIndex();
		ObservableList<Movimiento> listaActMovs = FXCollections.observableArrayList();

		if(indexCentro != -1){
			Centro selectedCentro = centros.get(indexCentro);
			for (Movimiento mov:movimientosFechas) {
				if (mov.getNombreCentro().equals(selectedCentro.getNombre())){
					listaActMovs.add(mov);
				}
			}

			movimientosFechas.clear();
			movimientosFechas.addAll(listaActMovs);
			listaActMovs.clear();
		}

		SingleSelectionModel selectionModelCategoria = categoriaSelect.getSelectionModel();
		int indexCategoria = selectionModelCategoria.getSelectedIndex();

		if(indexCategoria != -1){
			Categoria selectedCategoria = categorias.get(indexCategoria);
			for (Movimiento mov:movimientosFechas) {
				if (mov.getNombreCategoria().equals(selectedCategoria.getNombre())){
					listaActMovs.add(mov);
				}
			}

			movimientosFechas.clear();
			movimientosFechas.addAll(listaActMovs);
			listaActMovs.clear();
		}

		return movimientosFechas;
	}

	@FXML
	private void crearDocumentacionDatos(ActionEvent event) throws IOException, SQLException {

		if(radioButtonTodos.isSelected()){
			for (Trabajador trab:trabajadores) {
				pdfCreator.crearPdfDatosTrabajador(trab);
			}
		}else{
			pdfCreator.crearPdfDatosTrabajador(selectedTrabajador);
		}
	}

	@FXML
	private void crearDocumentacionMovimientos(ActionEvent event) throws IOException, SQLException {

		if(radioButtonTodos.isSelected()){
			for (Trabajador trab:trabajadores) {
				ObservableList<Movimiento> movimientosTrab = bbdd.obtenerMovimientoTrabajadorFecha(
						trab.getDni(),
						inicioDatepicker.getValue(),
						finDatepicker.getValue()
				);
				ObservableList<Movimiento> movimientosFiltrados = filtrarPorCentroYCategoria(movimientosTrab);
				if(!movimientosFiltrados.isEmpty()){
					pdfCreator.crearPdfMovimientos(movimientosFiltrados);
				}
			}

		}else{
			pdfCreator.crearPdfMovimientos(movimientos);
		}

	}

	@FXML
	private void crearDocumentacionDatosMovimientos(ActionEvent event) throws IOException, SQLException {
		if(radioButtonTodos.isSelected()){
			for (Trabajador trab:trabajadores) {
				ObservableList<Movimiento> movimientosTrab = bbdd.obtenerMovimientoTrabajadorFecha(
						trab.getDni(),
						inicioDatepicker.getValue(),
						finDatepicker.getValue()
				);
				pdfCreator.crearPdfDatosTrabajador(trab);
				ObservableList<Movimiento> movimientosFiltrados = filtrarPorCentroYCategoria(movimientosTrab);
				if(!movimientosFiltrados.isEmpty()){
					pdfCreator.crearPdfMovimientos(movimientosFiltrados);
				}
			}
		}else{
			pdfCreator.crearPdfDatosTrabajador(selectedTrabajador);
			pdfCreator.crearPdfMovimientos(movimientos);
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
    	Parent home_page_parent = FXMLLoader.load(getClass().getResource("InformesView.fxml"));
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
