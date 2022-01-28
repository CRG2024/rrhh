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
import app.model.Centro;
import app.util.DataBase;

public class CentrosOverviewController {

	@FXML
    private TableView<Centro> centrosTable;


	@FXML
    private TableColumn<Centro, Number> idCentroColumn;
	@FXML
    private TableColumn<Centro, String> nombreColumn;
    @FXML
    private TableColumn<Centro, String> direccionColumn;
    @FXML
    private TableColumn<Centro, String> ciudadColumn;
	@FXML
    private TableColumn<Centro, Number> cpColumn;
    @FXML
    private javafx.scene.control.TextField busquedaField;

    private ObservableList<Centro> datosCentros = FXCollections.observableArrayList();
    private ObservableList<Centro> filtroCentros = FXCollections.observableArrayList();
    private DataBase bbdd;
    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public CentrosOverviewController() {
    }


    @FXML
    private void initialize() throws SQLException {
    	idCentroColumn.setCellValueFactory(cellData -> cellData.getValue().idCentroProperty());
    	nombreColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
    	direccionColumn.setCellValueFactory(cellData -> cellData.getValue().direccionProperty());
    	cpColumn.setCellValueFactory(cellData -> cellData.getValue().cpProperty());
    	ciudadColumn.setCellValueFactory(cellData -> cellData.getValue().ciudadProperty());

    	bbdd = new DataBase();
        datosCentros = bbdd.obtenerDatosCentros();
        centrosTable.setItems(datosCentros);

        centrosTable.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
            	Centro selectedCentro = centrosTable.getSelectionModel().getSelectedItem();
            	try {
					showPersonEditDialog(selectedCentro);
					initialize();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });

        centrosTable.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                	Centro selectedCentro = centrosTable.getSelectionModel().getSelectedItem();
                	try {
						showPersonEditDialog(selectedCentro);
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
    private void buttonEliminarCentro() throws SQLException {
    	Centro selectedCentro = centrosTable.getSelectionModel().getSelectedItem();
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Confirmación");
    	alert.setContentText("Está acción eliminará de la Base de datos el centro "+ selectedCentro.getNombre()+""+". ¿Está seguro?");

    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    		bbdd.eliminarCentro(selectedCentro.getIdCentro());
    		centrosTable.getItems().removeAll(centrosTable.getItems());
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
    private void buttonAñadirCentro() throws SQLException {
        Centro tempCentro = new Centro();
        showNewDialog(tempCentro);
        initialize();

    }



    public boolean showPersonEditDialog(Centro centro) throws SQLException {
        try {

            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/CentroEditView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Crear Centro");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            CentrosEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setCentro(centro, bbdd);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean showNewDialog(Centro centro) throws SQLException {
        try {

            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/CentroCreateView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Centro");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            CentrosEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setCentro(centro, bbdd);

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
    		centrosTable.setItems(datosCentros);
    	}else{

    		filtroCentros.clear();
        	for (Centro centro : datosCentros) {
        		if(centro.getNombre().toLowerCase().contains(filtro)||
        				centro.getDireccion().toLowerCase().contains(filtro)){
        			filtroCentros.add(centro);
        		}
    		}

        	centrosTable.setItems(filtroCentros);
    	}

    }

}
