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
import app.model.Trabajador;
import app.util.DataBase;

public class WorkersOverviewController {

	@FXML
    private TableView<Trabajador> trabajadorTable;

	@FXML
    private TableColumn<Trabajador, String> dniColumn;
	@FXML
    private TableColumn<Trabajador, String> nombreColumn;
    @FXML
    private TableColumn<Trabajador, String> apellido1Column;
    @FXML
    private TableColumn<Trabajador, String> apellido2Column;
    @FXML
    private TableColumn<Trabajador, LocalDate> fechaNacimientoColumn;
	@FXML
    private TableColumn<Trabajador, String> nacionalidadColumn;
    @FXML
    private TableColumn<Trabajador, String> domicilioColumn;
    @FXML
    private TableColumn<Trabajador, String> ciudadColumn;
    @FXML
    private TableColumn<Trabajador, String> poblacionColumn;
	@FXML
    private TableColumn<Trabajador, Number> cpColumn;
    @FXML
    private TableColumn<Trabajador, String> nssColumn;
    @FXML
    private TableColumn<Trabajador, String> emailColumn;
    @FXML
    private TableColumn<Trabajador, String> telefono1Column;
	@FXML
    private TableColumn<Trabajador, String> telefono2Column;
    @FXML
    private TableColumn<Trabajador, String> cuentaColumn;
    @FXML
    private TableColumn<Trabajador, String> carnetColumn;
    @FXML
    private TableColumn<Trabajador, String> vehiculoColumn;
	@FXML
    private TableColumn<Trabajador, String> permisoTrabajoColumn;
    @FXML
    private TableColumn<Trabajador, String> discapacidadesColumn;

    @FXML
    private javafx.scene.control.TextField busquedaField;

    private ObservableList<Trabajador> datosTrabajadores = FXCollections.observableArrayList();
    private ObservableList<Trabajador> filtroTrabajadores = FXCollections.observableArrayList();
    private DataBase bbdd;
    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public WorkersOverviewController() {
    }


    @FXML
    private void initialize() throws SQLException {
    	dniColumn.setCellValueFactory(cellData -> cellData.getValue().dniProperty());
    	nombreColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
    	apellido1Column.setCellValueFactory(cellData -> cellData.getValue().apellido1Property());
    	apellido2Column.setCellValueFactory(cellData -> cellData.getValue().apellido2Property());
    	fechaNacimientoColumn.setCellValueFactory(cellData -> cellData.getValue().fechaNacimientoProperty());
    	
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    	 
    	fechaNacimientoColumn.setCellFactory(column -> {
            return new TableCell<Trabajador, LocalDate>() {
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
        
        
    	nacionalidadColumn.setCellValueFactory(cellData -> cellData.getValue().nacionalidadProperty());
    	domicilioColumn.setCellValueFactory(cellData -> cellData.getValue().domicilioProperty());
    	ciudadColumn.setCellValueFactory(cellData -> cellData.getValue().ciudadProperty());
    	poblacionColumn.setCellValueFactory(cellData -> cellData.getValue().poblacionProperty());
    	cpColumn.setCellValueFactory(cellData -> cellData.getValue().cpProperty());
    	nssColumn.setCellValueFactory(cellData -> cellData.getValue().nssProperty());
    	emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
    	telefono1Column.setCellValueFactory(cellData -> cellData.getValue().telefono1Property());
    	telefono2Column.setCellValueFactory(cellData -> cellData.getValue().telefono2Property());
    	cuentaColumn.setCellValueFactory(cellData -> cellData.getValue().cuentaProperty());
    	carnetColumn.setCellValueFactory(cellData -> cellData.getValue().carnetProperty());
    	vehiculoColumn.setCellValueFactory(cellData -> cellData.getValue().vehiculoProperty());
    	permisoTrabajoColumn.setCellValueFactory(cellData -> cellData.getValue().permisoTrabajoProperty());
    	discapacidadesColumn.setCellValueFactory(cellData -> cellData.getValue().discapacidadesProperty());


    	bbdd = new DataBase();
        datosTrabajadores = bbdd.obtenerDatosTrabajadores();
        trabajadorTable.setItems(datosTrabajadores);

        trabajadorTable.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
            	Trabajador selectedPerson = trabajadorTable.getSelectionModel().getSelectedItem();
            	try {
					showPersonEditDialog(selectedPerson);
					initialize();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });

        trabajadorTable.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                	Trabajador selectedPerson = trabajadorTable.getSelectionModel().getSelectedItem();
                	try {
						showPersonEditDialog(selectedPerson);
						initialize();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
            }
        });

    }


	public void setMainApp(Main mainApp) {
    }


    @FXML
    private void buttonEliminarTrabajador() throws SQLException {
    	Trabajador selectedPerson = trabajadorTable.getSelectionModel().getSelectedItem();
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Confirmaci�n");
    	alert.setContentText("Est� acci�n eliminar� de la Base de datos el trabajador "+ selectedPerson.getNombre()+""
    			+ " "+selectedPerson.getApellido1()+" "+selectedPerson.getApellido2()+" con dni "+ selectedPerson.getDni()+". �Est� seguro?");

    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    		bbdd.eliminarTrabajador(selectedPerson.getDni());
    		trabajadorTable.getItems().removeAll(trabajadorTable.getItems());
    		initialize();
    	}
    }

    @FXML
    private void buttonVolver(ActionEvent event) throws IOException {
    	Parent home_page_parent = FXMLLoader.load(getClass().getResource("MainView.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide(); //optional
        app_stage.setScene(home_page_scene);
        app_stage.show();

    }

    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new person.
     * @throws SQLException
     */
    @FXML
    private void buttonAñadirTrabajador() throws SQLException {
        Trabajador tempPerson = new Trabajador();
        showNewDialog(tempPerson);
        initialize();

    }



    public boolean showPersonEditDialog(Trabajador trabajador) throws SQLException {
        try {

            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/WorkerEditView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            WorkersEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(trabajador, bbdd);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean showNewDialog(Trabajador trabajador) throws SQLException {
        try {

            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/WorkerCreateView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            WorkersEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(trabajador, bbdd);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    @FXML
    public void filtrarResultados(KeyEvent event){

    	String filtro = busquedaField.getText().toLowerCase();

    	if (filtro.isEmpty()){
    		trabajadorTable.setItems(datosTrabajadores);
    	}else{

    		filtroTrabajadores.clear();
        	for (Trabajador trabajador : datosTrabajadores) {
        		if(trabajador.getNombre().toLowerCase().contains(filtro)||
        				trabajador.getApellido1().toLowerCase().contains(filtro)){
        			filtroTrabajadores.add(trabajador);
        		}
    		}

        	trabajadorTable.setItems(filtroTrabajadores);
    	}

    }

}
