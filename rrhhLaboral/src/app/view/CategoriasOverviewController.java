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
import app.util.DataBase;

public class CategoriasOverviewController {

	@FXML
    private TableView<Categoria> categoriasTable;


	@FXML
    private TableColumn<Categoria, Number> idCategoriaColumn;
	@FXML
    private TableColumn<Categoria, String> nombreColumn;
    @FXML
    private TableColumn<Categoria, String> descripcionColumn;

    @FXML
    private javafx.scene.control.TextField busquedaField;

    private ObservableList<Categoria> datosCategorias = FXCollections.observableArrayList();
    private ObservableList<Categoria> filtroCategorias = FXCollections.observableArrayList();
    private DataBase bbdd;
    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public CategoriasOverviewController() {
    }


    @FXML
    private void initialize() throws SQLException {
    	idCategoriaColumn.setCellValueFactory(cellData -> cellData.getValue().idCategoriaProperty());
    	nombreColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
    	descripcionColumn.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());

    	bbdd = new DataBase();
        datosCategorias = bbdd.obtenerDatosCategorias();
        categoriasTable.setItems(datosCategorias);

        categoriasTable.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
            	Categoria selectedCategoria = categoriasTable.getSelectionModel().getSelectedItem();
            	try {
					showCategoriaEditDialog(selectedCategoria);
					initialize();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });

        categoriasTable.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                	Categoria selectedCategoria = categoriasTable.getSelectionModel().getSelectedItem();
                	try {
						showCategoriaEditDialog(selectedCategoria);
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
    private void buttonEliminarCategoria() throws SQLException {
    	Categoria selectedCategoria = categoriasTable.getSelectionModel().getSelectedItem();
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Confirmaci�n");
    	alert.setContentText("Est� acci�n eliminar� de la Base de datos la categoria "+ selectedCategoria.getNombre()+""+". �Est� seguro?");

    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    		bbdd.eliminarCategoria(selectedCategoria.getIdCategoria());
    		categoriasTable.getItems().removeAll(categoriasTable.getItems());
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
    private void buttonAñadirCategoria() throws SQLException {
        Categoria tempCategoria = new Categoria();
        showNewDialog(tempCategoria);
        initialize();

    }



    public boolean showCategoriaEditDialog(Categoria categoria) throws SQLException {
        try {

            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/CategoriasEditView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Crear Categoria");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            CategoriasEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setCategoria(categoria, bbdd);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean showNewDialog(Categoria categoria) throws SQLException {
        try {

            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/CategoriasCreateView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Categoria");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            CategoriasEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setCategoria(categoria, bbdd);

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
    		categoriasTable.setItems(datosCategorias);
    	}else{

    		filtroCategorias.clear();
        	for (Categoria categoria : datosCategorias) {
        		if(categoria.getNombre().toLowerCase().contains(filtro)||
        				categoria.getDescripcion().toLowerCase().contains(filtro)){
        			filtroCategorias.add(categoria);
        		}
    		}

        	categoriasTable.setItems(filtroCategorias);
    	}

    }

}
