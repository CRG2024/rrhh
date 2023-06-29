package app.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Horario {
	private IntegerProperty   idHorario;
	private StringProperty  nombre;
	private StringProperty  horario;
	private StringProperty horas;


	public Horario() {
		this(null,null);
	}


	public Horario(String nombre, String horario) {
		this.idHorario = new SimpleIntegerProperty(0);
		this.nombre = new SimpleStringProperty("");
		this.horario = new SimpleStringProperty("");
		this.horas = new SimpleStringProperty("");
	}


	public int getIdHorario() {
		return idHorario.get();
	}
	public void setIdHorario(int idHorario) {
		this.idHorario.set(idHorario);
	}
	public IntegerProperty idHorarioProperty(){
		return idHorario;
	}

	public String getNombre() {
		return nombre.get();
	}
	public void setNombre(String nombre) {
		this.nombre.set(nombre);
	}
	public StringProperty nombreProperty(){
		return nombre;
	}

	public String getHorario() {
		return horario.get();
	}
	public void setHorario(String horario) {
		this.horario.set(horario);
	}
	public StringProperty horarioProperty(){
		return horario;
	}

	public String getHoras() {
		return horas.get();
	}
	public void setHoras(String horas) {
		this.horas.set(horas);
	}
	public StringProperty horasProperty(){
		return horas;
	}

	public String getHorarioAbreviado(){
		String horario = this.getHorario();
		String lines [] = horario.split("\\r?\\n");
		String cadenaDias [] = new String[9];
		String cadenaHorarios [] = new String[9];

		for(int i=0; i<9; i++){
			String separarDia[] = lines[i].split(";");
			cadenaDias[i] = separarDia[0];
			cadenaHorarios[i] = separarDia[1];
		}

		List<String> dias = new ArrayList<>();
		List<String> horarios = new ArrayList<>();

		for(int i=0; i<9; i++){

			if(horarios.contains(cadenaHorarios[i])){
				int index = horarios.indexOf(cadenaHorarios[i]);
				String cadenaD = dias.get(index);
				String cadenaActualizada = cadenaD+", "+ abreviaturaDia(cadenaDias[i]);
				dias.set(index,cadenaActualizada);
			}else{
				dias.add(abreviaturaDia(cadenaDias[i]));
				horarios.add(cadenaHorarios[i]);
			}
		}

		int tamaño = dias.size();
		String cadenaHorarioAbrev = "";
		for(int i=0; i<tamaño;i++){
			cadenaHorarioAbrev = cadenaHorarioAbrev + dias.get(i)+" : "+horarios.get(i)+"\n";
		}

		return cadenaHorarioAbrev;
	}

	public String abreviaturaDia(String dia){
		if (dia.equals("Lunes")){
			return "L";
		}
		if (dia.equals("Martes")){
			return "M";
		}
		if (dia.equals("Miercoles")){
			return "Mi";
		}
		if (dia.equals("Jueves")){
			return "J";
		}
		if (dia.equals("Viernes")){
			return "V";
		}
		if (dia.equals("Sabado")){
			return "S";
		}
		if (dia.equals("Domingo")){
			return "D";
		}
		if (dia.equals("Visperas")){
			return "Vi";
		}
		if (dia.equals("Festivos")){
			return "F";
		}
		return "";
	}
}
