package app.view;


import app.util.*;
import com.itextpdf.text.Document;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.itextpdf.text.DocumentException;
import app.Main;
import app.model.Categoria;
import app.model.Centro;
import app.model.Horario;
import app.model.Movimiento;
import app.model.TipoContrato;
import app.model.TipoMovimiento;
import app.model.Trabajador;

import javax.mail.MessagingException;

public class LlamamientosCreateController {


	@FXML
	private TableView<Movimiento> movimientoTableView;

	@FXML
	private TableColumn<Movimiento, String> movimientoColumn;
	@FXML
	private TableColumn<Movimiento, String> nombreColumn;
	@FXML
	private TableColumn<Movimiento, String> centroColumn;
	@FXML
	private TableColumn<Movimiento, LocalDate> fechaInicioColumn;
	@FXML
	private TableColumn<Movimiento, LocalDate> fechaFinColumn;
    private Stage dialogStage;
    private Movimiento movimiento;
    private boolean okClicked = false;
    private DataBase bbdd;
	private ObservableList<Movimiento> movimientos = FXCollections.observableArrayList();


    @FXML
    private void initialize() throws SQLException {

		movimientoColumn.setCellValueFactory(cellData -> cellData.getValue().nombreTipoMovimientoProperty());
		nombreColumn.setCellValueFactory(cellData -> cellData.getValue().nombreTrabajadorProperty());
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
		movimientoTableView.setItems(movimientos);

		movimientoTableView.setOnMouseClicked((MouseEvent event) -> {
			if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
				Movimiento selectedMovimiento = movimientoTableView.getSelectionModel().getSelectedItem();
				try {
					showEditMovimiento(selectedMovimiento);
					initialize();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});

		movimientoTableView.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					Movimiento selectedMovimiento = movimientoTableView.getSelectionModel().getSelectedItem();
					try {
						showEditMovimiento(selectedMovimiento);
						initialize();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		});

    	
    	
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
    	Parent home_page_parent = FXMLLoader.load(getClass().getResource("MainMovimientosView.fxml"));
	    Scene home_page_scene = new Scene(home_page_parent);
	    Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    app_stage.hide(); 
	    app_stage.setScene(home_page_scene);
	    app_stage.show();
    }

    private boolean isInputValid() {

    	return true;
    }

    

    
    @FXML
    private void createDocumentacion() throws DocumentException, IOException, SQLException, MessagingException, InterruptedException {
    	
        PdfCreator creadorPdf = new PdfCreator();
        ExcelCreator creadorExcel = new ExcelCreator();
		EmailSender email= new EmailSender();

        for (Movimiento mov: movimientos) {
			if(creadorExcel.devolverTipoMovimiento(mov.getNombreTipoMovimiento()).equals("ALTA")){
				String anexo = creadorPdf.crearAnexoTrabajador(bbdd.obtenerTrabajador(mov.getDni()),mov.getFechaInicio(),mov.getFechaFin());
				String llamamiento = creadorPdf.crearLlamamientoRealizadoTrabajador(bbdd.obtenerTrabajador(mov.getDni()),mov.getFechaInicio(),mov.getFechaFin());
				String consentimiento = creadorPdf.crearConsentimientoTrabajador(bbdd.obtenerTrabajador(mov.getDni()),mov.getFechaInicio());


				List<String> documentos = new ArrayList<>();
				documentos.add(anexo);
				documentos.add(llamamiento);
				documentos.add(consentimiento);
				bbdd.insertarMovimiento(mov);


				//TODO MANDAR EMAIL AL TRABAJADOR CON LA DOCUMENTACION
				String correoTrabajador = bbdd.obtenerTrabajador(mov.getDni()).getEmail();
				correoTrabajador = "romerogallen@gmail.com";

				email.mandarCorreoVariosArchivos(documentos, correoTrabajador);
			}

        }
        creadorExcel.crearExcels(movimientos);

    }

	@FXML
	public boolean showCrearMovimiento() throws SQLException {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/LlamamientoNewView.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Trabajador trabajador = new Trabajador();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Nuevo Movimiento");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			LlamamientosEditController controller = loader.getController();
			controller.setListaMovimientos(movimientos);

			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();
			movimientos = controller.getListaMovimientos();
			initialize();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}
	@FXML
	private void buttonEliminarMovimiento() throws SQLException {
		//TODO HACER SELECCION MULTIPLE
		Movimiento selectedMovimiento = movimientoTableView.getSelectionModel().getSelectedItem();
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("¡ALERTA - ACCIÓN IRREVERSIBLE!");
		alert.setContentText("Esta acción eliminará el movimiento de la lista "+ selectedMovimiento.getNombreTrabajador()+""
				+ " "+selectedMovimiento.getNombreTrabajador()+" con dni "+
				selectedMovimiento.getDni()+". ¿Está seguro?");
		alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			movimientos.remove(selectedMovimiento);
			movimientoTableView.getItems().removeAll(movimientoTableView.getItems());
			initialize();
		}
	}

	@FXML
	public boolean showEditMovimiento(Movimiento movimiento) throws SQLException {
		try {

			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/LlamamientoEditView.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Editar Movimiento");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the person into the controller.
			LlamamientosEditController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setMovimiento(movimiento, bbdd);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}
}
