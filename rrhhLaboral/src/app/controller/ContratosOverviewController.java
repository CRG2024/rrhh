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
import app.model.TipoContrato;
import app.util.DataBase;

public class ContratosOverviewController {

	@FXML
    private TableView<TipoContrato> contratosTable;


	@FXML
    private TableColumn<TipoContrato, Number> idContratoColumn;
	@FXML
    private TableColumn<TipoContrato, String> nombreColumn;
    @FXML
    private TableColumn<TipoContrato, String> codContratoColumn;

    @FXML
    private javafx.scene.control.TextField busquedaField;

    private ObservableList<TipoContrato> datosContratos = FXCollections.observableArrayList();
    private ObservableList<TipoContrato> filtroContratos = FXCollections.observableArrayList();
    private DataBase bbdd;
    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public ContratosOverviewController() {
    }


    @FXML
    private void initialize() throws SQLException {
    	idContratoColumn.setCellValueFactory(cellData -> cellData.getValue().idTipoContratoProperty());
    	nombreColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
    	codContratoColumn.setCellValueFactory(cellData -> cellData.getValue().codContratoProperty());

    	bbdd = new DataBase();
        datosContratos = bbdd.obtenerDatosContratos();
        contratosTable.setItems(datosContratos);

        contratosTable.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
            	TipoContrato selectedContrato = contratosTable.getSelectionModel().getSelectedItem();
            	try {
					showContratoEditDialog(selectedContrato);
					initialize();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });

        contratosTable.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                	TipoContrato selectedContrato = contratosTable.getSelectionModel().getSelectedItem();
                	try {
                		showContratoEditDialog(selectedContrato);
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
    private void buttonEliminarContrato() throws SQLException {
    	TipoContrato selectedContrato = contratosTable.getSelectionModel().getSelectedItem();
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Confirmaci�n");
    	alert.setContentText("Est� acci�n eliminar� de la Base de datos el tipo de contrato "+ selectedContrato.getNombre()+""+". �Est� seguro?");

    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    		bbdd.eliminarContrato(selectedContrato.getIdTipoContrato());
    		contratosTable.getItems().removeAll(contratosTable.getItems());
    		initialize();
    	}
    }

    @FXML
    private void buttonVolver(ActionEvent event) throws IOException {
    	Parent home_page_parent = FXMLLoader.load(getClass().getResource("../view/CreacionDatosView.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide(); //optional
        app_stage.setScene(home_page_scene);
        app_stage.show();

    }


    @FXML
    private void buttonAñadirContrato() throws SQLException {
        TipoContrato tempContrato = new TipoContrato();
        showNewDialog(tempContrato);
        initialize();

    }



    public boolean showContratoEditDialog(TipoContrato contrato) throws SQLException {
        try {

            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/ContratosEditView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Crear Contrato");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            ContratosEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setContrato(contrato, bbdd);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean showNewDialog(TipoContrato contrato) throws SQLException {
        try {

            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/ContratosCreateView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Contrato");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            ContratosEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setContrato(contrato, bbdd);

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
    		contratosTable.setItems(datosContratos);
    	}else{

    		filtroContratos.clear();
        	for (TipoContrato contrato : datosContratos) {
        		if(contrato.getNombre().toLowerCase().contains(filtro)||
        				contrato.getCodContrato().toLowerCase().contains(filtro)){
        			filtroContratos.add(contrato);
        		}
    		}

        	contratosTable.setItems(filtroContratos);
    	}

    }

}
