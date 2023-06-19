package app.view;


import app.model.Horario;
import app.model.Trabajador;
import app.util.ComboBoxAutoComplete;
import app.util.DataBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;

public class HorariosSelectController {

	@FXML
	private TextField idHorarioField;
	@FXML
	private ComboBox horarioSelect;
	@FXML
	private TextField horasField;
	
	private String horarioCadena;

	@FXML
	private TextField lunesInicioTurno1Id;
	@FXML
	private TextField lunesFinTurno1Id;
	@FXML
	private TextField lunesInicioTurno2Id;
	@FXML
	private TextField lunesFinTurno2Id;
	@FXML
	private TextField martesInicioTurno1Id;
	@FXML
	private TextField martesFinTurno1Id;
	@FXML
	private TextField martesInicioTurno2Id;
	@FXML
	private TextField martesFinTurno2Id;
	@FXML
	private TextField miercolesInicioTurno1Id;
	@FXML
	private TextField miercolesFinTurno1Id;
	@FXML
	private TextField miercolesInicioTurno2Id;
	@FXML
	private TextField miercolesFinTurno2Id;
	@FXML
	private TextField juevesInicioTurno1Id;
	@FXML
	private TextField juevesFinTurno1Id;
	@FXML
	private TextField juevesInicioTurno2Id;
	@FXML
	private TextField juevesFinTurno2Id;
	@FXML
	private TextField viernesInicioTurno1Id;
	@FXML
	private TextField viernesFinTurno1Id;
	@FXML
	private TextField viernesInicioTurno2Id;
	@FXML
	private TextField viernesFinTurno2Id;
	@FXML
	private TextField sabadoInicioTurno1Id;
	@FXML
	private TextField sabadoFinTurno1Id;
	@FXML
	private TextField sabadoInicioTurno2Id;
	@FXML
	private TextField sabadoFinTurno2Id;
	@FXML
	private TextField domingoInicioTurno1Id;
	@FXML
	private TextField domingoFinTurno1Id;
	@FXML
	private TextField domingoInicioTurno2Id;
	@FXML
	private TextField domingoFinTurno2Id;
	@FXML
	private TextField visperasInicioTurno1Id;
	@FXML
	private TextField visperasFinTurno1Id;
	@FXML
	private TextField visperasInicioTurno2Id;
	@FXML
	private TextField visperasFinTurno2Id;
	@FXML
	private TextField festivosInicioTurno1Id;
	@FXML
	private TextField festivosFinTurno1Id;
	@FXML
	private TextField festivosInicioTurno2Id;
	@FXML
	private TextField festivosFinTurno2Id;


    private Stage dialogStage;
    private Horario horario;
    private boolean okClicked = false;
    private DataBase bbdd;
	private ObservableList<String> nombresHorarios = FXCollections.observableArrayList();


    @FXML
    private void initialize() throws SQLException {
		bbdd = new DataBase();
		ObservableList<Horario> horarios = FXCollections.observableArrayList();
		horarios = bbdd.obtenerDatosHorarios();
		for(Horario horario: horarios){
			nombresHorarios.add(horario.getNombre());
		}
		horarioSelect.setItems(nombresHorarios);
		new ComboBoxAutoComplete<String>(horarioSelect);
		desactivarTextos();
    	
    }

