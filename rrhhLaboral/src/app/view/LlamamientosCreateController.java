package app.view;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.Main;
import app.model.Categoria;
import app.model.Centro;
import app.model.Horario;
import app.model.Movimiento;
import app.model.TipoContrato;
import app.model.TipoMovimiento;
import app.model.Trabajador;
import app.util.DataBase;

public class LlamamientosCreateController {

	@FXML
	private TextField dniField;
	@FXML
	private TextField nombreField;
	@FXML
	private TextField apellido1Field;
	@FXML
	private TextField apellido2Field;
	@FXML
	private TextField fechaNacimientoField;

	@FXML
	private DatePicker fechaNacimientoDatePicker;
	@FXML
	private TextField nacionalidadField;
	@FXML
	private TextField domicilioField;
	@FXML
	private TextField ciudadField;
	@FXML
	private TextField poblacionField;
	@FXML
	private TextField cpField;
	@FXML
	private TextField nssField;
	@FXML
	private TextField emailField;
	@FXML
	private TextField telefono1Field;
	@FXML
	private TextField telefono2Field;
	@FXML
	private TextField cuentaField;
	@FXML
	private TextField carnetField;
	@FXML
	private CheckBox carnetCheckbox;

	@FXML
	private TextField vehiculoField;
	@FXML
	private CheckBox vehiculoCheckbox;
	@FXML
	private TextField permisoTrabajoField;
	@FXML
	private CheckBox permisoTrabajoCheckbox;

	@FXML
	private TextField discapacidadesField;
	@FXML
	private CheckBox discapacidadesCheckbox;

	@FXML
	private GridPane gridId;
	
    private Stage dialogStage;
    private Trabajador worker;
    private boolean okClicked = false;
    private DataBase bbdd;
    
    private ArrayList<String> movimientosTipoSelect = new ArrayList<>();

    private ObservableList<Trabajador> trabajadores = FXCollections.observableArrayList();
    private ObservableList<Trabajador> filtroTrabajadores = FXCollections.observableArrayList();
 
    private ObservableList<Centro> centros = FXCollections.observableArrayList();
    
    private ObservableList<Categoria> categorias = FXCollections.observableArrayList();

    private ObservableList<Horario> horarios = FXCollections.observableArrayList();
    
    private ObservableList<TipoContrato> contratos = FXCollections.observableArrayList();
    
    private ArrayList<CheckBox> listaCheckDelete = new ArrayList<CheckBox>();
    
    private int totalFilas = 0;
    
