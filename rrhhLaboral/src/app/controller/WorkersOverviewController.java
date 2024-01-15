package app.controller;


import java.io.IOException;
import java.sql.SQLException;
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
    	alert.setTitle("¡ALERTA - ACCIÓN IRREVERSIBLE!");
    	alert.setContentText("Esta acción eliminará de la Base de datos el trabajador "+ selectedPerson.getNombre()+""
    			+ " "+selectedPerson.getApellido1()+" "+selectedPerson.getApellido2()+" con dni "+
                selectedPerson.getDni()+". ¿Está seguro?");

    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    		bbdd.eliminarTrabajador(selectedPerson.getDni());
    		trabajadorTable.getItems().removeAll(trabajadorTable.getItems());
    		initialize();
    	}
    }

    @FXML
    private void buttonVolver(ActionEvent event) throws IOException {
    	Parent home_page_parent = FXMLLoader.load(getClass().getResource("../view/MainView.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide(); //optional
        app_stage.setScene(home_page_scene);
        app_stage.show();

    }

    @FXML
    private void buttonAddTrabajador() throws SQLException {
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
            String[] palabrasfiltro = filtro.split(" ");
        	for (Trabajador trabajador : datosTrabajadores) {
                for(String palabra: palabrasfiltro){
                    if(trabajador.getNombre().toLowerCase().contains(palabra)||
                            trabajador.getApellido1().toLowerCase().contains(palabra)||
                            trabajador.getDni().toLowerCase().contains(palabra)){
                        filtroTrabajadores.add(trabajador);
                    }
                }
    		}

        	trabajadorTable.setItems(filtroTrabajadores);
    	}

    }

}