	private void desactivarTextos() {

		idHorarioField.setEditable(false);
		idHorarioField.setFocusTraversable(false);
		idHorarioField.setDisable(true);

		horasField.setEditable(false);
		horasField.setFocusTraversable(false);
		horasField.setDisable(true);

		lunesInicioTurno1Id.setEditable(false);
		lunesInicioTurno1Id.setFocusTraversable(false);
		lunesInicioTurno1Id.setDisable(true);
		lunesFinTurno1Id.setEditable(false);
		lunesFinTurno1Id.setFocusTraversable(false);
		lunesFinTurno1Id.setDisable(true);
		lunesInicioTurno2Id.setEditable(false);
		lunesInicioTurno2Id.setFocusTraversable(false);
		lunesInicioTurno2Id.setDisable(true);
		lunesFinTurno2Id.setEditable(false);
		lunesFinTurno2Id.setFocusTraversable(false);
		lunesFinTurno2Id.setDisable(true);

		martesInicioTurno1Id.setEditable(false);
		martesInicioTurno1Id.setFocusTraversable(false);
		martesInicioTurno1Id.setDisable(true);
		martesFinTurno1Id.setEditable(false);
		martesFinTurno1Id.setFocusTraversable(false);
		martesFinTurno1Id.setDisable(true);
		martesInicioTurno2Id.setEditable(false);
		martesInicioTurno2Id.setFocusTraversable(false);
		martesInicioTurno2Id.setDisable(true);
		martesFinTurno2Id.setEditable(false);
		martesFinTurno2Id.setFocusTraversable(false);
		martesFinTurno2Id.setDisable(true);

		miercolesInicioTurno1Id.setEditable(false);
		miercolesInicioTurno1Id.setFocusTraversable(false);
		miercolesInicioTurno1Id.setDisable(true);
		miercolesFinTurno1Id.setEditable(false);
		miercolesFinTurno1Id.setFocusTraversable(false);
		miercolesFinTurno1Id.setDisable(true);
		miercolesInicioTurno2Id.setEditable(false);
		miercolesInicioTurno2Id.setFocusTraversable(false);
		miercolesInicioTurno2Id.setDisable(true);
		miercolesFinTurno2Id.setEditable(false);
		miercolesFinTurno2Id.setFocusTraversable(false);
		miercolesFinTurno2Id.setDisable(true);

		juevesInicioTurno1Id.setEditable(false);
		juevesInicioTurno1Id.setFocusTraversable(false);
		juevesInicioTurno1Id.setDisable(true);
		juevesFinTurno1Id.setEditable(false);
		juevesFinTurno1Id.setFocusTraversable(false);
		juevesFinTurno1Id.setDisable(true);
		juevesInicioTurno2Id.setEditable(false);
		juevesInicioTurno2Id.setFocusTraversable(false);
		juevesInicioTurno2Id.setDisable(true);
		juevesFinTurno2Id.setEditable(false);
		juevesFinTurno2Id.setFocusTraversable(false);
		juevesFinTurno2Id.setDisable(true);

		viernesInicioTurno1Id.setEditable(false);
		viernesInicioTurno1Id.setFocusTraversable(false);
		viernesInicioTurno1Id.setDisable(true);
		viernesFinTurno1Id.setEditable(false);
		viernesFinTurno1Id.setFocusTraversable(false);
		viernesFinTurno1Id.setDisable(true);
		viernesInicioTurno2Id.setEditable(false);
		viernesInicioTurno2Id.setFocusTraversable(false);
		viernesInicioTurno2Id.setDisable(true);
		viernesFinTurno2Id.setEditable(false);
		viernesFinTurno2Id.setFocusTraversable(false);
		viernesFinTurno2Id.setDisable(true);

		sabadoInicioTurno1Id.setEditable(false);
		sabadoInicioTurno1Id.setFocusTraversable(false);
		sabadoInicioTurno1Id.setDisable(true);
		sabadoFinTurno1Id.setEditable(false);
		sabadoFinTurno1Id.setFocusTraversable(false);
		sabadoFinTurno1Id.setDisable(true);
		sabadoInicioTurno2Id.setEditable(false);
		sabadoInicioTurno2Id.setFocusTraversable(false);
		sabadoInicioTurno2Id.setDisable(true);
		sabadoFinTurno2Id.setEditable(false);
		sabadoFinTurno2Id.setFocusTraversable(false);
		sabadoFinTurno2Id.setDisable(true);

		domingoInicioTurno1Id.setEditable(false);
		domingoInicioTurno1Id.setFocusTraversable(false);
		domingoInicioTurno1Id.setDisable(true);
		domingoFinTurno1Id.setEditable(false);
		domingoFinTurno1Id.setFocusTraversable(false);
		domingoFinTurno1Id.setDisable(true);
		domingoInicioTurno2Id.setEditable(false);
		domingoInicioTurno2Id.setFocusTraversable(false);
		domingoInicioTurno2Id.setDisable(true);
		domingoFinTurno2Id.setEditable(false);
		domingoFinTurno2Id.setFocusTraversable(false);
		domingoFinTurno2Id.setDisable(true);

		visperasInicioTurno1Id.setEditable(false);
		visperasInicioTurno1Id.setFocusTraversable(false);
		visperasInicioTurno1Id.setDisable(true);
		visperasFinTurno1Id.setEditable(false);
		visperasFinTurno1Id.setFocusTraversable(false);
		visperasFinTurno1Id.setDisable(true);
		visperasInicioTurno2Id.setEditable(false);
		visperasInicioTurno2Id.setFocusTraversable(false);
		visperasInicioTurno2Id.setDisable(true);
		visperasFinTurno2Id.setEditable(false);
		visperasFinTurno2Id.setFocusTraversable(false);
		visperasFinTurno2Id.setDisable(true);

		festivosInicioTurno1Id.setEditable(false);
		festivosInicioTurno1Id.setFocusTraversable(false);
		festivosInicioTurno1Id.setDisable(true);
		festivosFinTurno1Id.setEditable(false);
		festivosFinTurno1Id.setFocusTraversable(false);
		festivosFinTurno1Id.setDisable(true);
		festivosInicioTurno2Id.setEditable(false);
		festivosInicioTurno2Id.setFocusTraversable(false);
		festivosInicioTurno2Id.setDisable(true);
		festivosFinTurno2Id.setEditable(false);
		festivosFinTurno2Id.setFocusTraversable(false);
		festivosFinTurno2Id.setDisable(true);
	}

