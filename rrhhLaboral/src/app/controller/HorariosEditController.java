package app.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import app.model.Horario;
import app.util.DataBase;

public class HorariosEditController {

	@FXML
	private TextField idHorarioField;
	@FXML
	private TextField nombreField;
	@FXML
	private TextField horasField;
	
	private String horarioCadena;

	@FXML
	private ComboBox<String> lunesInicioTurno1Id = new ComboBox<String>();
	@FXML
	private ComboBox<String>  lunesFinTurno1Id = new ComboBox<String>();
	@FXML
	private ComboBox<String> lunesInicioTurno2Id = new ComboBox<String>();
	@FXML
	private ComboBox<String> lunesFinTurno2Id = new ComboBox<String>();
	@FXML
	private ComboBox<String> martesInicioTurno1Id = new ComboBox<String>();
	@FXML
	private ComboBox<String> martesFinTurno1Id = new ComboBox<String>();
	@FXML
	private ComboBox<String> martesInicioTurno2Id = new ComboBox<String>();
	@FXML
	private ComboBox<String> martesFinTurno2Id = new ComboBox<String>();
	@FXML
	private ComboBox<String> miercolesInicioTurno1Id = new ComboBox<String>();
	@FXML
	private ComboBox<String> miercolesFinTurno1Id = new ComboBox<String>();
	@FXML
	private ComboBox<String> miercolesInicioTurno2Id = new ComboBox<String>();
	@FXML
	private ComboBox<String> miercolesFinTurno2Id = new ComboBox<String>();
	@FXML
	private ComboBox<String> juevesInicioTurno1Id = new ComboBox<String>();
	@FXML
	private ComboBox<String> juevesFinTurno1Id = new ComboBox<String>();
	@FXML
	private ComboBox<String> juevesInicioTurno2Id = new ComboBox<String>();
	@FXML
	private ComboBox<String> juevesFinTurno2Id = new ComboBox<String>();
	@FXML
	private ComboBox<String> viernesInicioTurno1Id = new ComboBox<String>();
	@FXML
	private ComboBox<String> viernesFinTurno1Id = new ComboBox<String>();
	@FXML
	private ComboBox<String> viernesInicioTurno2Id = new ComboBox<String>();
	@FXML
	private ComboBox<String> viernesFinTurno2Id = new ComboBox<String>();
	@FXML
	private ComboBox<String> sabadoInicioTurno1Id = new ComboBox<String>();
	@FXML
	private ComboBox<String> sabadoFinTurno1Id = new ComboBox<String>();
	@FXML
	private ComboBox<String> sabadoInicioTurno2Id = new ComboBox<String>();
	@FXML
	private ComboBox<String> sabadoFinTurno2Id = new ComboBox<String>();
	@FXML
	private ComboBox<String> domingoInicioTurno1Id = new ComboBox<String>();
	@FXML
	private ComboBox<String> domingoFinTurno1Id = new ComboBox<String>();
	@FXML
	private ComboBox<String> domingoInicioTurno2Id = new ComboBox<String>();
	@FXML
	private ComboBox<String> domingoFinTurno2Id = new ComboBox<String>();
	@FXML
	private ComboBox<String> visperasInicioTurno1Id = new ComboBox<String>();
	@FXML
	private ComboBox<String> visperasFinTurno1Id = new ComboBox<String>();
	@FXML
	private ComboBox<String> visperasInicioTurno2Id = new ComboBox<String>();
	@FXML
	private ComboBox<String> visperasFinTurno2Id = new ComboBox<String>();
	@FXML
	private ComboBox<String> festivosInicioTurno1Id = new ComboBox<String>();
	@FXML
	private ComboBox<String> festivosFinTurno1Id = new ComboBox<String>();
	@FXML
	private ComboBox<String> festivosInicioTurno2Id = new ComboBox<String>();
	@FXML
	private ComboBox<String> festivosFinTurno2Id = new ComboBox<String>();


    private Stage dialogStage;
    private Horario horario;
    private boolean okClicked = false;
    private DataBase bbdd;
    private ArrayList<String> horas = new ArrayList<>();
	private ObservableList<String> nombresHorarios = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
    	
