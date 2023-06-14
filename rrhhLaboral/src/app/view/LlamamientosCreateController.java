package app.view;


import app.util.*;
import com.itextpdf.text.Document;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.DocumentException;
import app.Main;
import app.model.Categoria;
import app.model.Centro;
import app.model.Horario;
import app.model.Movimiento;
import app.model.TipoContrato;
import app.model.TipoMovimiento;
import app.model.Trabajador;

import javax.mail.MessagingException;

public class LlamamientosCreateController {


	@FXML
	private GridPane gridId;
	
    private Stage dialogStage;
    private Trabajador worker;
    private boolean okClicked = false;
    private DataBase bbdd;
    
    private ObservableList<TipoMovimiento> movimientosTipoSelect = FXCollections.observableArrayList();
    private ArrayList<ComboBox<TipoMovimiento>> listaComboTipoMovimiento = new ArrayList<ComboBox<TipoMovimiento>>();

    private ObservableList<Trabajador> trabajadores = FXCollections.observableArrayList();
    private ArrayList<ComboBox<Trabajador>> listaComboTrabajadores = new ArrayList<ComboBox<Trabajador>>();
 
    private ObservableList<Centro> centros = FXCollections.observableArrayList();
    private ArrayList<ComboBox<Centro>> listaComboCentros = new ArrayList<ComboBox<Centro>>();
    
    private ObservableList<Categoria> categorias = FXCollections.observableArrayList();
    private ArrayList<ComboBox<Categoria>> listaComboCategorias = new ArrayList<ComboBox<Categoria>>();

    private ObservableList<Horario> horarios = FXCollections.observableArrayList();
    private ArrayList<ComboBox<Horario>> listaComboHorarios = new ArrayList<ComboBox<Horario>>();
    
    private ObservableList<TipoContrato> contratos = FXCollections.observableArrayList();
    private ArrayList<ComboBox<TipoContrato>> listaComboContratos = new ArrayList<ComboBox<TipoContrato>>();
    
    private ArrayList<DatePicker> listaPickerInicio = new ArrayList<DatePicker>();
    private ArrayList<DatePicker> listaPickerFin = new ArrayList<DatePicker>();
    private ArrayList<TextField> listaComboBajas = new ArrayList<TextField>();
    
    
    private ArrayList<CheckBox> listaCheckDelete = new ArrayList<CheckBox>();
    
    private ObservableList<Movimiento> movimientos = FXCollections.observableArrayList();
    
    private int totalFilas = 0;
    
    @FXML
    private void initialize() throws SQLException {
    	bbdd = new DataBase();
    	crearListas();
    	
    	
    }

    public void setDialogStage(Stage dialogStage) {

		this.dialogStage = dialogStage;
		this.dialogStage.setMaximized(true);
    }
    
