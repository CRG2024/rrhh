package app.view;


import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


import app.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Pagination;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import app.Main;
import app.util.DataBase;

public class LlamamientosOverviewController {

	@FXML
    private TableView<Movimiento> movimientosTable;

    @FXML
    private TableColumn<Movimiento, String> tipoMovimientoColumn;

    @FXML
    private TableColumn<Movimiento, String> dniColumn;
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


    @FXML
    private void initialize() throws SQLException {

        tipoMovimientoColumn.setCellValueFactory(cellData -> cellData.getValue().nombreTipoMovimientoProperty());
        trabajadorColumn.setCellValueFactory(cellData -> cellData.getValue().nombreTrabajadorProperty());
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
        datosMovimientos = bbdd.obtenerDatosMovimientos();
        movimientosTable.setItems(datosMovimientos);

        movimientosTable.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                Movimiento selectedMovimiento = movimientosTable.getSelectionModel().getSelectedItem();
                try {
                    showEditMovimiento(selectedMovimiento);
                    initialize();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        movimientosTable.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    Movimiento selectedMovimiento = movimientosTable.getSelectionModel().getSelectedItem();
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


    private void showMovementEditDialog(Movimiento selectedMovement) {
    }


    public void setMainApp(Main mainApp) {
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


    @FXML
    public void filtrarResultados(KeyEvent event) throws Throwable {

        String filtro = busquedaField.getText().toLowerCase();

        if (filtro.isEmpty()){
            movimientosTable.setItems(datosMovimientos);
        }else{

            filtroMovimientos.clear();
            for (Movimiento movimiento : datosMovimientos) {
                if(movimiento.getNombreTrabajador().toLowerCase().contains(filtro)||
                        movimiento.getNombreCentro().toLowerCase().contains(filtro)){
                    filtroMovimientos.add(movimiento);
                }
            }

            movimientosTable.setItems(filtroMovimientos);
        }

    }

}
