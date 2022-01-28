package app.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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
}