    private void crearListas() throws SQLException {
    	trabajadores = bbdd.obtenerDatosTrabajadores();
    	movimientosTipoSelect = bbdd.obtenerDatosTipoMovimientos();
    	centros = bbdd.obtenerDatosCentros();
    	categorias = bbdd.obtenerDatosCategorias();
    	horarios = bbdd.obtenerDatosHorarios();
    	contratos = bbdd.obtenerDatosContratos();
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    
    @FXML
    private void addRowGridPane() throws SQLException {
    	ComboBox<TipoMovimiento> tipoMovimientoCombo = new ComboBox<TipoMovimiento>();
    	tipoMovimientoCombo.getItems().addAll(movimientosTipoSelect);
    	tipoMovimientoCombo.setConverter(new StringConverter<TipoMovimiento>() {

			@Override
			public TipoMovimiento fromString(String arg0) {
				return null;
			}

			@Override
			public String toString(TipoMovimiento arg0) {
				if (arg0 == null) {
					return "";
				}else {
					
					return arg0.getNombre();
				}
			}
		});
    	tipoMovimientoCombo.setId(Integer.toString(gridId.getRowCount()) + "movimientoTipoCombo");
    	listaComboTipoMovimiento.add(tipoMovimientoCombo);

    	ComboBox<Trabajador> trabajadoresCombo = new ComboBox<Trabajador>();
    	trabajadoresCombo.getItems().addAll(trabajadores);
    	trabajadoresCombo.setConverter(new StringConverter<Trabajador>() {

			@Override
			public Trabajador fromString(String arg0) {
				return null;
			}

			@Override
			public String toString(Trabajador arg0) {
				if (arg0 == null) {
					return "";
				}else {
					return arg0.getDniNombreCompleto();
				}
			}
		});
    	trabajadoresCombo.setId(Integer.toString(gridId.getRowCount()) + "trabajadoresCombo");
    	listaComboTrabajadores.add(trabajadoresCombo);

    	ComboBox<Centro> centrosCombo = new ComboBox<Centro>();
    	centrosCombo.getItems().addAll(centros);
    	centrosCombo.setConverter(new StringConverter<Centro>() {

			@Override
			public Centro fromString(String arg0) {
				return null;
			}

			@Override
			public String toString(Centro arg0) {
				return arg0 == null ? "" : arg0.getNombre();
			}
		});
    	centrosCombo.setId(Integer.toString(gridId.getRowCount()) + "centrosCombo");
    	listaComboCentros.add(centrosCombo);

    	ComboBox<Categoria> categoriasCombo = new ComboBox<Categoria>();
    	categoriasCombo.getItems().addAll(categorias);
    	categoriasCombo.setConverter(new StringConverter<Categoria>() {
			
			@Override
			public String toString(Categoria arg0) {
				return arg0 == null ? "" : arg0.getNombre();
			}
			
			@Override
			public Categoria fromString(String arg0) {
				return null;
			}
		});
    	categoriasCombo.setId(Integer.toString(gridId.getRowCount()) + "categoriaCombo");
    	listaComboCategorias.add(categoriasCombo);
    	
    	ComboBox<TipoContrato> contratosCombo = new ComboBox<TipoContrato>();
    	contratosCombo.getItems().addAll(contratos);
    	contratosCombo.setConverter(new StringConverter<TipoContrato>() {
			
			@Override
			public String toString(TipoContrato arg0) {
				return arg0 == null ? "" : arg0.getNombre();
			}
			
			@Override
			public TipoContrato fromString(String arg0) {
				return null;
			}
		});
    	contratosCombo.setId(Integer.toString(gridId.getRowCount()) + "contratoCombo");
    	listaComboContratos.add(contratosCombo);
    	
    	ComboBox<Horario> horariosCombo = new ComboBox<Horario>();
    	horariosCombo.getItems().addAll(horarios);
    	horariosCombo.setConverter(new StringConverter<Horario>() {
			
			@Override
			public String toString(Horario arg0) {
				return arg0 == null ? "" : arg0.getHorario();
			}
			
			@Override
			public Horario fromString(String arg0) {
				return null;
			}
		});
    	horariosCombo.setId(Integer.toString(gridId.getRowCount()) + "horarioCombo");
    	listaComboHorarios.add(horariosCombo);
    	
    	DatePicker fechaInicio = new DatePicker();
    	fechaInicio.setId(Integer.toString(gridId.getRowCount()) + "fechaInicio");
    	listaPickerInicio.add(fechaInicio);
    	
    	DatePicker fechaFin = new DatePicker();
    	fechaFin.setId(Integer.toString(gridId.getRowCount()) + "fechaFin");
    	listaPickerFin.add(fechaFin);
    	
    	TextField importeBaja = new TextField();
    	importeBaja.setId(Integer.toString(gridId.getRowCount()) + "importebaja");
    	listaComboBajas.add(importeBaja);
    	
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
    
    
    @SuppressWarnings("unchecked")
	@FXML
    private void hacerLlamamientos(ActionEvent event) throws SQLException, DocumentException, IOException, MessagingException, InterruptedException {
    	//EXISTE UNA FILA 0 DEM�S
    	movimientos.clear();
        if (isInputValid()) {
        	int rows = gridId.getRowCount();
        	int cols = gridId.getColumnCount();
        	for (int row = 1; row < rows; row ++) {
        		Movimiento movimientoActual = new Movimiento(
	        			0, 
	        			new Trabajador(), 
	        			new Centro(), 
	        			new Horario(), 
	        			LocalDate.now(),
	        			LocalDate.now(),
	        			0,
	        			new Categoria(),
	        			new TipoContrato(),
	        			new TipoMovimiento());
	        	movimientoActual.setIdMovimiento(row);
        		for (int col = 0; col < cols; col ++) {
        			
        			for (Node node : gridId.getChildren()) {
        		        if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col){

        		        	if (col == 0) {
        		            	for (ComboBox<TipoMovimiento> combo:listaComboTipoMovimiento) {
        		            		if(node.getId() == combo.getId()) {
        		            			movimientoActual.setNombreTipoMovimiento(combo.getValue().getNombre());
        		            		}
        		            	}    		            	
        		            }
        		        	if (col == 1) {
        		            	for (ComboBox<Trabajador> combo:listaComboTrabajadores) {
        		            		if(node.getId() == combo.getId()) {
        		            			movimientoActual.setDni(combo.getValue().getDni());
										movimientoActual.setNombreTrabajador(combo.getValue().getNombreCompleto());

        		            		}
        		            	}    		            	
        		            }
        		        	if (col == 2) {
        		            	for (ComboBox<Centro> combo:listaComboCentros) {
        		            		if(node.getId() == combo.getId()) {
        		            			movimientoActual.setNombreCentro(combo.getValue().getNombre());
        		            		}
        		            	}    		            	
        		            }
        		        	if (col == 3) {
        		            	for (ComboBox<Categoria> combo:listaComboCategorias) {
        		            		if(node.getId() == combo.getId()) {
        		            			movimientoActual.setNombreCategoria(combo.getValue().getNombre());
        		            		}
        		            	}    		            	
        		            }
        		        	if (col == 4) {
        		            	for (ComboBox<TipoContrato> combo:listaComboContratos) {
        		            		if(node.getId() == combo.getId()) {
        		            			movimientoActual.setNombreTipoContrato(combo.getValue().getNombre());
        		            		}
        		            	}    		            	
        		            }
        		        	if (col == 5) {
        		            	for (ComboBox<Horario> combo:listaComboHorarios) {
        		            		if(node.getId() == combo.getId()) {
        		            			movimientoActual.setNombreHorario(combo.getValue().getNombre());
        		            		}
        		            	}    		            	
        		            }
        		        	movimientoActual.setFechaCreacion(LocalDate.now());
        		        	if (col == 6) {
        		            	for (DatePicker combo:listaPickerInicio) {
        		            		if(node.getId() == combo.getId()) {
        		            			movimientoActual.setFechaInicio(combo.getValue());
        		            		}
        		            	}    		            	
        		            }
        		        	if (col == 7) {
        		            	for (DatePicker combo:listaPickerFin) {
        		            		if(node.getId() == combo.getId()) {
        		            			movimientoActual.setFechaFin(combo.getValue());
        		            		}
        		            	}    		            	
        		            }
        		        	if (col == 8) {
        		            	for (TextField combo:listaComboBajas) {
        		            		if(node.getId() == combo.getId()) {
        		            			String baja = combo.getText();
        		            			Integer importe;
        		            			if (baja.isEmpty()) {
        		            				importe = 0;
        		            			}else {
        		            				importe = Integer.parseInt(baja);
        		            			}
        		            			movimientoActual.setImporteBaja(importe);
        		            		}
        		            	}    		            	
        		            }
        		        	
        		        }
        			}
        			
        		}
        		
        		if(!movimientos.contains(movimientoActual)) {
        			movimientos.add(movimientoActual);
        		}
        	}
        }
        createPdf();
    }
    
    
    @FXML
    private void eliminarFilas(ActionEvent event) throws SQLException {
    	//EXISTE UNA FILA 0 DEM�S
        if (isInputValid()) {
        	
        	List<Integer> borrar = new ArrayList<Integer>();
        	ArrayList<CheckBox> borrarCheck = new ArrayList<CheckBox>();
        	ArrayList<ComboBox<TipoMovimiento>> borrarTipoMov = new ArrayList<ComboBox<TipoMovimiento>>();
        	ArrayList<ComboBox<Trabajador>> borrarTrab = new ArrayList<ComboBox<Trabajador>>();
        	ArrayList<ComboBox<Centro>> borrarCentro = new ArrayList<ComboBox<Centro>>();
        	ArrayList<ComboBox<Categoria>> borrarCategoria = new ArrayList<ComboBox<Categoria>>();
        	ArrayList<ComboBox<TipoContrato>> borrarTipoContr= new ArrayList<ComboBox<TipoContrato>>();
        	ArrayList<ComboBox<Horario>> borrarHorario = new ArrayList<ComboBox<Horario>>();
        	ArrayList<DatePicker> borrarFechaInicio = new ArrayList<DatePicker>();
        	ArrayList<DatePicker> borrarFechaFin = new ArrayList<DatePicker>();
        	ArrayList<TextField> borrarImporteBaja = new ArrayList<TextField>();
        	
        	for (int i = totalFilas-1; i > -1; i--) {
        		CheckBox check = listaCheckDelete.get(i);
        		if(check.isSelected()) {
        			
        			String id = check.getId();
        			String[] separarId = id.split("checkDelete");
        			int indiceFila = Integer.parseInt(separarId[0]);
        			borrar.add(indiceFila);
        			borrarCheck.add(check);
        			borrarTipoMov.add(listaComboTipoMovimiento.get(i));
        			borrarTrab.add(listaComboTrabajadores.get(i));
        			borrarCentro.add(listaComboCentros.get(i));
        			borrarCategoria.add(listaComboCategorias.get(i));
        			borrarTipoContr.add(listaComboContratos.get(i));
        			borrarHorario.add(listaComboHorarios.get(i));
        			borrarFechaInicio.add(listaPickerInicio.get(i));
        			borrarFechaFin.add(listaPickerFin.get(i));
        			borrarImporteBaja.add(listaComboBajas.get(i));
        			
        		}
			}
        	
        	//Borramos las filas
        	for (int i = borrar.size()-1;i > -1; i--) {
        		int indiceBorrar = borrar.get(i);
        		gridId.getChildren().removeIf(node -> gridId.getRowIndex(node) == indiceBorrar);
        		totalFilas--;
        	}
        
        	//Borramos los check de la lista y los combo de las listas correspondientes
        	for(CheckBox borrado:borrarCheck) {
        		listaCheckDelete.remove(borrado);
        	}
        	
        	for(ComboBox<TipoMovimiento> borrado: borrarTipoMov) {
        		listaComboTipoMovimiento.remove(borrado);
        	}
        	
        	for(ComboBox<Trabajador> borrado: borrarTrab) {
        		listaComboTrabajadores.remove(borrado);
        	}

        	
        	for(ComboBox<Centro> borrado: borrarCentro) {
        		listaComboCentros.remove(borrado);
        	}

        	
        	for(ComboBox<Categoria> borrado: borrarCategoria) {
        		listaComboCategorias.remove(borrado);
        	}

        	for(ComboBox<TipoContrato> borrado: borrarTipoContr) {
        		listaComboContratos.remove(borrado);
        	}
        	
        	for(ComboBox<Horario> borrado: borrarHorario) {
        		listaComboHorarios.remove(borrado);
        	}
        	
        	for(DatePicker borrado: borrarFechaInicio) {
        		listaPickerInicio.remove(borrado);
        	}
        	
        	for(DatePicker borrado: borrarFechaFin) {
        		listaPickerFin.remove(borrado);
        	}
        	for(TextField borrado: borrarImporteBaja) {
        		listaComboBajas.remove(borrado);
        	}
        }
    }
 
    
   

	@FXML
    private void handleCancel(ActionEvent event) throws IOException {
    	Parent home_page_parent = FXMLLoader.load(getClass().getResource("MainMovimientosView.fxml"));
	    Scene home_page_scene = new Scene(home_page_parent);
	    Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    app_stage.hide(); 
	    app_stage.setScene(home_page_scene);
	    app_stage.show();
    }

    private boolean isInputValid() {

    	return true;
    }
    
    @FXML
    public boolean showNewCategoria() throws SQLException {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/CategoriasCreateView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Categoria categoria = new Categoria();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Crear Categoria");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            CategoriasEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setCategoria(categoria, bbdd);

            dialogStage.showAndWait();
            actualizarListasSelect();
            
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }
    
    @FXML
    public boolean showNewHorario() throws SQLException {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/HorariosCreateView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            Horario horario = new Horario();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Crear Horario");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            HorariosEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setHorario(horario, bbdd);

            dialogStage.showAndWait();
            actualizarListasSelect();
            
            
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }
    
    @FXML
    public boolean showNewCentro() throws SQLException {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/CentroCreateView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Centro centro = new Centro();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Centro");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            CentrosEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setCentro(centro, bbdd);

            dialogStage.showAndWait();
            actualizarListasSelect();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }
    
    @FXML
    public boolean showNewContrato() throws SQLException {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/ContratosCreateView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            TipoContrato contrato = new TipoContrato();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Contrato");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            ContratosEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setContrato(contrato, bbdd);

            dialogStage.showAndWait();
            actualizarListasSelect();
            
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }
    
    @FXML
    public boolean showNewTrabajador() throws SQLException {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/WorkerCreateView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Trabajador trabajador = new Trabajador();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            WorkersEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(trabajador, bbdd);

            dialogStage.showAndWait();
            actualizarListasSelect();
            
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }
    
    @FXML
    public boolean showNewTipoMovimiento() throws SQLException {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/TipoMovimientoCreateView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            TipoMovimiento tipoMovimiento = new TipoMovimiento();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Crear Movimiento");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            TiposMovimientosEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMovimiento(tipoMovimiento, bbdd);

            dialogStage.showAndWait();
            actualizarListasSelect();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }
    
    private void actualizarListasSelect() throws SQLException, IOException {
    	//HAY UNA FILA OCULTA, HAY QUE EMPEZAR EN UNO
    	int rows = gridId.getRowCount();
    	int cols = gridId.getColumnCount();
    	for (int row = 1; row < rows; row ++) {
    		for (int col = 0; col < cols; col ++) {
    			for (Node node : gridId.getChildren()) {
    		        if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col){
    		            
    		            //TIPO MOVIMIENTO
    		            if (col == 0) {
    		            	for (ComboBox<TipoMovimiento> combo:listaComboTipoMovimiento) {
    		            		if(node.getId() == combo.getId()) {
    		            			TipoMovimiento mov = combo.getValue();
    		            			combo.getItems().removeAll(movimientosTipoSelect);
    		            			movimientosTipoSelect = bbdd.obtenerDatosTipoMovimientos();
    		            			combo.setItems(movimientosTipoSelect);
    		            			for(TipoMovimiento movs: movimientosTipoSelect) {
    		            				if (movs.getNombre().equals(mov.getNombre())) {
    		            					combo.setValue(movs);
    		            				}
    		            			}
    		            		}
    		            	}    		            	
    		            }
    		            
    		            //TRABAJADOR
    		            if (col == 1) {
    		            	for (ComboBox<Trabajador> combo:listaComboTrabajadores) {
    		            		if(node.getId() == combo.getId()) {
    		            			Trabajador trabaj = combo.getValue();
    		            			combo.getItems().removeAll(trabajadores);
    		            			trabajadores = bbdd.obtenerDatosTrabajadores();
    		            			combo.setItems(trabajadores);
    		            			for(Trabajador trabs: trabajadores) {
    		            				if (trabs.getDniNombreCompleto().equals(trabaj.getDniNombreCompleto())) {
    		            					combo.setValue(trabs);
    		            				}
    		            			}
    		            		}
    		            	}    
    		            }
    		            
    		            //CENTRO
    		            if (col == 2) {
    		            	for (ComboBox<Centro> combo:listaComboCentros) {
    		            		if(node.getId() == combo.getId()) {
    		            			Centro centro = combo.getValue();
    		            			combo.getItems().removeAll(centros);
    		            			centros = bbdd.obtenerDatosCentros();
    		            			combo.setItems(centros);
    		            			for(Centro ctros: centros) {
    		            				if (ctros.getNombre().equals(centro.getNombre())) {
    		            					combo.setValue(ctros);
    		            				}
    		            			}
    		            		}
    		            	}    
    		            }
    		            
    		            //CATEGORIA
    		            if (col == 3) {
    		            	for (ComboBox<Categoria> combo:listaComboCategorias) {
    		            		if(node.getId() == combo.getId()) {
    		            			Categoria cat = combo.getValue();
    		            			combo.getItems().removeAll(categorias);
    		            			categorias = bbdd.obtenerDatosCategorias();
    		            			combo.setItems(categorias);
    		            			for(Categoria cats: categorias) {
    		            				if (cats.getNombre().equals(cat.getNombre())) {
    		            					combo.setValue(cats);
    		            				}
    		            			}
    		            		}
    		            	}    
    		            }
    		            
    		            //TIPOCONTRATO
    		            if (col == 4) {
    		            	for (ComboBox<TipoContrato> combo:listaComboContratos) {
    		            		if(node.getId() == combo.getId()) {
    		            			TipoContrato contrato = combo.getValue();
    		            			combo.getItems().removeAll(contratos);
    		            			contratos = bbdd.obtenerDatosContratos();
    		            			combo.setItems(contratos);
    		            			for(TipoContrato ctrs: contratos) {
    		            				if (ctrs.getNombre().equals(contrato.getNombre())) {
    		            					combo.setValue(ctrs);
    		            				}
    		            			}
    		            		}
    		            	}    
    		            }
    		            //HORARIO
    		            if (col == 5) {
    		            	for (ComboBox<Horario> combo:listaComboHorarios) {
    		            		if(node.getId() == combo.getId()) {
    		            			Horario hor = combo.getValue();
    		            			combo.getItems().removeAll(horarios);
    		            			horarios = bbdd.obtenerDatosHorarios();
    		            			combo.setItems(horarios);
    		            			for(Horario hors: horarios) {
    		            				if (hors.getNombre().equals(hor.getNombre())) {
    		            					combo.setValue(hors);
    		            				}
    		            			}
    		            		}
    		            	}    
    		            }
    		        	
    		        }
    		    }		
        	}	
    	}
    }
    