    @FXML
    private void initialize() throws SQLException {
    	bbdd = new DataBase();
    	crearListas();
    	
    	
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    private void crearListas() throws SQLException {
    	//TODO
    	movimientosTipoSelect.add("Alta Nueva");
    	movimientosTipoSelect.add("Llamamiento");
    	movimientosTipoSelect.add("Baja fin actividad");
    	movimientosTipoSelect.add("Baja voluntaria");
    	movimientosTipoSelect.add("Período prueba no superado");
    	movimientosTipoSelect.add("Cambio de categoría");
    	movimientosTipoSelect.add("Modificación");
    	movimientosTipoSelect.add("Cambio tipo contrato");
    	
    	trabajadores = bbdd.obtenerDatosTrabajadores();
    	centros = bbdd.obtenerDatosCentros();
    	categorias = bbdd.obtenerDatosCategorias();
    	horarios = bbdd.obtenerDatosHorarios();
    	contratos = bbdd.obtenerDatosContratos();
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     * @throws SQLException
     */
    @FXML
    private void handleOk() throws SQLException {
        if (isInputValid()) {

        	worker.setDni(dniField.getText());
        	worker.setNombre(nombreField.getText());
        	worker.setApellido1(apellido1Field.getText());
        	worker.setApellido2(apellido2Field.getText());
        	worker.setFechaNacimiento(fechaNacimientoDatePicker.getValue());
        	worker.setNacionalidad(nacionalidadField.getText());
        	worker.setDomicilio(domicilioField.getText());
        	worker.setCiudad(ciudadField.getText());
        	worker.setPoblacion(poblacionField.getText());
        	worker.setCp(Integer.parseInt(cpField.getText()));
        	worker.setnss(nssField.getText());
        	worker.setEmail(emailField.getText());
        	worker.setTelefono1(telefono1Field.getText());
        	worker.setTelefono2(telefono2Field.getText());
        	worker.setCuenta(cuentaField.getText());
        	if(carnetCheckbox.isSelected()){
        		worker.setCarnet("1");
        	}else{
        		worker.setCarnet("0");
        	}
        	if(vehiculoCheckbox.isSelected()){
        		worker.setVehiculo("1");
        	}else{
        		worker.setVehiculo("0");
        	}
        	if(permisoTrabajoCheckbox.isSelected()){
        		worker.setPermisoTrabajo("1");
        	}else{
        		worker.setPermisoTrabajo("0");
        	}
        	if(discapacidadesCheckbox.isSelected()){
        		worker.setDiscapacidades("1");
        	}else{
        		worker.setDiscapacidades("0");
        	}

            okClicked = true;
            bbdd.actualizarTrabajador(worker, worker.getDni());
            dialogStage.close();
        }
    }
    
    @FXML
    private void addRowGridPane() throws SQLException {
    	ComboBox<String> tipoMovimientoCombo = new ComboBox<String>();
    	tipoMovimientoCombo.getItems().addAll(movimientosTipoSelect);
    	tipoMovimientoCombo.setId(Integer.toString(gridId.getRowCount()) + "movimientoTipoCombo");
    	tipoMovimientoCombo.setEditable(true);
    	
    	ComboBox<Trabajador> trabajadoresCombo = new ComboBox<Trabajador>();
    	trabajadoresCombo.getItems().addAll(trabajadores);
    	trabajadoresCombo.setConverter(new StringConverter<Trabajador>() {

			@Override
			public Trabajador fromString(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String toString(Trabajador arg0) {
				// TODO Auto-generated method stub
				if (arg0 == null) {
					return "";
				}else {
					String nombreCompleto = arg0.getApellido1();
					if (arg0.getApellido2()!=null) {
						nombreCompleto = nombreCompleto + " " + arg0.getApellido2();
					}
					nombreCompleto = nombreCompleto +", " + arg0.getNombre();
					return nombreCompleto;
				}
			}
		});
    	trabajadoresCombo.setId(Integer.toString(gridId.getRowCount()) + "trabajadoresCombo");
    	trabajadoresCombo.setEditable(true);
    	
    	ComboBox<Centro> centrosCombo = new ComboBox<Centro>();
    	centrosCombo.getItems().addAll(centros);
    	centrosCombo.setConverter(new StringConverter<Centro>() {

			@Override
			public Centro fromString(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String toString(Centro arg0) {
				// TODO Auto-generated method stub
				return arg0 == null ? "" : arg0.getNombre();
			}
		});
    	centrosCombo.setId(Integer.toString(gridId.getRowCount()) + "centrosCombo");
    	centrosCombo.setEditable(true);
    	
    	ComboBox<Categoria> categoriasCombo = new ComboBox<Categoria>();
    	categoriasCombo.getItems().addAll(categorias);
    	categoriasCombo.setConverter(new StringConverter<Categoria>() {
			
			@Override
			public String toString(Categoria arg0) {
				// TODO Auto-generated method stub
				return arg0 == null ? "" : arg0.getNombre();
			}
			
			@Override
			public Categoria fromString(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
		});
    	categoriasCombo.setId(Integer.toString(gridId.getRowCount()) + "categoriaCombo");
    	categoriasCombo.setEditable(true);
    	
    	ComboBox<TipoContrato> contratosCombo = new ComboBox<TipoContrato>();
    	contratosCombo.getItems().addAll(contratos);
    	contratosCombo.setConverter(new StringConverter<TipoContrato>() {
			
			@Override
			public String toString(TipoContrato arg0) {
				// TODO Auto-generated method stub
				return arg0 == null ? "" : arg0.getNombre();
			}
			
			@Override
			public TipoContrato fromString(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
		});
    	contratosCombo.setId(Integer.toString(gridId.getRowCount()) + "contratoCombo");
    	contratosCombo.setEditable(true);
    	
    	ComboBox<Horario> horariosCombo = new ComboBox<Horario>();
    	horariosCombo.getItems().addAll(horarios);
    	horariosCombo.setConverter(new StringConverter<Horario>() {
			
			@Override
			public String toString(Horario arg0) {
				// TODO Auto-generated method stub
				return arg0 == null ? "" : arg0.getHorario();
			}
			
			@Override
			public Horario fromString(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
		});
    	horariosCombo.setId(Integer.toString(gridId.getRowCount()) + "horarioCombo");
    	horariosCombo.setEditable(true);
    	
    	DatePicker fechaInicio = new DatePicker();
    	fechaInicio.setId(Integer.toString(gridId.getRowCount()) + "fechaInicio");
    	
    	DatePicker fechaFin = new DatePicker();
    	fechaFin.setId(Integer.toString(gridId.getRowCount()) + "fechaFin");
    	
    	TextField importeBaja = new TextField();
    	importeBaja.setId(Integer.toString(gridId.getRowCount()) + "importebaja");
    	
    	CheckBox checkDelete = new CheckBox();
    	checkDelete.setId(Integer.toString(gridId.getRowCount()) + "checkDelete");
    	
    	listaCheckDelete.add(checkDelete);
    	
    	gridId.addRow(gridId.getRowCount(),
    			tipoMovimientoCombo,
    			trabajadoresCombo,
    			centrosCombo,
    			categoriasCombo,
    			contratosCombo,
    			horariosCombo,
    			fechaInicio,
    			fechaFin,
    			importeBaja,
    			checkDelete);
    	totalFilas++;
    }
    
    
    @FXML
    private void hacerLlamamientos(ActionEvent event) throws SQLException {
    	//EXISTE UNA FILA 0 DEMÁS
        if (isInputValid()) {
        	 Scene app_stage =  ((Node) event.getSource()).getScene();
        	 ComboBox<Trabajador> tb = (ComboBox<Trabajador>) app_stage.lookup("#1trabajadoresCombo");
        	 System.out.println(tb.getSelectionModel().getSelectedItem().getNombre());
        }
    }
    
    
    @FXML
    private void eliminarFilas(ActionEvent event) throws SQLException {
    	//EXISTE UNA FILA 0 DEMÁS
        if (isInputValid()) {
        	
        	List<Integer> borrar = new ArrayList<Integer>();
        	ArrayList<CheckBox> borrarCheck = new ArrayList<CheckBox>();
        	for (int i = totalFilas-1; i > -1; i--) {
        		CheckBox check = listaCheckDelete.get(i);
        		if(check.isSelected()) {
        			
        			String id = check.getId();
        			String[] separarId = id.split("checkDelete");
        			int indiceFila = Integer.parseInt(separarId[0]);
        			borrar.add(indiceFila);
        			borrarCheck.add(check);
        			//gridId.getChildren().removeIf(node -> gridId.getRowIndex(node) == indiceFila);
        			//listaCheckDelete.remove(i);
        			
        		}
			}
        	
        	//Borramos las filas
        	for (int i = borrar.size()-1;i > -1; i--) {
        		int indiceBorrar = borrar.get(i);
        		gridId.getChildren().removeIf(node -> gridId.getRowIndex(node) == indiceBorrar);
        		totalFilas--;
        	}
        
        	//Borramos los check de la lista
        	for(CheckBox borrado:borrarCheck) {
        		listaCheckDelete.remove(borrado);
        	}

        }
    }
 
    
   

	@FXML
    private void handleCancel(ActionEvent event) throws IOException {
    	Parent home_page_parent = FXMLLoader.load(getClass().getResource("MainMovimientosView.fxml"));
	    Scene home_page_scene = new Scene(home_page_parent);
	    Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    app_stage.hide(); //optional
	    app_stage.setScene(home_page_scene);
	    app_stage.show();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {

    	return true;
    }
    
    @FXML
    public boolean showNewCategoria() throws SQLException {
        try {

            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/CategoriasCreateView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Categoria categoria = new Categoria();

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
            crearListas();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }
    
    @FXML
    public boolean showNewHorario() throws SQLException {
        try {

            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/HorariosCreateView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            Horario horario = new Horario();
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
            crearListas();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }
    
    @FXML
    public boolean showNewCentro() throws SQLException {
        try {

            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/CentroCreateView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Centro centro = new Centro();
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
            crearListas();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }
    
    @FXML
    public boolean showNewContrato() throws SQLException {
        try {

            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/ContratosCreateView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            TipoContrato contrato = new TipoContrato();
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
    public boolean showNewTrabajador() throws SQLException {
        try {

            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/WorkerCreateView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Trabajador trabajador = new Trabajador();

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
}
