package app.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Centro {

	private IntegerProperty   idCentro;
	private StringProperty  nombre;
	private StringProperty  direccion;
	private IntegerProperty   cp;
	private StringProperty  ciudad;

	public Centro() {
		this(null,null, null,null);
	}

	/**
	 * Constructor with some initial data.
	 *
	 * @param firstName
	 * @param lastName
	 */
	public Centro(String nombre, String direccion, Integer cp, String ciudad) {
		this.idCentro = new SimpleIntegerProperty(0);
		this.nombre = new SimpleStringProperty("");
		this.direccion = new SimpleStringProperty("");
		this.cp= new SimpleIntegerProperty(0);
		this.ciudad = new SimpleStringProperty("");
	}


	public int getIdCentro() {
		return idCentro.get();
	}
	public void setIdCentro(int idCentro) {
		this.idCentro.set(idCentro);;
	}
	public IntegerProperty idCentroProperty(){
		return idCentro;
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

	public String getDireccion() {
		return direccion.get();
	}
	public void setDireccion(String direccion) {
		this.direccion.set(direccion);
	}
	public StringProperty direccionProperty(){
		return direccion;
	}

	public int getCp() {
		return cp.get();
	}
	public void setCp(int cp) {
		this.cp.set(cp);;
	}
	public IntegerProperty cpProperty(){
		return cp;
	}

	public String getCiudad() {
		return ciudad.get();
	}
	public void setCiudad(String ciudad) {
		this.ciudad.set(ciudad);
	}
	public StringProperty ciudadProperty(){
		return ciudad;
	}
}
