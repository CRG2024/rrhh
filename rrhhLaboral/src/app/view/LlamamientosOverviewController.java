package app.view;


import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import app.Main;
import app.model.Movimiento;
import app.model.Trabajador;
import app.util.DataBase;

public class LlamamientosOverviewController {

	@FXML
    private TableView<Movimiento> movimientosTable;

	@FXML
    private TableColumn<Movimiento, String> tipoMovimientoColumn;
	@FXML
    private TableColumn<Movimiento, String> trabajadorColumn;
    @FXML
    private TableColumn<Movimiento, String> centroColumn;
    @FXML
    private TableColumn<Movimiento, String> categoriaColumn;
    @FXML
    private TableColumn<Movimiento, String> tipoContratoColumn;
    @FXML
    private TableColumn<Movimiento, String> horarioColumn;
    @FXML
    private TableColumn<Movimiento, LocalDate> fechaInicioColumn;
    @FXML
    private TableColumn<Movimiento, LocalDate> fechaFinColumn;
    @FXML
    private TableColumn<Movimiento, Number> importeBajaColumn;

    @FXML
    private javafx.scene.control.TextField busquedaField;

    private ObservableList<Movimiento> datosMovimientos = FXCollections.observableArrayList();
    private ObservableList<Movimiento> filtroMovimientos = FXCollections.observableArrayList();
    private DataBase bbdd;
    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public LlamamientosOverviewController() {
    }


//    @FXML
//    private void initialize() throws SQLException {
//    	tipoMovimientoColumn.setCellValueFactory(cellData ->   bbdd.obtenerTipoMovimiento(cellData.getValue().getIdTipoMovimiento()).getNombre());
//    	trabajadorColumn.setCellValueFactory(cellData -> cellData.getValue().dniProperty());
//    	centroColumn.setCellValueFactory(cellData -> cellData.getValue().dniProperty());
//    	categoriaColumn.setCellValueFactory(cellData -> cellData.getValue().dniProperty());
//    	tipoContratoColumn.setCellValueFactory(cellData -> cellData.getValue().dniProperty());
//    	horarioColumn.setCellValueFactory(cellData -> cellData.getValue().dniProperty());
//    	
//    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//    	 
//    	fechaInicioColumn.setCellFactory(column -> {
//            return new TableCell<Movimiento, LocalDate>() {
//                @Override
//                protected void updateItem(LocalDate item, boolean empty) {
//                    super.updateItem(item, empty);
//
//                    if (item == null || empty) {
//                        setText(null);
//                    } else {
//                        setText(formatter.format(item));
//
//                    }
//                }
//            };
//        });
//    	
//    	fechaFinColumn.setCellFactory(column -> {
//            return new TableCell<Movimiento, LocalDate>() {
//                @Override
//                protected void updateItem(LocalDate item, boolean empty) {
//                    super.updateItem(item, empty);
//
//                    if (item == null || empty) {
//                        setText(null);
//                    } else {
//                        setText(formatter.format(item));
//
//                    }
//                }
//            };
//        });
//        
//        
//    	
//    	importeBajaColumn.setCellValueFactory(cellData -> cellData.getValue().cpProperty());
//
//
//    	bbdd = new DataBase();
//    	datosMovimientos = bbdd.obtenerDatosMovimientos();
//    	movimientosTable.setItems(datosMovimientos);
//
//    	movimientosTable.setOnMouseClicked((MouseEvent event) -> {
//            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
//            	Movimiento selectedMovement = movimientosTable.getSelectionModel().getSelectedItem();
//            	try {
//					showMovementEditDialog(selectedMovement);
//					initialize();
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//            }
//        });
//
//    	movimientosTable.setOnKeyPressed(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent ke) {
//                if (ke.getCode().equals(KeyCode.ENTER)) {
//                	Movimiento selectedMovement = movimientosTable.getSelectionModel().getSelectedItem();
//                	try {
//                		showMovementEditDialog(selectedMovement);
//						initialize();
//					} catch (SQLException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//                }
//            }
//        });
//
//    }
//
//
//	public void setMainApp(Main mainApp) {
//    }
//
//
//    @FXML
//    private void buttonEliminarTrabajador() throws SQLException {
//    	Trabajador selectedPerson = trabajadorTable.getSelectionModel().getSelectedItem();
//    	Alert alert = new Alert(AlertType.CONFIRMATION);
//    	alert.setTitle("Confirmación");
//    	alert.setContentText("Está acción eliminará de la Base de datos el trabajador "+ selectedPerson.getNombre()+""
//    			+ " "+selectedPerson.getApellido1()+" "+selectedPerson.getApellido2()+" con dni "+ selectedPerson.getDni()+". ¿Está seguro?");
//
//    	Optional<ButtonType> result = alert.showAndWait();
//    	if (result.get() == ButtonType.OK){
//    		bbdd.eliminarTrabajador(selectedPerson.getDni());
//    		trabajadorTable.getItems().removeAll(trabajadorTable.getItems());
//    		initialize();
//    	}
//    }
//
//    @FXML
//    private void buttonVolver(ActionEvent event) throws IOException {
//    	Parent home_page_parent = FXMLLoader.load(getClass().getResource("MainView.fxml"));
//        Scene home_page_scene = new Scene(home_page_parent);
//        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        app_stage.hide(); //optional
//        app_stage.setScene(home_page_scene);
//        app_stage.show();
//
//    }
//
//    /**
//     * Called when the user clicks the new button. Opens a dialog to edit
//     * details for a new person.
//     * @throws SQLException
//     */
//    @FXML
//    private void buttonAñadirTrabajador() throws SQLException {
//        Trabajador tempPerson = new Trabajador();
//        showNewDialog(tempPerson);
//        initialize();
//
//    }
//
//
//
//    public boolean showPersonEditDialog(Trabajador trabajador) throws SQLException {
//        try {
//
//            // Load the fxml file and create a new stage for the popup dialog.
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(Main.class.getResource("view/WorkerEditView.fxml"));
//            AnchorPane page = (AnchorPane) loader.load();
//
//            // Create the dialog Stage.
//            Stage dialogStage = new Stage();
//            dialogStage.setTitle("Edit Person");
//            dialogStage.initModality(Modality.WINDOW_MODAL);
//            Scene scene = new Scene(page);
//            dialogStage.setScene(scene);
//
//            // Set the person into the controller.
//            WorkersEditController controller = loader.getController();
//            controller.setDialogStage(dialogStage);
//            controller.setPerson(trabajador, bbdd);
//
//            // Show the dialog and wait until the user closes it
//            dialogStage.showAndWait();
//
//            return controller.isOkClicked();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
//
//    }
//
//    public boolean showNewDialog(Trabajador trabajador) throws SQLException {
//        try {
//
//            // Load the fxml file and create a new stage for the popup dialog.
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(Main.class.getResource("view/WorkerCreateView.fxml"));
//            AnchorPane page = (AnchorPane) loader.load();
//
//            // Create the dialog Stage.
//            Stage dialogStage = new Stage();
//            dialogStage.setTitle("Edit Person");
//            dialogStage.initModality(Modality.WINDOW_MODAL);
//            Scene scene = new Scene(page);
//            dialogStage.setScene(scene);
//
//            // Set the person into the controller.
//            WorkersEditController controller = loader.getController();
//            controller.setDialogStage(dialogStage);
//            controller.setPerson(trabajador, bbdd);
//
//            // Show the dialog and wait until the user closes it
//            dialogStage.showAndWait();
//
//            return controller.isOkClicked();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
//
//    }
//
//    @FXML
//    public void filtrarResultados(KeyEvent event){
//
//    	String filtro = busquedaField.getText().toLowerCase();
//
//    	if (filtro.isEmpty()){
//    		trabajadorTable.setItems(datosTrabajadores);
//    	}else{
//
//    		filtroTrabajadores.clear();
//        	for (Trabajador trabajador : datosTrabajadores) {
//        		if(trabajador.getNombre().toLowerCase().contains(filtro)||
//        				trabajador.getApellido1().toLowerCase().contains(filtro)){
//        			filtroTrabajadores.add(trabajador);
//        		}
//    		}
//
//        	trabajadorTable.setItems(filtroTrabajadores);
//    	}
//
//    }

}