    	horas.add("00:00");
    	horas.add("00:30");
    	horas.add("01:00");
    	horas.add("01:30");
    	horas.add("02:00");
    	horas.add("02:30");
    	horas.add("03:00");
    	horas.add("03:30");
    	horas.add("04:00");
    	horas.add("04:30");
    	horas.add("05:00");
    	horas.add("05:30");
    	horas.add("06:00");
    	horas.add("06:30");
    	horas.add("07:00");
    	horas.add("07:30");
    	horas.add("08:00");
    	horas.add("08:30");
    	horas.add("09:00");
    	horas.add("09:30");
    	horas.add("10:00");
    	horas.add("10:30");
    	horas.add("11:00");
    	horas.add("11:30");
    	horas.add("12:00");
    	horas.add("12:30");
    	horas.add("13:00");
    	horas.add("13:30");
    	horas.add("14:00");
    	horas.add("14:30");
    	horas.add("15:00");
    	horas.add("15:30");
    	horas.add("16:00");
    	horas.add("16:30");
    	horas.add("17:00");
    	horas.add("17:30");
    	horas.add("18:00");
    	horas.add("18:30");
    	horas.add("19:00");
    	horas.add("19:30");
    	horas.add("20:00");
    	horas.add("20:30");
    	horas.add("21:00");
    	horas.add("21:30");
    	horas.add("22:00");
    	horas.add("22:30");
    	horas.add("23:00");
    	horas.add("23:30");
    	
