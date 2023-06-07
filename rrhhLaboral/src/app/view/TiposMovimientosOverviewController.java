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
import app.model.Categoria;
import app.model.TipoMovimiento;
import app.util.DataBase;

public class TiposMovimientosOverviewController {

	@FXML
    private TableView<TipoMovimiento> movimientosTable;


	@FXML
    private TableColumn<TipoMovimiento, Number> idTipoMovimientoColumn;
	@FXML
    private TableColumn<TipoMovimiento, String> nombreColumn;

    @FXML
    private javafx.scene.control.TextField busquedaField;

    private ObservableList<TipoMovimiento> datosTipoMovimientos = FXCollections.observableArrayList();
    private ObservableList<TipoMovimiento> filtroTipoMovimientos = FXCollections.observableArrayList();
    private DataBase bbdd;

    public TiposMovimientosOverviewController() {
    }


    @FXML
    private void initialize() throws SQLException {
    	idTipoMovimientoColumn.setCellValueFactory(cellData -> cellData.getValue().idTipoMovimientoProperty());
    	nombreColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());

    	bbdd = new DataBase();
    	datosTipoMovimientos = bbdd.obtenerDatosTipoMovimientos();
    	movimientosTable.setItems(datosTipoMovimientos);

    	movimientosTable.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
            	TipoMovimiento selectedTipoMovimiento = movimientosTable.getSelectionModel().getSelectedItem();
            	try {
					showMovimientoEditDialog(selectedTipoMovimiento);
					initialize();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });

    	movimientosTable.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                	TipoMovimiento selectedTipoMovimiento = movimientosTable.getSelectionModel().getSelectedItem();
                	try {
                		showMovimientoEditDialog(selectedTipoMovimiento);
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
    private void buttonEliminarTipoMovimiento() throws SQLException {
    	TipoMovimiento selectedMovimiento = movimientosTable.getSelectionModel().getSelectedItem();
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Confirmaci�n");
    	alert.setContentText("Est� acci�n eliminar� de la Base de datos el tipo de movimiento "+ selectedMovimiento.getNombre()+""+". �Est� seguro?");

    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    		bbdd.eliminarTipoMovimiento(selectedMovimiento.getIdTipoMovimiento());
    		movimientosTable.getItems().removeAll(movimientosTable.getItems());
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
    private void buttonAñadirTipoMovimiento() throws SQLException {
        TipoMovimiento tempMovimiento = new TipoMovimiento();
        showNewDialog(tempMovimiento);
        initialize();

    }



    public boolean showMovimientoEditDialog(TipoMovimiento tipoMovimiento) throws SQLException {
        try {

            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/TipoMovimientoEditView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Editar Tipo Movimiento");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            TiposMovimientosEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMovimiento(tipoMovimiento, bbdd);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }
    public boolean showNewDialog(TipoMovimiento tipoMovimiento) throws SQLException {
        try {

            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/TipoMovimientoCreateView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Crear Tipo Movimiento");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            TiposMovimientosEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMovimiento(tipoMovimiento, bbdd);

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
    		movimientosTable.setItems(datosTipoMovimientos);
    	}else{

    		filtroTipoMovimientos.clear();
        	for (TipoMovimiento movimiento : datosTipoMovimientos) {
        		if(movimiento.getNombre().toLowerCase().contains(filtro)){
        			filtroTipoMovimientos.add(movimiento);
        		}
    		}

        	movimientosTable.setItems(filtroTipoMovimientos);
    	}

    }

}