    @FXML
    private void createPdf() throws DocumentException, IOException, SQLException, MessagingException, InterruptedException {
    	
        PdfCreator creadorPdf = new PdfCreator();
        ExcelCreator creadorExcel = new ExcelCreator();
		EmailSender email= new EmailSender();

        for (Movimiento mov: movimientos) {
        	String anexo = creadorPdf.crearAnexoTrabajador(bbdd.obtenerTrabajador(mov.getDni()),mov.getFechaInicio(),mov.getFechaFin());
			String llamamiento = creadorPdf.crearLlamamientoRealizadoTrabajador(bbdd.obtenerTrabajador(mov.getDni()),mov.getFechaInicio(),mov.getFechaFin());
			String consentimiento = creadorPdf.crearConsentimientoTrabajador(bbdd.obtenerTrabajador(mov.getDni()),mov.getFechaInicio());


			List<String> documentos = new ArrayList<>();
			documentos.add(anexo);
			documentos.add(llamamiento);
			documentos.add(consentimiento);
			bbdd.insertarMovimiento(mov);


			//TODO MANDAR EMAIL AL TRABAJADOR CON LA DOCUMENTACION
			String correoTrabajador = bbdd.obtenerTrabajador(mov.getDni()).getEmail();
			correoTrabajador = "romerogallen@gmail.com";

			email.mandarCorreoVariosArchivos(documentos, correoTrabajador);
        }
        creadorExcel.crearExcels(movimientos);

    }

	@FXML
	public boolean showCrearMovimiento() throws SQLException {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/LlamamientoEditView.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Trabajador trabajador = new Trabajador();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Movement");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			LlamamientosEditController controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();
			actualizarListasSelect();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}
	
}
