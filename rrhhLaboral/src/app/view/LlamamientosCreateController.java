package app.view;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
 
    private ObservableList<Centro> centros = FXCollections.observableArrayList();
    
    private ObservableList<Categoria> categorias = FXCollections.observableArrayList();

    private ObservableList<Horario> horarios = FXCollections.observableArrayList();
    
    private ObservableList<TipoContrato> contratos = FXCollections.observableArrayList();
    
    private ArrayList<CheckBox> listaCheckDelete = new ArrayList<CheckBox>();
    
    @FXML
    private void initialize() throws SQLException {
    	DataBase bbdd = new DataBase();
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

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
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
    			horariosCombo,
    			fechaInicio,
    			fechaFin,
    			importeBaja,
    			checkDelete);
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
        	int filasInicio = gridId.getRowCount() -2;
        	List<Integer> borrar = new ArrayList<Integer>();
        	ArrayList<CheckBox> borrarCheck = new ArrayList<CheckBox>();
        	System.out.println("filas");
        	System.out.println(filasInicio);
        	for (int i = filasInicio; i > -1; i--) {
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
        	}
        	System.out.println(listaCheckDelete.size());
        	//Borramos los check de la lista
        	for(CheckBox borrado:borrarCheck) {
        		listaCheckDelete.remove(borrado);
        	}
        	System.out.println(listaCheckDelete.size());
        	//Renombramos los checks que quedan
        	int sizeChecks = listaCheckDelete.size();
        	for (int i = 0; i < sizeChecks; i++ ) {
        		CheckBox cambiar = listaCheckDelete.get(i);
        		String[] separarId = cambiar.getId().split("checkDelete");
        		String nuevo = Integer.toString(i+1) +"checkDelete";
        		listaCheckDelete.get(i).setId(nuevo);
        		System.out.println(nuevo);
        	}

        }
    }
 
    private void renameCheckDeleteList(int ind) {
		// TODO Auto-generated method stub
    	
   
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
}
