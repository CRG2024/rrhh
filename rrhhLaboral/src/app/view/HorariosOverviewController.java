package app.view;


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
import app.model.Horario;
import app.util.DataBase;

public class HorariosOverviewController {

	@FXML
    private TableView<Horario> horariosTable;


	@FXML
    private TableColumn<Horario, Number> idHorarioColumn;
	@FXML
    private TableColumn<Horario, String> nombreColumn;
    @FXML
    private TableColumn<Horario, String> horarioColumn;
    @FXML
    private TableColumn<Horario, String> horasColumn;
    @FXML
    private javafx.scene.control.TextField busquedaField;

    private ObservableList<Horario> datosHorarios = FXCollections.observableArrayList();
    private ObservableList<Horario> filtroHorarios = FXCollections.observableArrayList();
    private DataBase bbdd;
    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public HorariosOverviewController() {
    }


    @FXML
    private void initialize() throws SQLException {
    	idHorarioColumn.setCellValueFactory(cellData -> cellData.getValue().idHorarioProperty());
    	nombreColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
    	horarioColumn.setCellValueFactory(cellData -> cellData.getValue().horarioProperty());
    	horasColumn.setCellValueFactory(cellData -> cellData.getValue().horasProperty());

    	bbdd = new DataBase();
        datosHorarios = bbdd.obtenerDatosHorarios();
        horariosTable.setItems(datosHorarios);

        horariosTable.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
            	Horario selectedHorario = horariosTable.getSelectionModel().getSelectedItem();
            	try {
					showHorarioEditDialog(selectedHorario);
					initialize();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });

        horariosTable.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                	Horario selectedHorario = horariosTable.getSelectionModel().getSelectedItem();
                	try {
                		showHorarioEditDialog(selectedHorario);
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
    private void buttonEliminarHorario() throws SQLException {
    	Horario selectedHorario = horariosTable.getSelectionModel().getSelectedItem();
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Confirmaci�n");
    	alert.setContentText("Est� acci�n eliminar� de la Base de datos el tipo de horario "+ selectedHorario.getNombre()+""+". �Est� seguro?");

    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    		bbdd.eliminarHorario(selectedHorario.getIdHorario());
    		horariosTable.getItems().removeAll(horariosTable.getItems());
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


    @FXML
    private void buttonAñadirHorario() throws SQLException {
        Horario tempHorario = new Horario();
        showNewDialog(tempHorario);
        initialize();

    }



    public boolean showHorarioEditDialog(Horario horario) throws SQLException {
        try {

            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/HorariosEditView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Editar Horario");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            HorariosEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setHorario(horario, bbdd);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean showNewDialog(Horario horario) throws SQLException {
        try {

            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/HorariosCreateView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Crear Horario");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            HorariosEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setHorario(horario, bbdd);

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
    		horariosTable.setItems(datosHorarios);
    	}else{

    		filtroHorarios.clear();
        	for (Horario horario : datosHorarios) {
        		if(horario.getNombre().toLowerCase().contains(filtro)||
        				horario.getHorario().toLowerCase().contains(filtro)){
        			filtroHorarios.add(horario);
        		}
    		}

        	horariosTable.setItems(filtroHorarios);
    	}

    }

}
