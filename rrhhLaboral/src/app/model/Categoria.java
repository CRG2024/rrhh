package app.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Categoria {

	private IntegerProperty   idCategoria;
	private StringProperty  nombre;
	private StringProperty  descripcion;

	public Categoria() {
		this(null,null);
	}

	/**
	 * Constructor with some initial data.
	 *
	 * @param firstName
	 * @param lastName
	 */
	public Categoria(String nombre, String descripcion) {
		this.idCategoria = new SimpleIntegerProperty(0);
		this.nombre = new SimpleStringProperty("");
		this.descripcion = new SimpleStringProperty("");
	}


	public int getIdCategoria() {
		return idCategoria.get();
	}
	public void setIdCategoria(int idCategoria) {
		this.idCategoria.set(idCategoria);
	}
	public IntegerProperty idCategoriaProperty(){
		return idCategoria;
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

	public String getDescripcion() {
		return descripcion.get();
	}
	public void setDescripcion(String descripcion) {
		this.descripcion.set(descripcion);
	}
	public StringProperty descripcionProperty(){
		return descripcion;
	}

}
