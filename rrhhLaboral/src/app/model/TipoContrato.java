package app.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TipoContrato {


	private IntegerProperty   idTipoContrato;
	private StringProperty  nombre;
	private StringProperty  codContrato;

	public TipoContrato() {
		this(null,null);
	}

	/**
	 * Constructor with some initial data.
	 *
	 * @param firstName
	 * @param lastName
	 */
	public TipoContrato(String nombre, String codContrato) {
		this.idTipoContrato = new SimpleIntegerProperty(0);
		this.nombre = new SimpleStringProperty("");
		this.codContrato = new SimpleStringProperty("");
	}


	public int getIdTipoContrato() {
		return idTipoContrato.get();
	}
	public void setIdTipoContrato(int idTipoContrato) {
		this.idTipoContrato.set(idTipoContrato);
	}
	public IntegerProperty idTipoContratoProperty(){
		return idTipoContrato;
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

	public String getCodContrato() {
		return codContrato.get();
	}
	public void setCodContrato(String codContrato) {
		this.codContrato.set(codContrato);
	}
	public StringProperty codContratoProperty(){
		return codContrato;
	}


}