	public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setHorario(Horario horario, DataBase bbdd) throws SQLException {
    	this.bbdd = bbdd;
    	this.horario = horario;
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
			Horario seleccionado = bbdd.obtenerHorarioPorNombre(horarioSelect.getValue().toString());
			this.horario = seleccionado;
			okClicked = true;
			dialogStage.close();
        }
    }

	private void descomponerCadenaHorario(Horario horario) {
		String[] horarioDias = horario.getHorario().split("\n");
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
			lunesInicioTurno1Id.setText(iniciofin1[0]);
			lunesFinTurno1Id.setText(iniciofin1[1]);
			lunesInicioTurno2Id.setText(iniciofin2[0]);
			lunesFinTurno2Id.setText(iniciofin2[1]);
		}
		
		if(diaHorario[0].equals("Martes")) {
			martesInicioTurno1Id.setText(iniciofin1[0]);
			martesFinTurno1Id.setText(iniciofin1[1]);
			martesInicioTurno2Id.setText(iniciofin2[0]);
			martesFinTurno2Id.setText(iniciofin2[1]);
		}
		
		if(diaHorario[0].equals("Miercoles")) {
			miercolesInicioTurno1Id.setText(iniciofin1[0]);
			miercolesFinTurno1Id.setText(iniciofin1[1]);
			miercolesInicioTurno2Id.setText(iniciofin2[0]);
			miercolesFinTurno2Id.setText(iniciofin2[1]);
		}
		
		if(diaHorario[0].equals("Jueves")) {
			juevesInicioTurno1Id.setText(iniciofin1[0]);
			juevesFinTurno1Id.setText(iniciofin1[1]);
			juevesInicioTurno2Id.setText(iniciofin2[0]);
			juevesFinTurno2Id.setText(iniciofin2[1]);
		}
		
		if(diaHorario[0].equals("Viernes")) {
			viernesInicioTurno1Id.setText(iniciofin1[0]);
			viernesFinTurno1Id.setText(iniciofin1[1]);
			viernesInicioTurno2Id.setText(iniciofin2[0]);
			viernesFinTurno2Id.setText(iniciofin2[1]);
		}
		
		if(diaHorario[0].equals("Sabado")) {
			sabadoInicioTurno1Id.setText(iniciofin1[0]);
			sabadoFinTurno1Id.setText(iniciofin1[1]);
			sabadoInicioTurno2Id.setText(iniciofin2[0]);
			sabadoFinTurno2Id.setText(iniciofin2[1]);
		}
		
		if(diaHorario[0].equals("Domingo")) {
			domingoInicioTurno1Id.setText(iniciofin1[0]);
			domingoFinTurno1Id.setText(iniciofin1[1]);
			domingoInicioTurno2Id.setText(iniciofin2[0]);
			domingoFinTurno2Id.setText(iniciofin2[1]);
		}
		
		if(diaHorario[0].equals("Visperas")) {
			visperasInicioTurno1Id.setText(iniciofin1[0]);
			visperasFinTurno1Id.setText(iniciofin1[1]);
			visperasInicioTurno2Id.setText(iniciofin2[0]);
			visperasFinTurno2Id.setText(iniciofin2[1]);
		}
		
		if(diaHorario[0].equals("Festivos")) {
			festivosInicioTurno1Id.setText(iniciofin1[0]);
			festivosFinTurno1Id.setText(iniciofin1[1]);
			festivosInicioTurno2Id.setText(iniciofin2[0]);
			festivosFinTurno2Id.setText(iniciofin2[1]);
		}
			
	}

	@FXML
    private void handleCancel() {
        dialogStage.close();
    }

	@FXML
	public void actualizarDatos() throws SQLException {
		Horario horario = bbdd.obtenerHorarioPorNombre(horarioSelect.getValue().toString());
		horasField.setText(horario.getHoras());
		descomponerCadenaHorario(horario);
	}

    private boolean isInputValid() {

    	return true;
    }

    public Horario getHorario() {

		return this.horario;
    }
}