    	lunesInicioTurno1Id.getItems().addAll(horas);
    	lunesInicioTurno1Id.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newValue) -> contarHorasSemana());
    	lunesFinTurno1Id.getItems().addAll(horas);
    	lunesFinTurno1Id.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newValue) -> contarHorasSemana());
    	lunesInicioTurno2Id.getItems().addAll(horas);
    	lunesInicioTurno2Id.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newValue) -> contarHorasSemana());
    	lunesFinTurno2Id.getItems().addAll(horas);
    	lunesFinTurno2Id.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newValue) -> contarHorasSemana());
    	
    	martesInicioTurno1Id.getItems().addAll(horas);
    	martesInicioTurno1Id.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newValue) -> contarHorasSemana());
    	martesFinTurno1Id.getItems().addAll(horas);
    	martesFinTurno1Id.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newValue) -> contarHorasSemana());
    	martesInicioTurno2Id.getItems().addAll(horas);
    	martesInicioTurno2Id.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newValue) -> contarHorasSemana());
    	martesFinTurno2Id.getItems().addAll(horas);
    	martesFinTurno2Id.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newValue) -> contarHorasSemana());
    	
    	miercolesInicioTurno1Id.getItems().addAll(horas);
    	miercolesInicioTurno1Id.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newValue) -> contarHorasSemana());
    	miercolesFinTurno1Id.getItems().addAll(horas);
    	miercolesFinTurno1Id.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newValue) -> contarHorasSemana());
    	miercolesInicioTurno2Id.getItems().addAll(horas);
    	miercolesInicioTurno2Id.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newValue) -> contarHorasSemana());
    	miercolesFinTurno2Id.getItems().addAll(horas);
    	miercolesFinTurno2Id.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newValue) -> contarHorasSemana());
    	
    	juevesInicioTurno1Id.getItems().addAll(horas);
    	juevesInicioTurno1Id.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newValue) -> contarHorasSemana());
    	juevesFinTurno1Id.getItems().addAll(horas);
    	juevesFinTurno1Id.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newValue) -> contarHorasSemana());
    	juevesInicioTurno2Id.getItems().addAll(horas);
    	juevesInicioTurno2Id.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newValue) -> contarHorasSemana());
    	juevesFinTurno2Id.getItems().addAll(horas);
    	juevesFinTurno2Id.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newValue) -> contarHorasSemana());
    	
    	viernesInicioTurno1Id.getItems().addAll(horas);
    	viernesInicioTurno1Id.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newValue) -> contarHorasSemana());
    	viernesFinTurno1Id.getItems().addAll(horas);
    	viernesFinTurno1Id.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newValue) -> contarHorasSemana());
    	viernesInicioTurno2Id.getItems().addAll(horas);
    	viernesInicioTurno2Id.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newValue) -> contarHorasSemana());
    	viernesFinTurno2Id.getItems().addAll(horas);
    	viernesFinTurno2Id.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newValue) -> contarHorasSemana());
    	
    	sabadoInicioTurno1Id.getItems().addAll(horas);
    	sabadoInicioTurno1Id.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newValue) -> contarHorasSemana());
    	sabadoFinTurno1Id.getItems().addAll(horas);
    	sabadoFinTurno1Id.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newValue) -> contarHorasSemana());
    	sabadoInicioTurno2Id.getItems().addAll(horas);
    	sabadoInicioTurno2Id.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newValue) -> contarHorasSemana());
    	sabadoFinTurno2Id.getItems().addAll(horas);
    	sabadoFinTurno2Id.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newValue) -> contarHorasSemana());
    	
    	domingoInicioTurno1Id.getItems().addAll(horas);
    	domingoInicioTurno1Id.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newValue) -> contarHorasSemana());
    	domingoFinTurno1Id.getItems().addAll(horas);
    	domingoFinTurno1Id.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newValue) -> contarHorasSemana());
    	domingoInicioTurno2Id.getItems().addAll(horas);
    	domingoInicioTurno2Id.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newValue) -> contarHorasSemana());
    	domingoFinTurno2Id.getItems().addAll(horas);
    	domingoFinTurno2Id.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newValue) -> contarHorasSemana());
    	
    	visperasInicioTurno1Id.getItems().addAll(horas);
    	visperasInicioTurno1Id.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newValue) -> contarHorasSemana());
    	visperasFinTurno1Id.getItems().addAll(horas);
    	visperasFinTurno1Id.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newValue) -> contarHorasSemana());
    	visperasInicioTurno2Id.getItems().addAll(horas);
    	visperasInicioTurno2Id.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newValue) -> contarHorasSemana());
    	visperasFinTurno2Id.getItems().addAll(horas);
    	visperasFinTurno2Id.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newValue) -> contarHorasSemana());
    	
    	festivosInicioTurno1Id.getItems().addAll(horas);
    	festivosInicioTurno1Id.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newValue) -> contarHorasSemana());
    	festivosFinTurno1Id.getItems().addAll(horas);
    	festivosFinTurno1Id.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newValue) -> contarHorasSemana());
    	festivosInicioTurno2Id.getItems().addAll(horas);
    	festivosInicioTurno2Id.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newValue) -> contarHorasSemana());
    	festivosFinTurno2Id.getItems().addAll(horas);
    	festivosFinTurno2Id.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newValue) -> contarHorasSemana());
    	
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setHorario(Horario horario, DataBase bbdd) throws SQLException {
    	this.bbdd = bbdd;
    	this.horario = horario;    	
    	idHorarioField.setText(Integer.toString(horario.getIdHorario()));
    	nombreField.setText(horario.getNombre());
    	if(horario.getNombre()!="") {
    		descomponerCadenaHorario();
    	}
    	
    	horasField.setText(horario.getHoras());
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
        	contarHorasSemana();
        	horario.setIdHorario(((Integer.parseInt(idHorarioField.getText()))));
        	horario.setNombre(nombreField.getText());
        	horario.setHoras(horasField.getText());
        	crearCadenaHorario();
        	okClicked = true;
            bbdd.actualizarHorario(horario, horario.getIdHorario());
            dialogStage.close();
        }
    }

    private void crearCadenaHorario() {
		// TODO Auto-generated method stub
    	horarioCadena = "Lunes;";
    	if (lunesInicioTurno1Id.getValue()!=null) {
    		horarioCadena = horarioCadena + lunesInicioTurno1Id.getValue()+ "-";
    	}else {
    		horarioCadena = horarioCadena + "...-";
    	}
    	
    	if (lunesFinTurno1Id.getValue()!=null) {
    		horarioCadena = horarioCadena + lunesFinTurno1Id.getValue()+ "/";
    	}else {
    		horarioCadena = horarioCadena + ".../";
    	}
    	
    	if (lunesInicioTurno2Id.getValue()!=null) {
    		horarioCadena = horarioCadena + lunesInicioTurno2Id.getValue()+ "-";
    	}else {
    		horarioCadena = horarioCadena + "...-";
    	}
    	
    	if (lunesFinTurno2Id.getValue()!=null) {
    		horarioCadena = horarioCadena + lunesFinTurno2Id.getValue()+ "\n";
    	}else {
    		horarioCadena = horarioCadena + "...\n";
    	}
    	
    	
    	
    	horarioCadena = horarioCadena + "Martes;";
    	if (martesInicioTurno1Id.getValue()!=null) {
    		horarioCadena = horarioCadena + martesInicioTurno1Id.getValue()+ "-";
    	}else {
    		horarioCadena = horarioCadena + "...-";
    	}
    	
    	if (martesFinTurno1Id.getValue()!=null) {
    		horarioCadena = horarioCadena + martesFinTurno1Id.getValue()+ "/";
    	}else {
    		horarioCadena = horarioCadena + ".../";
    	}
    	
    	if (martesInicioTurno2Id.getValue()!=null) {
    		horarioCadena = horarioCadena + martesInicioTurno2Id.getValue()+ "-";
    	}else {
    		horarioCadena = horarioCadena + "...-";
    	}
    	
    	if (martesFinTurno2Id.getValue()!=null) {
    		horarioCadena = horarioCadena + martesFinTurno2Id.getValue()+ "\n";
    	}else {
    		horarioCadena = horarioCadena + "...\n";
    	}
    	
    	
    	horarioCadena = horarioCadena + "Miercoles;";
    	if (miercolesInicioTurno1Id.getValue()!=null) {
    		horarioCadena = horarioCadena + miercolesInicioTurno1Id.getValue()+ "-";
    	}else {
    		horarioCadena = horarioCadena + "...-";
    	}
    	
    	if (miercolesFinTurno1Id.getValue()!=null) {
    		horarioCadena = horarioCadena + martesFinTurno1Id.getValue()+ "/";
    	}else {
    		horarioCadena = horarioCadena + ".../";
    	}
    	
    	if (miercolesInicioTurno2Id.getValue()!=null) {
    		horarioCadena = horarioCadena + miercolesInicioTurno2Id.getValue()+ "-";
    	}else {
    		horarioCadena = horarioCadena + "...-";
    	}
    	
    	if (miercolesFinTurno2Id.getValue()!=null) {
    		horarioCadena = horarioCadena + miercolesFinTurno2Id.getValue()+ "\n";
    	}else {
    		horarioCadena = horarioCadena + "...\n";
    	}
    	
    	
    	horarioCadena = horarioCadena + "Jueves;";
    	if (juevesInicioTurno1Id.getValue()!=null) {
    		horarioCadena = horarioCadena + juevesInicioTurno1Id.getValue()+ "-";
    	}else {
    		horarioCadena = horarioCadena + "...-";
    	}
    	
    	if (juevesFinTurno1Id.getValue()!=null) {
    		horarioCadena = horarioCadena + juevesFinTurno1Id.getValue()+ "/";
    	}else {
    		horarioCadena = horarioCadena + ".../";
    	}
    	
    	if (juevesInicioTurno2Id.getValue()!=null) {
    		horarioCadena = horarioCadena + juevesInicioTurno2Id.getValue()+ "-";
    	}else {
    		horarioCadena = horarioCadena + "...-";
    	}
    	
    	if (juevesFinTurno2Id.getValue()!=null) {
    		horarioCadena = horarioCadena + juevesFinTurno2Id.getValue()+ "\n";
    	}else {
    		horarioCadena = horarioCadena + "...\n";
    	}
    	
    	
    	horarioCadena = horarioCadena + "Viernes;";
    	if (viernesInicioTurno1Id.getValue()!=null) {
    		horarioCadena = horarioCadena + viernesInicioTurno1Id.getValue()+ "-";
    	}else {
    		horarioCadena = horarioCadena + "...-";
    	}
    	
    	if (viernesFinTurno1Id.getValue()!=null) {
    		horarioCadena = horarioCadena + viernesFinTurno1Id.getValue()+ "/";
    	}else {
    		horarioCadena = horarioCadena + ".../";
    	}
    	
    	if (viernesInicioTurno2Id.getValue()!=null) {
    		horarioCadena = horarioCadena + viernesInicioTurno2Id.getValue()+ "-";
    	}else {
    		horarioCadena = horarioCadena + "...-";
    	}
    	
    	if (viernesFinTurno2Id.getValue()!=null) {
    		horarioCadena = horarioCadena + viernesFinTurno2Id.getValue()+ "\n";
    	}else {
    		horarioCadena = horarioCadena + "...\n";
    	}
    	
    	
    	horarioCadena = horarioCadena + "Sabado;";
    	if (sabadoInicioTurno1Id.getValue()!=null) {
    		horarioCadena = horarioCadena + sabadoInicioTurno1Id.getValue()+ "-";
    	}else {
    		horarioCadena = horarioCadena + "...-";
    	}
    	
    	if (sabadoFinTurno1Id.getValue()!=null) {
    		horarioCadena = horarioCadena + sabadoFinTurno1Id.getValue()+ "/";
    	}else {
    		horarioCadena = horarioCadena + ".../";
    	}
    	
    	if (sabadoInicioTurno2Id.getValue()!=null) {
    		horarioCadena = horarioCadena + sabadoInicioTurno2Id.getValue()+ "-";
    	}else {
    		horarioCadena = horarioCadena + "...-";
    	}
    	
    	if (sabadoFinTurno2Id.getValue()!=null) {
    		horarioCadena = horarioCadena + sabadoFinTurno2Id.getValue()+ "\n";
    	}else {
    		horarioCadena = horarioCadena + "...\n";
    	}
    	
    	
    	
    	horarioCadena = horarioCadena + "Domingo;";
    	if (domingoInicioTurno1Id.getValue()!=null) {
    		horarioCadena = horarioCadena + domingoInicioTurno1Id.getValue()+ "-";
    	}else {
    		horarioCadena = horarioCadena + "...-";
    	}
    	
    	if (domingoFinTurno1Id.getValue()!=null) {
    		horarioCadena = horarioCadena + domingoFinTurno1Id.getValue()+ "/";
    	}else {
    		horarioCadena = horarioCadena + ".../";
    	}
    	
    	if (domingoInicioTurno2Id.getValue()!=null) {
    		horarioCadena = horarioCadena + domingoInicioTurno2Id.getValue()+ "-";
    	}else {
    		horarioCadena = horarioCadena + "...-";
    	}
    	
    	if (domingoFinTurno2Id.getValue()!=null) {
    		horarioCadena = horarioCadena + domingoFinTurno2Id.getValue()+ "\n";
    	}else {
    		horarioCadena = horarioCadena + "...\n";
    	}
    	
    	horarioCadena = horarioCadena + "Visperas;";
    	if (visperasInicioTurno1Id.getValue()!=null) {
    		horarioCadena = horarioCadena + visperasInicioTurno1Id.getValue()+ "-";
    	}else {
    		horarioCadena = horarioCadena + "...-";
    	}
    	
    	if (visperasFinTurno1Id.getValue()!=null) {
    		horarioCadena = horarioCadena + visperasFinTurno1Id.getValue()+ "/";
    	}else {
    		horarioCadena = horarioCadena + ".../";
    	}
    	
    	if (visperasInicioTurno2Id.getValue()!=null) {
    		horarioCadena = horarioCadena + visperasInicioTurno2Id.getValue()+ "-";
    	}else {
    		horarioCadena = horarioCadena + "...-";
    	}
    	
    	if (visperasFinTurno2Id.getValue()!=null) {
    		horarioCadena = horarioCadena + visperasFinTurno2Id.getValue()+ "\n";
    	}else {
    		horarioCadena = horarioCadena + "...\n";
    	}
    	
    	horarioCadena = horarioCadena + "Festivos;";
    	if (festivosInicioTurno1Id.getValue()!=null) {
    		horarioCadena = horarioCadena + festivosInicioTurno1Id.getValue()+ "-";
    	}else {
    		horarioCadena = horarioCadena + "...-";
    	}
    	
    	if (festivosFinTurno1Id.getValue()!=null) {
    		horarioCadena = horarioCadena + festivosFinTurno1Id.getValue()+ "/";
    	}else {
    		horarioCadena = horarioCadena + ".../";
    	}
    	
    	if (festivosInicioTurno2Id.getValue()!=null) {
    		horarioCadena = horarioCadena + festivosInicioTurno2Id.getValue()+ "-";
    	}else {
    		horarioCadena = horarioCadena + "...-";
    	}
    	
    	if (festivosFinTurno2Id.getValue()!=null) {
    		horarioCadena = horarioCadena + festivosFinTurno2Id.getValue();
    	}else {
    		horarioCadena = horarioCadena + "...";
    	}
    	
    	this.horario.setHorario(horarioCadena);
	}

	@FXML
    private void nuevoOk() throws SQLException {
        if (isInputValid()) {
        	
        	horario.setIdHorario((Integer.parseInt(idHorarioField.getText())));
        	horario.setNombre(nombreField.getText());
        	crearCadenaHorario();
        	contarHorasSemana();
        	horario.setHoras((horasField.getText()));
        	okClicked = true;
            bbdd.insertarHorario(horario);
            dialogStage.close();
        }
    }
	
	private void contarHorasSemana() {
		int minLunes = contarHoras(lunesInicioTurno1Id.getValue(), lunesFinTurno1Id.getValue(),
				lunesInicioTurno2Id.getValue(), lunesFinTurno2Id.getValue());
		int minMartes = contarHoras(martesInicioTurno1Id.getValue(), martesFinTurno1Id.getValue(),
				martesInicioTurno2Id.getValue(), martesFinTurno2Id.getValue());
		int minMiercoles = contarHoras(miercolesInicioTurno1Id.getValue(), miercolesFinTurno1Id.getValue(),
				miercolesInicioTurno2Id.getValue(), miercolesFinTurno2Id.getValue());
		int minJueves = contarHoras(juevesInicioTurno1Id.getValue(), juevesFinTurno1Id.getValue(),
				juevesInicioTurno2Id.getValue(), juevesFinTurno2Id.getValue());
		int minViernes = contarHoras(viernesInicioTurno1Id.getValue(), viernesFinTurno1Id.getValue(),
				viernesInicioTurno2Id.getValue(), viernesFinTurno2Id.getValue());
		int minSabado = contarHoras(sabadoInicioTurno1Id.getValue(), sabadoFinTurno1Id.getValue(),
				sabadoInicioTurno2Id.getValue(), sabadoFinTurno2Id.getValue());
		int minDomingo = contarHoras(domingoInicioTurno1Id.getValue(), domingoFinTurno1Id.getValue(),
				domingoInicioTurno2Id.getValue(), domingoFinTurno2Id.getValue());
		int totalMinHorasSemana = minLunes + minMartes + minMiercoles + minJueves + minViernes + minSabado + minDomingo;
		int horas = totalMinHorasSemana/60;
		int minutos = totalMinHorasSemana%60;
		String horasHorario = horas + " h. y " + minutos + " min.";
		this.horario.setHoras(horasHorario);
		this.horasField.setText(horasHorario);
	}
	
	private int contarHoras(String hora1, String hora2, String hora3, String hora4) {
		
		int horasturno1 = 0;
		if (hora1!= null & hora2!=null) {
			if(!hora1.equals("...") & !hora2.equals("...")) {
				String[] horasMinutos1 = hora1.split(":");
				int horaint1 = Integer.parseInt(horasMinutos1[0]);
				int minhoraint1 = horaint1*60;
				int minint1 = Integer.parseInt(horasMinutos1[1]);
				int minutoshora1 = minhoraint1 +minint1;
				
				String[] horasMinutos2 = hora2.split(":");
				int horaint2 = Integer.parseInt(horasMinutos2[0]);
				int minhoraint2 = 0;
				if (horaint2 < horaint1) {
					minhoraint2 = (horaint2 + 24) *60;
				}else {
					minhoraint2 = horaint2*60;
				}
				int minint2 = Integer.parseInt(horasMinutos2[1]);
				int minutoshora2 = minhoraint2 + minint2;
				
				horasturno1 = minutoshora2 - minutoshora1;	
			}
		}
		
		int horasturno2 = 0;
		if (hora3!= null & hora4!=null) {
			if(!hora3.equals("...") & !hora4.equals("...")) {
				String[] horasMinutos3 = hora3.split(":");
				int horaint3 = Integer.parseInt(horasMinutos3[0]);
				int minhoraint3 = horaint3*60;
				int minint3 = Integer.parseInt(horasMinutos3[1]);
				int minutoshora3 = minhoraint3 +minint3;
				
				String[] horasMinutos4 = hora4.split(":");
				int horaint4 = Integer.parseInt(horasMinutos4[0]);
				int minhoraint4 = 0;
				if (horaint4 < horaint3) {
					minhoraint4 = (horaint4 + 24) *60;
				}else {
					minhoraint4 = horaint4*60;
				}
				int minint4 = Integer.parseInt(horasMinutos4[1]);
				int minutoshora4 = minhoraint4 + minint4;
				
				horasturno2 = minutoshora4 - minutoshora3;
			}
		}
		int totalHorasDia = horasturno1 + horasturno2;
		return totalHorasDia;
		
	}
    private void descomponerCadenaHorario() {
		String[] horarioDias = this.horario.getHorario().split("\n");
		for(String dia: horarioDias) {
			setTextFields(dia);
		}
	}

	private void setTextFields(String dia) {
		String[] diaHorario = dia.split(";");
		String[] turnos = diaHorario[1].split("/");
		String[] iniciofin1 = turnos[0].split("-");
		String[] iniciofin2 = turnos[1].split("-");
		if(diaHorario[0].equals("Lunes")) {
			lunesInicioTurno1Id.setValue(iniciofin1[0]);
			lunesFinTurno1Id.setValue(iniciofin1[1]);
			lunesInicioTurno2Id.setValue(iniciofin2[0]);
			lunesFinTurno2Id.setValue(iniciofin2[1]);
		}
		
		if(diaHorario[0].equals("Martes")) {
			martesInicioTurno1Id.setValue(iniciofin1[0]);
			martesFinTurno1Id.setValue(iniciofin1[1]);
			martesInicioTurno2Id.setValue(iniciofin2[0]);
			martesFinTurno2Id.setValue(iniciofin2[1]);
		}
		
		if(diaHorario[0].equals("Miercoles")) {
			miercolesInicioTurno1Id.setValue(iniciofin1[0]);
			miercolesFinTurno1Id.setValue(iniciofin1[1]);
			miercolesInicioTurno2Id.setValue(iniciofin2[0]);
			miercolesFinTurno2Id.setValue(iniciofin2[1]);
		}
		
		if(diaHorario[0].equals("Jueves")) {
			juevesInicioTurno1Id.setValue(iniciofin1[0]);
			juevesFinTurno1Id.setValue(iniciofin1[1]);
			juevesInicioTurno2Id.setValue(iniciofin2[0]);
			juevesFinTurno2Id.setValue(iniciofin2[1]);
		}
		
		if(diaHorario[0].equals("Viernes")) {
			viernesInicioTurno1Id.setValue(iniciofin1[0]);
			viernesFinTurno1Id.setValue(iniciofin1[1]);
			viernesInicioTurno2Id.setValue(iniciofin2[0]);
			viernesFinTurno2Id.setValue(iniciofin2[1]);
		}
		
		if(diaHorario[0].equals("Sabado")) {
			sabadoInicioTurno1Id.setValue(iniciofin1[0]);
			sabadoFinTurno1Id.setValue(iniciofin1[1]);
			sabadoInicioTurno2Id.setValue(iniciofin2[0]);
			sabadoFinTurno2Id.setValue(iniciofin2[1]);
		}
		
		if(diaHorario[0].equals("Domingo")) {
			domingoInicioTurno1Id.setValue(iniciofin1[0]);
			domingoFinTurno1Id.setValue(iniciofin1[1]);
			domingoInicioTurno2Id.setValue(iniciofin2[0]);
			domingoFinTurno2Id.setValue(iniciofin2[1]);
		}
		
		if(diaHorario[0].equals("Visperas")) {
			visperasInicioTurno1Id.setValue(iniciofin1[0]);
			visperasFinTurno1Id.setValue(iniciofin1[1]);
			visperasInicioTurno2Id.setValue(iniciofin2[0]);
			visperasFinTurno2Id.setValue(iniciofin2[1]);
		}
		
		if(diaHorario[0].equals("Festivos")) {
			festivosInicioTurno1Id.setValue(iniciofin1[0]);
			festivosFinTurno1Id.setValue(iniciofin1[1]);
			festivosInicioTurno2Id.setValue(iniciofin2[0]);
			festivosFinTurno2Id.setValue(iniciofin2[1]);
		}
			
	}

	@FXML
    private void handleCancel() {
        dialogStage.close();
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
